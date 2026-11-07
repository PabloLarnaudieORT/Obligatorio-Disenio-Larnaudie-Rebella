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
                //aumentamos 1 la posicion de la columna
                columnaActual += i;
            } else {
                //aumentamos 1 la posicion de la fila
                filaActual += i;
            }
            validarSeparacion(filaActual, columnaActual);
        }

    }

    //EXPLICACION PARA NATI 
    //Consulte con la IA por tener 2 for... no rompe experto.
    //es simplemente para poder recorrer las filas y columnas
    // validamos validarSeparacion(2,3)
    private void validarSeparacion(int fila, int columna) throws BatallaException {
        // Implementación de la validación de separación entre barcos
        // Vamos a ir a la fila de al lado a la que queremos ubicar
        // fila es la celda central. => 2.
        // fila - 1, es la fila de abajo => 1.
        // mientras (1) sea menor o igual a fila(2)+1 = 3. sigue...
        // 1 fila tiene 3 columnas... antes de sumar una fila hay 3 fors a ser completados.
        for(int filaDeAlLado = fila - 1; filaDeAlLado <= fila + 1; filaDeAlLado++) {
            //Aca la costa es igual... columna es 3.
            //columnaDeAlLado = 2. (columna 3-1 = 2).
            //mientras que 2 sigue siendo menor o igual a 3+1 = 4. sigue...
            for(int columnaDeAlLado = columna - 1; columnaDeAlLado <= columna + 1; columnaDeAlLado++) {
                //entonces, si la fila no es negativa (filaDeAlLado >= 0 )
                //si no supera el valor de las filas valido ( < filas )
                //si la columna no es negativa (columnaDeAlLado >= 0 )
                //Si no supera el valor de las columnas valido ( < columnas )
                if(filaDeAlLado >= 0 && filaDeAlLado < filas && columnaDeAlLado >= 0 && columnaDeAlLado < columnas) {
                    Celda celdaOcupada = celdas[filaDeAlLado][columnaDeAlLado];
                    Barco hayBarco = this.getFlota().buscarBarco(celdaOcupada);
                    if(hayBarco != null) {
                        throw new BatallaException("El barco se superpone o toca otro barco.");
                    }
                }

            }
        }
    }

    public void ubicarBarco(Barco barco, int fila, int columna, boolean horizontal) throws BatallaException {
        // primero vamos a tener que validar la ubicacion del barco
        validarUbicacion(barco, fila, columna, horizontal);
        int tamañoDelBarco = barco.getTipo().getTamanio();
        for (int i = 0; i < tamañoDelBarco; i++) {
            //misma logica que antes, igualamos a fila y a columna y 
            //luego avanzamos posicion a posicion
            int filaActual = fila;
            int columnaActual = columna;
            if (horizontal) {
                columnaActual += i;
            } else {
                filaActual += i;
            }
            Celda celda = celdas[filaActual][columnaActual];
            barco.agregarCeldaOcupada(celda);
        }
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
