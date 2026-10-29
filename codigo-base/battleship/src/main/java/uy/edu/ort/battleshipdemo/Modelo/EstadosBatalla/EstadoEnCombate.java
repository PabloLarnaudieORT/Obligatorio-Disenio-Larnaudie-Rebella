package uy.edu.ort.battleshipdemo.Modelo.EstadosBatalla;

import uy.edu.ort.battleshipdemo.Modelo.Batalla;
import uy.edu.ort.battleshipdemo.Modelo.Batalla.Estados;

public class EstadoEnCombate extends EstadoBatalla {
    public EstadoEnCombate(Batalla batalla) {
        super(batalla, Estados.enCombate, "En combate");
    }

    // Con Nati armamos la base, ahora hay que sobrescribir las operaciones del State según este estado.
}
