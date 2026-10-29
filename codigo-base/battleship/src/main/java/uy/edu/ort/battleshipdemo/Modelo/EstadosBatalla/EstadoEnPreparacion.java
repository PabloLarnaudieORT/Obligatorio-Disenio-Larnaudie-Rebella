package uy.edu.ort.battleshipdemo.Modelo.EstadosBatalla;

import uy.edu.ort.battleshipdemo.Modelo.Batalla;
import uy.edu.ort.battleshipdemo.Modelo.Batalla.Estados;

public class EstadoEnPreparacion extends EstadoBatalla {
    public EstadoEnPreparacion(Batalla batalla) {
        super(batalla, Estados.enPreparacion, "En preparación");
    }


    // Con Nati armamos la base, ahora hay que sobrescribir las operaciones del State según este estado.
}
