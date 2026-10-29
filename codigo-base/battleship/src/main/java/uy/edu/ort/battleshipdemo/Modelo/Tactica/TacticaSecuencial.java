package uy.edu.ort.battleshipdemo.Modelo.Tactica;

import uy.edu.ort.battleshipdemo.Modelo.Disparo;

public class TacticaSecuencial extends Tactica {
    public TacticaSecuencial() {
        super("Secuencial");
    }

    @Override
    public int[] seleccionarObjetivo(int filas, int columnas) {
        // TODO: implementar la selección de objetivo de Secuencial.
        throw new UnsupportedOperationException("Pendiente: seleccionar objetivo.");
    }

    @Override
    public void registraResultado(Disparo disparo) {
        // TODO: decidir qué información necesita conservar Secuencial de este disparo.
        throw new UnsupportedOperationException("Pendiente: registrar resultado.");
    }
}
