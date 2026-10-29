package uy.edu.ort.battleshipdemo.Modelo;

public class Celda {
    private int fila;
    private int columna;
    private boolean disparada;

    public Celda() {
    }
    
    public Celda(int fila, int columna, boolean disparada) {
        this.fila = fila;
        this.columna = columna;
        this.disparada = disparada;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }
    
    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public boolean isDisparada() {
        return disparada;
    }

    public void setDisparada(boolean disparada) {
        this.disparada = disparada;
    }

}
