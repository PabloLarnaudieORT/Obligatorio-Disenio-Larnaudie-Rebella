package uy.edu.ort.battleshipdemo.Modelo;

import uy.edu.ort.battleshipdemo.excepciones.BatallaException;
import uy.edu.ort.battleshipdemo.Modelo.EstadosBatalla.EstadoBatalla;
import uy.edu.ort.battleshipdemo.Modelo.EstadosBatalla.EstadoEnPreparacion;
import uy.edu.ort.battleshipdemo.Modelo.Jugador.JugadorHumano;
import uy.edu.ort.battleshipdemo.Modelo.Jugador.JugadorMaquina;
import java.util.ArrayList;
import java.util.List;

public class Batalla {
    private String lugar;
    private EstadoBatalla estado = new EstadoEnPreparacion(this);
    private ConfiguracionBatalla configuracion;
    private JugadorHumano jugadorHumano;
    private JugadorMaquina jugadorMaquina;
    private List<Ronda> rondas = new ArrayList<>();

    public Batalla(){
    }

    public EstadoBatalla getEstado() {
      return this.estado;
   }

   protected void cambiarEstado(EstadoBatalla estado) {
      this.estado = estado;
   }

   public void enCombate() throws BatallaException {
      this.estado = this.estado.enCombate();
   }

   public void enPreparacion() throws BatallaException {
      this.estado = this.estado.enPreparacion();
   }

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

    public void iniciarCombate() throws BatallaException {
        // delegar en State; solo comenzar cuando las flotas estén posicionadas.
        throw new UnsupportedOperationException("Pendiente: iniciar combate.");
    }

    public Ronda jugarRonda(int fila, int columna, int numeroRondaEsperada) throws BatallaException {
        // validar estado y ronda; resolver humano y, si no gana, máquina.
        // Ambos disparos deben formar una operación indivisible.
        throw new UnsupportedOperationException("Pendiente: jugar ronda.");
    }

}
