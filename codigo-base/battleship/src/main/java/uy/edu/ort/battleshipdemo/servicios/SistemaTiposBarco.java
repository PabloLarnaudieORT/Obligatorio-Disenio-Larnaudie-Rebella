package uy.edu.ort.battleshipdemo.servicios;
import uy.edu.ort.battleshipdemo.Modelo.TipoBarco;
import java.util.ArrayList;
import java.util.List;
import uy.edu.ort.battleshipdemo.excepciones.TipoBarcoException;

public class SistemaTiposBarco {
    private List<TipoBarco> tiposBarco;

    private static SistemaTiposBarco instancia;

    public static SistemaTiposBarco getInstancia() {
        if (instancia == null) {
            instancia = new SistemaTiposBarco();
        }
        return instancia;
    }
    private SistemaTiposBarco() {
        this.tiposBarco = new ArrayList<>();
    }

    public void agregarTipoBarco(TipoBarco tipo) throws TipoBarcoException {
        if (tipo == null) {
            throw new TipoBarcoException("El tipo de barco no puede ser nulo");
        }
        this.tiposBarco.add(tipo);
    }

    public List<TipoBarco> getTiposBarco() {
        return tiposBarco;
    }

    public TipoBarco buscarTipoBarco(String nombreTipoBarco){
        for(TipoBarco tipoBarco : tiposBarco){
            if(tipoBarco.getNombre().equals(nombreTipoBarco)){
                return tipoBarco;
            }
        }
        return null;
    }
}
