package uy.edu.ort.battleshipdemo.Modelo.Tactica;

import uy.edu.ort.battleshipdemo.Modelo.Disparo;

public abstract class Tactica {

    private String nombre;

    protected Tactica(String nombre) { this.nombre = nombre; }
    public String getNombre() { return nombre; }

    // Devuelve {fila, columna}
    public abstract int[] seleccionarObjetivo(int filas, int columnas);
    public void registraResultado(Disparo disparo) {
}


}
