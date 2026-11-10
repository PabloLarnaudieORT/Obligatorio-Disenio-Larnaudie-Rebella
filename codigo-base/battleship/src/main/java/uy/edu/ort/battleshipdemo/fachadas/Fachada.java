package uy.edu.ort.battleshipdemo.fachadas;
import uy.edu.ort.battleshipdemo.servicios.SistemaTiposBarco;
import uy.edu.ort.battleshipdemo.servicios.SistemaBatallas;
import uy.edu.ort.battleshipdemo.Modelo.TipoBarco;
import uy.edu.ort.battleshipdemo.Modelo.Batalla;
import java.util.List;
import uy.edu.ort.battleshipdemo.excepciones.TipoBarcoException;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;

public class Fachada {
    private static Fachada instancia;
    private SistemaTiposBarco sistemaTiposBarco = SistemaTiposBarco.getInstancia();
    private SistemaBatallas sistemaBatallas = SistemaBatallas.getInstancia();

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

    public List<Batalla> getBatallas() {
        return sistemaBatallas.getBatallas();
    }

    public void agregarBatalla(Batalla batalla) throws BatallaException {
        sistemaBatallas.agregarBatalla(batalla);
    }

    public Batalla buscarBatalla(String nombreLugar) {
        return sistemaBatallas.buscarBatalla(nombreLugar);
    }

    public TipoBarco buscarTipoBarco(String nombreDelTipoBarco){
        return sistemaTiposBarco.buscarTipoBarco(nombreDelTipoBarco);
    }

    public void ubicarBarco(String lugarBatalla, int indiceBarco, int fila, int columna, boolean horizontal)throws BatallaException{
        sistemaBatallas.ubicarBarco(lugarBatalla, indiceBarco, fila, columna, horizontal);
    }

    public int contarBatallasPorEstado(Batalla.Estados estadoBuscado){
        return sistemaBatallas.contarBatallasPorEstado(estadoBuscado);
    }

    public double calcularPorcentajeExito(){
        return sistemaBatallas.calcularPorcentajeExito();
    }

}
