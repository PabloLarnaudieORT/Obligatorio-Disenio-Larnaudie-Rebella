package uy.edu.ort.battleshipdemo.modelo;

import battleshipdemo.excepciones.BatallaException;
public class Batalla {
    private String lugar;
    private EstadoBatalla estado = new EnCombate(this);

    public Batalla(){
    }

    public EstadoBatalla getEstado() {
      return this.estado;
   }

   protected void cambiarEstado(EstadoBatalla estado) {
      this.estado = estado;
   }

   public void enCombate() throws BatallaException {
      this.estado.enCombate();
   }

   public void enPreparacion() throws BatallaException {
      this.estado.enPreparacion();
   }

   public void ganada() throws BatallaException {
      this.estado.ganada();
   }

   public void perdida() throws BatallaException {
      this.estado.perdida();
   }

   public static enum Estados {
      enCombate,
      enPreparacion,
      ganada,
      perdida;

      private Estados() {
      }
   }
}
