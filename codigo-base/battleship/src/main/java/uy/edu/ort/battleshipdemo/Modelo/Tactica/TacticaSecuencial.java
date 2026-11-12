package uy.edu.ort.battleshipdemo.Modelo.Tactica;

import uy.edu.ort.battleshipdemo.Modelo.Disparo;

public class TacticaSecuencial extends Tactica {

    private int proximoObjetivo = 0;

    public TacticaSecuencial() {
        super("Secuencial");
    }

    @Override
    public int[] seleccionarObjetivo(int filas, int columnas) {
        int fila = (proximoObjetivo / columnas);
        int columna = proximoObjetivo % columnas;

        proximoObjetivo++;

        return new int[] { fila, columna };
    }

   
}
