package uy.edu.ort.battleshipdemo.Modelo.EstadosBatalla;

import uy.edu.ort.battleshipdemo.Modelo.Batalla;
import uy.edu.ort.battleshipdemo.Modelo.Batalla.Estados;

public class EstadoGanada extends EstadoBatalla {
    public EstadoGanada(Batalla batalla) {
        super(batalla, Estados.ganada, "Ganada");
    }


    // Con Nati armamos la base, ahora hay que sobrescribir las operaciones del State según este estado.
}
