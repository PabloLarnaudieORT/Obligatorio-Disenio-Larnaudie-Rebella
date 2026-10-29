package uy.edu.ort.battleshipdemo.Modelo.EstadosBatalla;

import uy.edu.ort.battleshipdemo.Modelo.Batalla.Estados;

public class EstadoEnPreparacion extends EstadoBatalla {
    public EstadoEnPreparacion(Batalla batalla) {
        super(batalla, Estados.enPrearacion);
    }
}
