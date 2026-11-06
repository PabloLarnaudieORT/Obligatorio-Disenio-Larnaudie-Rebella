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

    // OJO ACA HAY UN FOR DENRTO DE OTRO FOR, EXPERTO?
    // lo dejo asi, no encuentro alternativa de momento, luego lo pienso
    private void generarOceano(int filas, int columnas) {
        // Implementación de la generación del océano con celdas
        celdas = new Celda[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda(i, j, false);
            }
        }
    }

    public void validarUbicacion(Barco barco, int fila, int columna, boolean horizontal) {
        // barco de esta flota, límites, superposición y separación incluso diagonal.
    }

    public void ubicarBarco(Barco barco, int fila, int columna, boolean horizontal) {
        // validar primero y luego asignar todas las celdas al barco.
    }

    public Disparo recibirDisparo(int fila, int columna) throws BatallaException {
        // Nati, aca si una coordenada fila o columna esta fuera del rango, ya hay un
        // error.
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
            throw new BatallaException("La posición está fuera del tablero.");
        }
        // guardo las celdas en una variable
        Celda celda = celdas[fila][columna];
        // consulto si fue disparada.
        if (celda.isDisparada()) {
            throw new BatallaException("Ya se ha disparado a esta celda.");
        }
        Barco barco = this.getFlota().buscarBarco(celda);
        if (barco != null) {
            // si hay un barco voy a disparar
            celda.setDisparada(true);
            boolean seHundio = barco.estaHundido();
            if (seHundio) {
                Resultado resultado1 = new Resultado("Barco hundido");
                return new Disparo(celda, resultado1);
            } else {
                Resultado resultado1 = new Resultado("Impacto");
                return new Disparo(celda, resultado1);
            }
        } else {
            // si no hay barco, voy a disparar igual PERO el disparo no tiene barco
            celda.setDisparada(true);
            Resultado resultado2 = new Resultado("Agua");
            return new Disparo(celda, resultado2);
        }
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public Celda[][] getCeldas() {
        return celdas;
    }

    public Flota getFlota() {
        return flota;
    }

    public void setFlota(Flota flota) {
        this.flota = flota;
    }

}
