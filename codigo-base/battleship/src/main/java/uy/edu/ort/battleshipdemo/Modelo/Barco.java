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

//aqui vamos a contar las partes sanas del barco, es dcirr las celds que no fueron disparadass
    public int partesSanas() {
        int cantidad = 0;
        for (Celda celda : celdas) {
            if (!celda.isDisparada()) {
                cantidad++;
            }
        }
        return cantidad;
    }

//aqui lo que hacemos es verificar que las partes sanas sean igual a 0, es decir que este hundido. 
    public boolean estaHundido() {
        return partesSanas() == 0;
    }

}
