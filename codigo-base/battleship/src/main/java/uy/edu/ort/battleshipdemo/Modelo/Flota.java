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
    }

    public int tamanioTotal() {
        // sumar los tamaños de los barcos de la flota.
    }

    public boolean estaHundida() {
        // determinar si todos sus barcos están hundidos.
    }

    //Aca queremos buscar un barco
    //Usamos el getCeldas y el contains para ver si la celda pertenece a un barco
    public Barco buscarBarco(Celda celda) {
       for (Barco barco : barcos) {
            if (barco.getCeldas().contains(celda)) {
                return barco;
            }
        }
        return null;
    }

}
