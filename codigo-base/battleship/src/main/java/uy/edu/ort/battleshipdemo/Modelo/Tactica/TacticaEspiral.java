package uy.edu.ort.battleshipdemo.Modelo.Tactica;

import java.util.ArrayList;
import java.util.List;

public class TacticaEspiral extends Tactica {

     private List<int[]> objetivos = new ArrayList<>();
     private int proximoObjetivo = 0;


    public TacticaEspiral() {
        super("Espiral");
    }

    @Override
    public int[] seleccionarObjetivo(int filas, int columnas) {
        if (objetivos.isEmpty()) {
            generarEspiral(filas, columnas);
        }

        int[] objetivo = objetivos.get(proximoObjetivo);
        proximoObjetivo++;

        return objetivo;
    }

    private void generarEspiral(int filas, int columnas) {
        int fila = (filas - 1) / 2;
        int columna = (columnas - 1) / 2;

        agregarObjetivo(fila, columna, filas, columnas);

        int pasos = 1;

        while (objetivos.size() < filas * columnas) {
            for (int i = 0; i < pasos; i++) {
                columna++;
                agregarObjetivo(fila, columna, filas, columnas);
            }

            for (int i = 0; i < pasos; i++) {
                fila++;
                agregarObjetivo(fila, columna, filas, columnas);
            }

            pasos++;

            for (int i = 0; i < pasos; i++) {
                columna--;
                agregarObjetivo(fila, columna, filas, columnas);
            }

            for (int i = 0; i < pasos; i++) {
                fila--;
                agregarObjetivo(fila, columna, filas, columnas);
            }

            pasos++;
        }
    }

    private void agregarObjetivo(
            int fila, int columna, int filas, int columnas) {
        if (fila >= 0 && fila < filas
                && columna >= 0 && columna < columnas) {
            objetivos.add(new int[] { fila, columna });
        }
    }
}
