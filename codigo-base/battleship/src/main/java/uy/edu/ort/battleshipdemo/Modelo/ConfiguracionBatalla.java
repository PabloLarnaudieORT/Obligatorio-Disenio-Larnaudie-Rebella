package uy.edu.ort.battleshipdemo.Modelo;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;

public class ConfiguracionBatalla {
    private int filas;
    private int columnas;
    private DetalleFlota[] flota;
    
    public ConfiguracionBatalla() {
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public DetalleFlota[] getFlota() {
        return flota;
    }

    public void setFlota(DetalleFlota[] flota) {
        this.flota = flota;
    }

    public void validar() throws BatallaException {
        // filas y columnas entre 6 y 12, inclusive.
        if (filas < 6 || filas > 12) {
            throw new BatallaException("El número de filas debe estar entre 6 y 12.");
        }
        if (columnas < 6 || columnas > 12) {
            throw new BatallaException("El número de columnas debe estar entre 6 y 12.");
        }
        // flota no nula hayq que validar cada DetalleFlota.
        if(flota == null || flota.length == 0) {
            throw new BatallaException("La flota no puede ser nula o vacía.");
        }
        //Si todo lo anterior no falló, entonces recorro la flota
        for (DetalleFlota detalle : flota) {
            if(detalle == null) {
                throw new BatallaException("Detalle de flota no puede ser nulo.");
            }
            detalle.validar();
        }


    }

    public int calcularOcupados() {
        // sumar calcularCeldas() de cada detalle, aplicando Experto.
        int totalEspacioOcupado = 0;
        for (DetalleFlota detalle : flota) {
            totalEspacioOcupado += detalle.calcularCeldas();
        }
        return totalEspacioOcupado;
    }
    
}
