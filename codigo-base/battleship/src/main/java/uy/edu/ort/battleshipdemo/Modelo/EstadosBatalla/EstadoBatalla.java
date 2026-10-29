package uy.edu.ort.battleshipdemo.Modelo.EstadosBatalla;

import uy.edu.ort.battleshipdemo.Modelo.Batalla;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;

public abstract class EstadoBatalla {
    protected Batalla batalla;
    private Batalla.Estados estado;
    private String nombre;

    protected EstadoBatalla(Batalla batalla, Batalla.Estados estado, String nombre) {
        this.batalla = batalla;
        this.estado = estado;
        this.nombre = nombre;
    }

    public Batalla.Estados getEstado() { return estado; }
    public String getNombre() { return nombre; }

    // Con nati vimos que las clases hijas deben decidir qué operaciones permiten y qué errores
    // producen. Cada transición devuelve el estado que Batalla debe conservar.
    public EstadoBatalla enCombate() throws BatallaException {
       
    }
    public EstadoBatalla enPreparacion() throws BatallaException {

    }
    public EstadoBatalla ganada() throws BatallaException {

    }
    public EstadoBatalla perdida() throws BatallaException {
        
    }
}
