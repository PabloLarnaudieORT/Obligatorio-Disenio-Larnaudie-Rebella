package uy.edu.ort.battleshipdemo.Modelo;

import uy.edu.ort.battleshipdemo.excepciones.BatallaException;

public class Oceano {
    private int filas;
    private int columnas;
    private Celda[][] celdas;
    private Flota flota = new Flota();

    public Oceano() {
    }

    public Oceano(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        generarOceano(filas, columnas);
    }

    private void generarOceano(int filas,int columnas) {
        // Implementación de la generación del océano con celdas
        celdas = new Celda[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda(i, j, false);
            }
        }
    }




   public void validarUbicacion(
        Barco barco, int fila, int columna, boolean horizontal)
        throws BatallaException {

    if (barco == null || !flota.getBarcos().contains(barco)) {
        throw new BatallaException("El barco no pertenece a esta flota");
    }

    if (barco.getTipo() == null
            || barco.getTipo().getTamanio() <= 0) {
        throw new BatallaException("El barco no tiene un tipo válido");
    }

    int tamanio = barco.getTipo().getTamanio();

    for (int i = 0; i < tamanio; i++) {
        int filaBarco = fila + (horizontal ? 0 : i);
        int columnaBarco = columna + (horizontal ? i : 0);

        if (filaBarco < 0 || filaBarco >= filas
                || columnaBarco < 0 || columnaBarco >= columnas) {
            throw new BatallaException("El barco queda fuera del océano");
        }

        if (hayBarcoCerca(barco, filaBarco, columnaBarco)) {
            throw new BatallaException("Los barcos no pueden tocarse");
        }
    }
}



private boolean hayBarcoCerca(Barco barco, int fila, int columna) {
    int filaDesde = Math.max(0, fila - 1);
    int filaHasta = Math.min(filas - 1, fila + 1);
    int columnaDesde = Math.max(0, columna - 1);
    int columnaHasta = Math.min(columnas - 1, columna + 1);

    for (int f = filaDesde; f <= filaHasta; f++) {
        for (int c = columnaDesde; c <= columnaHasta; c++) {
            Barco barcoVecino = flota.barcoEn(celdas[f][c]);

            if (barcoVecino != null && barcoVecino != barco) {
                return true;
            }
        }
    }

    return false;
}


public void ubicarBarco(
        Barco barco, int fila, int columna, boolean horizontal)
        throws BatallaException {

    validarUbicacion(barco, fila, columna, horizontal);

    barco.getCeldas().clear();

    for (int i = 0; i < barco.getTipo().getTamanio(); i++) {
        int filaBarco = fila + (horizontal ? 0 : i);
        int columnaBarco = columna + (horizontal ? i : 0);

        barco.getCeldas().add(celdas[filaBarco][columnaBarco]);
    }
}



    public Disparo recibirDisparo(int fila, int columna) throws BatallaException {
        if (celdas == null || fila < 0 || fila >= filas
                || columna < 0 || columna >= columnas) {
            throw new BatallaException("La coordenada del disparo no es válida");
        }

        Celda celda = celdas[fila][columna];
        if (celda.isDisparada()) {
            throw new BatallaException("Esa celda ya recibió un disparo");
        }

        celda.setDisparada(true);

        Barco barco = flota.barcoEn(celda);
        String textoResultado;
        if (barco == null) {
            textoResultado = "agua";
        } else if (barco.estaHundido()) {
            textoResultado = "barco hundido";
        } else {
            textoResultado = "impacto";
        }

        Resultado resultado = new Resultado(textoResultado);
        return new Disparo(celda, resultado);
    }


    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }
    public Celda[][] getCeldas() { return celdas; }
    public Flota getFlota() { return flota; }
    public void setFlota(Flota flota) { this.flota = flota; }

}
