package uy.edu.ort.battleshipdemo.Modelo.EstadosBatalla;

import uy.edu.ort.battleshipdemo.Modelo.Batalla;
import uy.edu.ort.battleshipdemo.Modelo.Batalla.Estados;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;

public class EstadoEnPreparacion extends EstadoBatalla {
    public EstadoEnPreparacion(Batalla batalla) {
        super(batalla, Estados.enPreparacion, "En preparación");
    }

   
// En EstadoBatalla.js armamos la base, ahora se deben sobrescribir las operaciones del State según este estado.

@Override
    public EstadoBatalla enCombate() throws BatallaException {
        return new EstadoEnCombate(batalla);
    }

    @Override
    public EstadoBatalla enPreparacion() throws BatallaException {
        throw new BatallaException("La batalla ya está en preparación");
    }

    @Override
    public EstadoBatalla ganada() throws BatallaException {
        throw new BatallaException("No se puede ganar una batalla antes del combate");
    }

    @Override
    public EstadoBatalla perdida() throws BatallaException {
        throw new BatallaException("No se puede perder una batalla antes del combate");
    }

}
