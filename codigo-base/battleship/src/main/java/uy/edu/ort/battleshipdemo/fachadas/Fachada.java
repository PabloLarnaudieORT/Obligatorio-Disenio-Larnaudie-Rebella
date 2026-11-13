package uy.edu.ort.battleshipdemo.fachadas;

import uy.edu.ort.battleshipdemo.servicios.SistemaTiposBarco;
import uy.edu.ort.battleshipdemo.servicios.SistemaBatallas;
import uy.edu.ort.battleshipdemo.Modelo.TipoBarco;
import uy.edu.ort.battleshipdemo.Modelo.Batalla;
import java.util.List;
import uy.edu.ort.battleshipdemo.excepciones.TipoBarcoException;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;
import uy.edu.ort.battleshipdemo.Modelo.Ronda;
import uy.edu.ort.battleshipdemo.Modelo.Eventos.ObservadorBatalla;



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

    public TipoBarco buscarTipoBarco(String nombreDelTipoBarco) {
        return sistemaTiposBarco.buscarTipoBarco(nombreDelTipoBarco);
    }

    public void ubicarBarco(String lugarBatalla, int indiceBarco, int fila, int columna, boolean horizontal)
            throws BatallaException {
        sistemaBatallas.ubicarBarco(lugarBatalla, indiceBarco, fila, columna, horizontal);
    }

    public void validarFlotaParaLibrarBatalla(String lugarBatalla) throws BatallaException {
        sistemaBatallas.validarFlotaParaLibrarBatalla(lugarBatalla);
    }

    public int contarBatallasPorEstado(Batalla.Estados estadoBuscado) {
        return sistemaBatallas.contarBatallasPorEstado(estadoBuscado);
    }

    public double calcularPorcentajeExito() {
        return sistemaBatallas.calcularPorcentajeExito();
    }






public void iniciarCombate(String lugarBatalla) throws BatallaException {
    sistemaBatallas.iniciarCombate(lugarBatalla);
}

public Ronda jugarRonda(
        String lugarBatalla, int fila, int columna, int numeroRondaEsperada)
        throws BatallaException {

    return sistemaBatallas.jugarRonda(
            lugarBatalla, fila, columna, numeroRondaEsperada);
}

public void agregarObservador(
        String lugarBatalla, ObservadorBatalla observador)
        throws BatallaException {

    sistemaBatallas.agregarObservador(lugarBatalla, observador);
}

public void quitarObservador(
        String lugarBatalla, ObservadorBatalla observador)
        throws BatallaException {

    sistemaBatallas.quitarObservador(lugarBatalla, observador);
}

public void agregarObservadorSala(ObservadorBatalla observador) {
    sistemaBatallas.agregarObservadorSala(observador);
}

public void quitarObservadorSala(ObservadorBatalla observador) {
    sistemaBatallas.quitarObservadorSala(observador);
}
}
