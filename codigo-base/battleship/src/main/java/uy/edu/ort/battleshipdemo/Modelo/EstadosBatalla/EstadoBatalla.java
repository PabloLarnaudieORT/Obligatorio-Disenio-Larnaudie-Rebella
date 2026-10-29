package uy.edu.ort.battleshipdemo.Modelo.EstadosBatalla;

import uy.edu.ort.battleshipdemo.Modelo.Batalla.Estados;
import battleshipdemo.excepciones.BatallaException;

public abstract class EstadoBatalla {
    private Batalla batalla;
    private Batalla.Estados estado;

    public EstadoBatalla(Batalla batalla, Batalla.Estados estado, String nombre) {
        this.batalla = batalla;
        this.estado = estado;
    }

   public abstract void iniciar() throws BatallaException;

   public abstract void terminar() throws BatallaException;

}
