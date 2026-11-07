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
        int totalDeLasPartesSana = 0;
        //cada barco sabe su parte sana, debe decirnos él cuanto sano tiene.
        for (Barco barco : barcos) {
            totalDeLasPartesSana += barco.partesSanas();
        }
        return totalDeLasPartesSana;
    }

    public int tamañoTotalDeLaFlota() {
        // sumar los tamaños de los barcos de la flota.
        int totalDeLosTamaños = 0;
        //cada barco sabe su tamaño, entonces vamos a obtenerlos con el metodo getTamañoDelBarco
        for (Barco barco : barcos) {
            totalDeLosTamaños += barco.getTamañoDelBarco();
        }
        return totalDeLosTamaños;
    }

    public boolean estaHundida() {
        // Si no hay barcos, la flota no puede estar hundida
        if(barcos.isEmpty()) {
            return false; 
        }
        // voy a preguntarle a cada barco si NO esta hundido.
        // si NO ESTA hundido devuelvo false..
        for(Barco barco : barcos) {
            //con que uno ya no este hundido, nos alcanza para cortar el recorrido.
            if (!barco.estaHundido()) {
                return false;
            }
        }
        //Si estan todos hundidos, entonces devolvemos true.. por que la flota esta hundida.
        return true;
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
