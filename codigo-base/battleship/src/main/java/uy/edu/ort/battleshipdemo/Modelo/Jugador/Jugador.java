package uy.edu.ort.battleshipdemo.Modelo.Jugador;

import uy.edu.ort.battleshipdemo.Modelo.Oceano;

public abstract class Jugador {
    private Oceano oceano;

    public Jugador() {
        this.oceano = new Oceano();
    }

    public Oceano getOceano() {
        return oceano;
    }
    
    // El jugador conoce su océano. Asignar uno con dimensiones antes de jugar.
    public void setOceano(Oceano oceano) {
        this.oceano = oceano;
    }

    public int partesSanas() {
        
        return oceano.partesSanas();
    }

    public int tamanioFlota() {
        return oceano.tamañoTotalDeLaFlota();
    }
}
