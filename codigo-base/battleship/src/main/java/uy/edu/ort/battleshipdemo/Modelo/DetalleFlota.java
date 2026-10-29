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
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidadBarcos = cantidad;
    }
    
    //calcularCeldas (?)
    public void calcularCantidadBarcos() {

    }
}