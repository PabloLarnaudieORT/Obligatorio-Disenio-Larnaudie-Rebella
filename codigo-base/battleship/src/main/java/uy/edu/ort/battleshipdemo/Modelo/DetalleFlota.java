package uy.edu.ort.battleshipdemo.Modelo;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;

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
        //3 acorazados de tamaño 4, ocupan 12 celdas...
        // con Nati evaluamos esta opcion para saber cuanto espacio precisamos
        return cantidadBarcos * tipo.getTamanio();
    }

    public void validar()  throws BatallaException {
        if (tipo == null) {
            throw new BatallaException("El tipo de barco no puede ser nulo.");
        }
        if (cantidadBarcos < 0 || cantidadBarcos > 6) {
            throw new BatallaException("La cantidad de barcos debe estar entre 0 y 6.");
        }
    }
}