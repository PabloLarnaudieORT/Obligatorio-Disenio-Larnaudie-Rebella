package uy.edu.ort.battleshipdemo.Modelo.EstadosBatalla;

import uy.edu.ort.battleshipdemo.Modelo.Batalla;
import uy.edu.ort.battleshipdemo.Modelo.Batalla.Estados;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;


public class EstadoEnCombate extends EstadoBatalla {
    public EstadoEnCombate(Batalla batalla) {
        super(batalla, Estados.enCombate, "En combate");
    }

@Override
    public EstadoBatalla enCombate() throws BatallaException {
        throw new BatallaException("La batalla ya está en combate");
    }

    @Override
    public EstadoBatalla enPreparacion() throws BatallaException {
        throw new BatallaException("No se puede volver a preparación durante el combate");
    }

    @Override
    public EstadoBatalla ganada() throws BatallaException {
        return new EstadoGanada(batalla);
    }

    @Override
    public EstadoBatalla perdida() throws BatallaException {
        return new EstadoPerdida(batalla);
    }
    
}
