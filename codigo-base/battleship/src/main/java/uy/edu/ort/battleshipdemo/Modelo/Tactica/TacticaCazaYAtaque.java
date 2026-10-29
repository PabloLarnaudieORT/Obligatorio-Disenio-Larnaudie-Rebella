package uy.edu.ort.battleshipdemo.Modelo.Tactica;

import uy.edu.ort.battleshipdemo.Modelo.Disparo;

public class TacticaCazaYAtaque extends Tactica {
public TacticaCazaYAtaque() {
        super("Caza y ataque");
    }

    @Override
    public int[] seleccionarObjetivo(int filas, int columnas) {
        // TODO: implementar la selección de objetivo de Caza y ataque.
        throw new UnsupportedOperationException("Pendiente: seleccionar objetivo.");
    }

    @Override
    public void registraResultado(Disparo disparo) {
        // TODO: decidir qué información necesita conservar Caza y ataque de este disparo.
        throw new UnsupportedOperationException("Pendiente: registrar resultado.");
    }
}
