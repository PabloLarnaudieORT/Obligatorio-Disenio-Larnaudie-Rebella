package uy.edu.ort.battleshipdemo.Modelo;

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

    public void validar() {
        // filas y columnas entre 6 y 12, inclusive.
        // flota no nula hayq que validar cada DetalleFlota.
        // La unicidad del lugar corresponde al futuro SistemaBatallas.
    }

    public int calcularOcupados() {
        // sumar calcularCeldas() de cada detalle, aplicando Experto.
    }
    
}
