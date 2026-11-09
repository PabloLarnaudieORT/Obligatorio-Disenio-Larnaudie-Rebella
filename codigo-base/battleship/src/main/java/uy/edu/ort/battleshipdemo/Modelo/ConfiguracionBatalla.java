package uy.edu.ort.battleshipdemo.Modelo;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;
import java.util.List;

public class ConfiguracionBatalla {
    private int filas;
    private int columnas;
    private List<DetalleFlota> flotas;
    
    public ConfiguracionBatalla() {
    }

    public ConfiguracionBatalla(List<DetalleFlota> flotas) {
        this.flotas = flotas;
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

    public List<DetalleFlota> getFlota() {
        return flotas;
    }

    public void setFlota(List<DetalleFlota> flotas) {
        this.flotas = flotas;
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
        if(flotas == null || flotas.isEmpty()) {
            throw new BatallaException("La flota no puede ser nula o vacía.");
        }
        //Si todo lo anterior no falló, entonces recorro la flota
        for (DetalleFlota detalle : flotas) {
            if(detalle == null) {
                throw new BatallaException("Detalle de flota no puede ser nulo.");
            }
            detalle.validar();
        }
        int totalOcupado = this.calcularOcupados();
        if(totalOcupado == 0) {
            throw new BatallaException("La flota no puede estar vacía.");
        }
        int tamañoTablero = filas * columnas;
        if((totalOcupado * 4) >= tamañoTablero) {
            throw new BatallaException("La ocupacion debe ser menor al 25%.");
        }

    }

    public int calcularOcupados() {
        // sumar calcularCeldas() de cada detalle, aplicando Experto.
        int totalEspacioOcupado = 0;
        for (DetalleFlota detalle : flotas) {
            totalEspacioOcupado += detalle.calcularCeldas();
        }
        return totalEspacioOcupado;
    }

    public Flota crearFlota() throws BatallaException{
        validar();
        Flota nuevaFlota = new Flota();
        for(DetalleFlota detalle : flotas){
            List<Barco> barcos = detalle.crearBarcos();
            for(Barco barco:barcos){
                nuevaFlota.agregarBarco(barco);
            }
        }
        return nuevaFlota;
    }
    
}
