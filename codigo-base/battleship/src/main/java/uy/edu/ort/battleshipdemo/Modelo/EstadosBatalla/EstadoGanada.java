package uy.edu.ort.battleshipdemo.Modelo.EstadosBatalla;

import uy.edu.ort.battleshipdemo.Modelo.Batalla;
import uy.edu.ort.battleshipdemo.Modelo.Batalla.Estados;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;


public class EstadoGanada extends EstadoBatalla {
    public EstadoGanada(Batalla batalla) {
        super(batalla, Estados.ganada, "Ganada");
    }

@Override
    public EstadoBatalla enCombate() throws BatallaException {
        throw new BatallaException("La batalla ya terminó");
    }

    @Override
    public EstadoBatalla enPreparacion() throws BatallaException {
        throw new BatallaException("No se puede volver a preparación");
    }

    @Override
    public EstadoBatalla ganada() throws BatallaException {
        throw new BatallaException("La batalla ya está ganada");
    }

    @Override
    public EstadoBatalla perdida() throws BatallaException {
        throw new BatallaException("Una batalla ganada no puede pasar a perdida");
    }

}
