package uy.edu.ort.battleshipdemo.Modelo;

public class DetalleFlota {

    private TipoBarco tipo;
    private int cantidadBarcos;

    public DetalleFlota() {
    }

    public DetalleFlota(TipoBarco tipo, int cantidad) {
        this.tipo = tipo;
        this.cantidadBarcos = cantidad;
    }

    public TipoBarco getTipo() {
        return tipo;
    }

    public void setTipo(TipoBarco tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidadBarcos;
    }

    public void setCantidad(int cantidad) {
        this.cantidadBarcos = cantidad;
    }
    
    public int calcularCeldas() {
        // cantidadBarcos multiplicada por el tamaño de tipo.
    }

    public void validar() {
        // tipo no nulo y cantidadBarcos entre 0 y 6, inclusive.
    }
}