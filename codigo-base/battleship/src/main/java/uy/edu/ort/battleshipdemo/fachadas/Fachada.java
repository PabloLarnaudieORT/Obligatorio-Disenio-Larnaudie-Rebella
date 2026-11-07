package uy.edu.ort.battleshipdemo.fachadas;
import uy.edu.ort.battleshipdemo.servicios.SistemaTiposBarco;
import uy.edu.ort.battleshipdemo.Modelo.TipoBarco;
import java.util.List;
import uy.edu.ort.battleshipdemo.excepciones.TipoBarcoException;

public class Fachada {
    private static Fachada instancia;
    private SistemaTiposBarco sistemaTiposBarco = SistemaTiposBarco.getInstancia();

    public static Fachada getInstancia() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }
    private Fachada() {
    }

    public List<TipoBarco> getTiposBarco() {
        return sistemaTiposBarco.getTiposBarco();
    }
    public void agregarTipoBarco(TipoBarco tipo) throws TipoBarcoException {
        sistemaTiposBarco.agregarTipoBarco(tipo);
    }

    
}
