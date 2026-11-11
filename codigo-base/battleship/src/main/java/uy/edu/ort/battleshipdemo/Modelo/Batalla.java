package uy.edu.ort.battleshipdemo.Modelo;

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

public void agregarObservador(ObservadorBatalla observador) {
    if (!observadores.contains(observador)) {
        observadores.add(observador);
    }
}

public void quitarObservador(ObservadorBatalla observador) {
    observadores.remove(observador);
}

private void notificarObservadores() {
    for (ObservadorBatalla observador : observadores) {
        observador.actualizar(this);
    }
}



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
    notificarObservadores();
}



   public Ronda jugarRonda(
        int fila, int columna, int numeroRondaEsperada)
        throws BatallaException {

    if (estado.getEstado() != Estados.enCombate) {
        throw new BatallaException("La batalla no está en combate");
    }

    if (numeroRondaEsperada != rondas.size() + 1) {
        throw new BatallaException("El número de ronda no es válido");
    }

    Disparo disparoHumano =
            jugadorMaquina.getOceano().recibirDisparo(fila, columna);

    Disparo disparoMaquina = null;

    if (jugadorMaquina.getOceano().getFlota().estaHundida()) {
        ganada();
    } else {
        int[] objetivo = jugadorMaquina.getTactica().seleccionarObjetivo(
                jugadorHumano.getOceano().getFilas(),
                jugadorHumano.getOceano().getColumnas());

        disparoMaquina = jugadorHumano.getOceano().recibirDisparo(
                objetivo[0], objetivo[1]);

        jugadorMaquina.getTactica().registraResultado(disparoMaquina);

        if (jugadorHumano.getOceano().getFlota().estaHundida()) {
            perdida();
        }
    }

    Ronda ronda = new Ronda(
            numeroRondaEsperada, disparoHumano, disparoMaquina);

    rondas.add(ronda);
    notificarObservadores();
    return ronda;
}
}
