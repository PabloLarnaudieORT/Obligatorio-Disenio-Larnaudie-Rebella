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
        // Armamos la base de jugador con nati, tenemos esta base para discutir pero la idea seria NO CALCULAR ACA, delegar a Oceano en Flota
    }

    public int tamanioFlota() {
        // Aca es lo mismo, tenemos esta base para discutir pero la idea seria NO CALCULAR ACA, delegar a Oceano en Flota para obtener el tamaño total.
    }
}
