package uy.edu.ort.battleshipdemo.Modelo;

import uy.edu.ort.battleshipdemo.Modelo.Eventos.EventoBatalla;
import uy.edu.ort.battleshipdemo.Modelo.Eventos.ObservadorBatalla;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;
import uy.edu.ort.battleshipdemo.Modelo.EstadosBatalla.EstadoBatalla;
import uy.edu.ort.battleshipdemo.Modelo.EstadosBatalla.EstadoEnPreparacion;
import uy.edu.ort.battleshipdemo.Modelo.Jugador.JugadorHumano;
import uy.edu.ort.battleshipdemo.Modelo.Jugador.JugadorMaquina;
import java.util.ArrayList;
import java.util.List;

public class Batalla {

   // Atributos
    private String lugar;
    private EstadoBatalla estado = new EstadoEnPreparacion(this);
    private ConfiguracionBatalla configuracion;
    private JugadorHumano jugadorHumano;
    private JugadorMaquina jugadorMaquina;
    private List<Ronda> rondas = new ArrayList<>();

    private List<ObservadorBatalla> observadores = new ArrayList<>();


// Constructor vacio
    public Batalla(){
    }

    public EstadoBatalla getEstado() {
      return this.estado;
   }

   protected void cambiarEstado(EstadoBatalla estado) {
      this.estado = estado;
   }


   public void enPreparacion() throws BatallaException {
      this.estado = this.estado.enPreparacion();
   }

//quiere guardar en Batalla estado, el estado al que le consulta si puede cambiar que es ganada().
   public void ganada() throws BatallaException {
      this.estado = this.estado.ganada();
   }

   public void perdida() throws BatallaException {
      this.estado = this.estado.perdida();
   }


   public static enum Estados {
      enCombate,
      enPreparacion,
      ganada,
      perdida;

      private Estados() {
      }
   }

   //seccion de getters y setters
    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    public ConfiguracionBatalla getConfiguracion() { return configuracion; }
    public void setConfiguracion(ConfiguracionBatalla configuracion) { this.configuracion = configuracion; }
    public JugadorHumano getJugadorHumano() { return jugadorHumano; }
    public void setJugadorHumano(JugadorHumano jugadorHumano) { this.jugadorHumano = jugadorHumano; }
    public JugadorMaquina getJugadorMaquina() { return jugadorMaquina; }
    public void setJugadorMaquina(JugadorMaquina jugadorMaquina) { this.jugadorMaquina = jugadorMaquina; }
    public List<Ronda> getRondas() { return rondas; }

//metodo que devuelve una lista de enteros con los tamanios de los barcos hundidos del enemigo en orden en que son hundidos.
public List<Integer> getBajasEnemigas() {
    List<Integer> bajas = new ArrayList<>();

    for (Ronda ronda : rondas) {
        Disparo disparo = ronda.getDisparoHumano();

        if ("barco hundido".equals(disparo.getResultado().getResultado())) {
            Barco barco = jugadorMaquina.getOceano().getFlota()
                    .barcoEn(disparo.getCelda());

            bajas.add(barco.getTipo().getTamanio());
        }
    }

    return bajas;
}



//observador de batalla, para notificar a la vista cuando hay un cambio en el estado de la batalla o cuando se completa una ronda.
public void agregarObservador(ObservadorBatalla observador) {
    
    if (!observadores.contains(observador)) {
        observadores.add(observador);
    }
}


public void quitarObservador(ObservadorBatalla observador) {
    observadores.remove(observador);
}

//notifica a los observadores de la batalla cuando hay un cambio en el estado de la batalla o cuando se completa una ronda.
private void notificarObservadores(String tipoCambio) {
    EventoBatalla evento = new EventoBatalla(lugar, tipoCambio);

    for (ObservadorBatalla observador : observadores) {
        observador.actualizar(evento);
    }
}



//metodo que inicia el combate, validando que los jugadores tengan sus flotas completas y que la batalla esté en estado de preparación.
public void iniciarCombate() throws BatallaException {
    
    // aqui se busca cambiar a estado EnCombate, validando que se pueda hacer. 
    EstadoBatalla nuevoEstado = estado.enCombate();

    if (jugadorHumano == null
            || jugadorHumano.getOceano() == null
            || configuracion == null
            || !configuracion.flotaCompleta(jugadorHumano.getOceano().getFlota())) {
        throw new BatallaException("Debe posicionar todos sus barcos primero");
    }

    if (jugadorMaquina == null
            || jugadorMaquina.getOceano() == null
            || !configuracion.flotaCompleta(jugadorMaquina.getOceano().getFlota())) {
        throw new BatallaException("La flota de la máquina no está posicionada");
    }

    estado = nuevoEstado;
    notificarObservadores("ESTADO_CAMBIADO");
}



//metodo que permite jugar una ronda de la batalla, validando que la batalla esté en estado de combate y que el número de ronda esperado sea correcto. Devuelve un objeto Ronda con los resultados del disparo del jugador humano y del disparo de la máquina.
   public synchronized Ronda jugarRonda(
        int fila, int columna, int numeroRondaEsperada)
        throws BatallaException {

    if (estado.getEstado() != Estados.enCombate) {
        throw new BatallaException("La batalla no está en combate");
    }

    if (numeroRondaEsperada != rondas.size() + 1) {
        throw new BatallaException("El número de ronda no es válido");
    }

    Oceano oceanoEnemigo = jugadorMaquina.getOceano();
    Oceano oceanoPropio = jugadorHumano.getOceano();

    // Validar los dos disparos antes de marcar la celda del jugador.
    oceanoEnemigo.validarDisparo(fila, columna);
    int[] objetivoMaquina = jugadorMaquina.getTactica().seleccionarObjetivo(
            oceanoPropio.getFilas(), oceanoPropio.getColumnas());
    oceanoPropio.validarDisparo(objetivoMaquina[0], objetivoMaquina[1]);

    Disparo disparoHumano = oceanoEnemigo.recibirDisparo(fila, columna);

    Disparo disparoMaquina = null;
    boolean ganoJugadorHumano = oceanoEnemigo.getFlota().estaHundida();
    boolean ganoMaquina = false;

    if (!ganoJugadorHumano) {
        disparoMaquina = oceanoPropio.recibirDisparo(
                objetivoMaquina[0], objetivoMaquina[1]);

        jugadorMaquina.getTactica().registraResultado(disparoMaquina);
        ganoMaquina = oceanoPropio.getFlota().estaHundida();
    }

    Ronda ronda = new Ronda(
            numeroRondaEsperada, disparoHumano, disparoMaquina);

    rondas.add(ronda);

    if (ganoJugadorHumano) {
        ganada();
    } else if (ganoMaquina) {
        perdida();
    }

    notificarObservadores("RONDA_COMPLETADA");   
    return ronda;
}
}
