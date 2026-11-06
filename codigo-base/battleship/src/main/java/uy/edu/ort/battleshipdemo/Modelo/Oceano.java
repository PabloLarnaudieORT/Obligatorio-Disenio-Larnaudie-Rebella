package uy.edu.ort.battleshipdemo.Modelo;

import uy.edu.ort.battleshipdemo.excepciones.BatallaException;
import java.util.List;

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

    //Aca vamos a validar celda con orientacion para el barco.
    public void validarUbicacion(Barco barco, int fila, int columna, boolean horizontal) throws BatallaException {
        //verifico que el barco no sea null...
        if(barco == null) {
            throw new BatallaException("El barco no puede ser nulo.");
        }
        //obtengo todos los barcos
        List<Barco> barcos = flota.getBarcos();
        //verifico que incluya al barco.
        boolean incluyeBarco = barcos.contains(barco);
        //si no lo incluye largo un error por que no pertenece a la flota.
        if (!incluyeBarco) {
            throw new BatallaException("El barco no pertenece a la flota.");
        }
        //vamos a gaurdar las celdas del barco para validarlas
        List<Celda> celdasBarco = barco.getCeldas();
        //el barco tiene celdas, si fue posicionado
        if(!celdasBarco.isEmpty()) {//si fuese if(celdasBarco.isEmpty()) -> no esta posicionado.
            throw new BatallaException("El barco ya esta posicionado.");
        }
        //voy a comprobar si la fila o la columna estan fuera del tablero
        //fila y columna indican donde empieza el barco.
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
            throw new BatallaException("La posición está fuera del tablero.");
        }
        int tamañoBarco = barco.getTipo().getTamanio();
        int filaFinal = fila;
        int columnaFinal = columna;
        if(horizontal) {
            //hay que restarle - 1 por que sino tenemos un largo mas grande...
            columnaFinal += tamañoBarco - 1;
        } else {
            filaFinal += tamañoBarco - 1;
        }
        //ahora, compruebo el final de la columna y de la fila a ver si caen afuera del tablero
        if(filaFinal >= filas || columnaFinal >= columnas) {
            throw new BatallaException("El barco no cabe en la posición indicada.");
        }
        //falta saber que hay entre medio... sabemos que entra, pero no si hay algo en el medio.
        for (int i = 0; i < tamañoBarco; i++) {
            int filaActual = fila;
            int columnaActual = columna;
            if(horizontal) {
                columnaActual += i;
            } else {
                filaActual += i;
            }
            //cambie la logica, aca guardo celda
            Celda celdaOcupada = celdas[filaActual][columnaActual];
            //luego busco el barco y si no es null, significa
            // que hay un barco en esa celda.
            Barco barcoOcupado = this.getFlota().buscarBarco(celdaOcupada);
            if(barcoOcupado != null) {
                throw new BatallaException("La celda ocupada.");
            }
        }

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
