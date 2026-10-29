package uy.edu.ort.battleshipdemo.Modelo;

public class Oceano {
    private int filas;
    private int columnas;
    private Celda[][] celdas;
    private Flota flota = new Flota();

    public Oceano() {
    }

    public Oceano(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        generarOceano(filas, columnas);
    }

    private void generarOceano(int filas,int columnas) {
        // Implementación de la generación del océano con celdas
        celdas = new Celda[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda(i, j, false);
            }
        }
    }

    public void validarUbicacion(Barco barco, int fila, int columna, boolean horizontal) {
        // barco de esta flota, límites, superposición y separación incluso diagonal.
        throw new UnsupportedOperationException("Pendiente: validar ubicación.");
    }

    public void ubicarBarco(Barco barco, int fila, int columna, boolean horizontal) {
        // validar primero y luego asignar todas las celdas al barco.
        throw new UnsupportedOperationException("Pendiente: ubicar barco.");
    }

    public Disparo recibirDisparo(int fila, int columna) {
        // validar coordenadas y no repetir celda; determinar Resultado y registrar bajas.
        throw new UnsupportedOperationException("Pendiente: recibir disparo.");
    }

    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }
    public Celda[][] getCeldas() { return celdas; }
    public Flota getFlota() { return flota; }
    public void setFlota(Flota flota) { this.flota = flota; }

}
