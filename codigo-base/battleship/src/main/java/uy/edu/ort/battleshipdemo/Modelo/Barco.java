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
    //Aca tenemos que determinar las partes sanas a partir del tamaño y las celdas alcanzadas.
    int contador = 0;
    for (Celda celda : celdas) {
            if (celda.isDisparada()) {
                contador++;
            }
        }
        //debemos mostrar las partes sanas para la Sala de Operacionas
        // cuando la batalla esta en preparacion, por eso decidi contar y restarlo al tamaño.
        return tipo.getTamanio() - contador;
    }

    public boolean estaHundido() {
        //Aca con nati tenemos que comprobar que está posicionado y que no tiene partes sanas.
        boolean noEsHundido = false;
        if (celdas.size() == tipo.getTamanio() && partesSanas() == 0) {
            noEsHundido = true;
        }
        return noEsHundido;
    }

}
