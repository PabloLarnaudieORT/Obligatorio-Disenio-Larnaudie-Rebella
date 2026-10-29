package uy.edu.ort.battleshipdemo.Modelo;

import java.util.ArrayList;
import java.util.List;

public class Barco {
    
    private TipoBarco tipo;
    private List<Celda> celdas = new ArrayList<>();

    public Barco() {
    }

    public Barco(TipoBarco tipo) {
        this.tipo = tipo;
    }

    public TipoBarco getTipo() {
        return tipo;
    }

    public void setTipo(TipoBarco tipo) {
        this.tipo = tipo;
    }

    public List<Celda> getCeldas() { return celdas; }

    public int partesSanas() {
        //Aca con nati tenemos que determinar las partes sanas a partir del tamaño y las celdas alcanzadas.
    }

    public boolean estaHundido() {
        //Aca con nati tenemos que comprobar que está posicionado y que no tiene partes sanas.
    }

}
