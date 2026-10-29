package uy.edu.ort.battleshipdemo.Modelo;

import java.util.List;
import java.util.ArrayList;

public class Flota {
    private List<Barco> barcos;

    public Flota() {
        this.barcos = new ArrayList<>();
    }

    public void agregarBarco(Barco barco) {
        this.barcos.add(barco);
    }

    public void eliminarBarco(Barco barco) {
        this.barcos.remove(barco);
    }

    public List<Barco> getBarcos() {
        return barcos;
    }

    public int partesSanas() {
        // recorrer los barcos y pedir a cada uno sus partes sanas.
        throw new UnsupportedOperationException("Pendiente: partes sanas de la flota.");
    }

    public int tamanioTotal() {
        // sumar los tamaños de los barcos de la flota.
        throw new UnsupportedOperationException("Pendiente: tamaño total de la flota.");
    }

    public boolean estaHundida() {
        // determinar si todos sus barcos están hundidos.
        throw new UnsupportedOperationException("Pendiente: hundimiento de la flota.");
    }

}
