package uy.edu.ort.battleshipdemo.Modelo.Tactica;

import uy.edu.ort.battleshipdemo.Modelo.Disparo;

public class TacticaEspiral extends Tactica {
    public TacticaEspiral() {
        super("Espiral");
    }

    @Override
    public int[] seleccionarObjetivo(int filas, int columnas) {
        // TODO: implementar la selección de objetivo de Espiral.
        throw new UnsupportedOperationException("Pendiente: seleccionar objetivo.");
    }

    @Override
    public void registraResultado(Disparo disparo) {
        // TODO: decidir qué información necesita conservar Espiral de este disparo.
        throw new UnsupportedOperationException("Pendiente: registrar resultado.");
    }
}
