package uy.edu.ort.battleshipdemo.DTOs;

public class UbicarBarcoDto {
    private String lugarDeLaBatalla;
    private int indiceDelBarco;
    private int fila;
    private int columna;
    private boolean esHorizontal;
    
    public String getLugarDeLaBatalla() {
        return lugarDeLaBatalla;
    }


    public void setLugarDeLaBatalla(String lugarDeLaBatalla) {
        this.lugarDeLaBatalla = lugarDeLaBatalla;
    }


    public int getIndiceDelBarco() {
        return indiceDelBarco;
    }


    public void setIndiceDelBarco(int indiceDelBarco) {
        this.indiceDelBarco = indiceDelBarco;
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


    public boolean isEsHorizontal() {
        return esHorizontal;
    }


    public void setEsHorizontal(boolean esHorizontal) {
        this.esHorizontal = esHorizontal;
    }

    public UbicarBarcoDto(){

    }

    public UbicarBarcoDto(String lugarDeLaBatalla, int indiceDelBarco, int fila, int columna, boolean esHorizontal) {
        this.lugarDeLaBatalla = lugarDeLaBatalla;
        this.indiceDelBarco = indiceDelBarco;
        this.fila = fila;
        this.columna = columna;
        this.esHorizontal = esHorizontal;
    }



}
