package uy.edu.ort.battleshipdemo.Modelo.EstadosBatalla;

import uy.edu.ort.battleshipdemo.Modelo.Batalla;
import uy.edu.ort.battleshipdemo.Modelo.Batalla.Estados;

public class EstadoPerdida extends EstadoBatalla {
    public EstadoPerdida(Batalla batalla) {
        super(batalla, Estados.perdida, "Perdida");
    }



    // Con Nati armamos la base, ahora hay que sobrescribir las operaciones del State según este estado.
}
