package uy.edu.ort.battleshipdemo.Modelo.EstadosBatalla;

import uy.edu.ort.battleshipdemo.Modelo.Batalla;
import uy.edu.ort.battleshipdemo.Modelo.Batalla.Estados;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;

public class EstadoPerdida extends EstadoBatalla {
    public EstadoPerdida(Batalla batalla) {
        super(batalla, Estados.perdida, "Perdida");
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
        throw new BatallaException("Una batalla perdida no puede pasar a ganada");
    }

    @Override
    public EstadoBatalla perdida() throws BatallaException {
        throw new BatallaException("La batalla ya está perdida");
    }


}
