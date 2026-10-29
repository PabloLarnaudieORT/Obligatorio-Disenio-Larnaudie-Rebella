package uy.edu.ort.battleshipdemo.Modelo.Tactica;

import uy.edu.ort.battleshipdemo.Modelo.Disparo;

public abstract class Tactica {

    private String nombre;

    protected Tactica(String nombre) { this.nombre = nombre; }
    public String getNombre() { return nombre; }

    // Devuelve {fila, columna}; solo dimensiones e información ya conocida por la táctica.
    public abstract int[] seleccionarObjetivo(int filas, int columnas);
    public abstract void registraResultado(Disparo disparo);
}
