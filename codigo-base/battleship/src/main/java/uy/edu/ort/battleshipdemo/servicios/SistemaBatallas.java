package uy.edu.ort.battleshipdemo.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uy.edu.ort.battleshipdemo.Modelo.Batalla;
import uy.edu.ort.battleshipdemo.Modelo.Oceano;
import uy.edu.ort.battleshipdemo.Modelo.Flota;
import uy.edu.ort.battleshipdemo.Modelo.Barco;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;
import uy.edu.ort.battleshipdemo.Modelo.Ronda;
import uy.edu.ort.battleshipdemo.Modelo.Eventos.ObservadorBatalla;
import uy.edu.ort.battleshipdemo.Modelo.Eventos.EventoBatalla;


public class SistemaBatallas implements ObservadorBatalla {

    private List<Batalla> batallas;

    private List<ObservadorBatalla> observadores = new ArrayList<>();

    private static SistemaBatallas instancia;

    public static SistemaBatallas getInstancia() {
        if (instancia == null) {
            instancia = new SistemaBatallas();
        }
        return instancia;
    }

    private SistemaBatallas() {
        this.batallas = new ArrayList<>();
    }

    // Nati, letra dice que hay que generar nombre al azar si queda vacio.
    private String generarLugar() {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random azar = new Random(); // averigue como generar algo random y es asi.
        String nombreDelLugar = "";
        // intente usando for... pero no funciono
        // entonces usé do while, para ver si el nombre esta ocupado
        do {
            nombreDelLugar = "";
            // le pregunté a la IA como generar un nombre al azar.
            for (int i = 0; i < 6; i++) {
                int posicion = azar.nextInt(letras.length());
                nombreDelLugar += letras.charAt(posicion);
            }
        } while (buscarBatalla(nombreDelLugar) != null);
        return nombreDelLugar;
    }

    public void agregarBatalla(Batalla batalla) throws BatallaException {
        if (batalla == null) {
            throw new BatallaException("La batalla no puede ser nula");
        }
        String nombreLugar = batalla.getLugar();
        // Probando, me di cuenta que faltaba un trim()
        if (nombreLugar == null || nombreLugar.trim().isEmpty()) {
            // debido a que no puede ser vacio el nombre, vamos a poner generarLugar
            nombreLugar = generarLugar();
            batalla.setLugar(nombreLugar);
        }
        if (buscarBatalla(nombreLugar) != null) {
            throw new BatallaException("No se puede librar más de una batalla en el mismo lugar, cambie de nombre");
        }
        this.batallas.add(batalla);
        batalla.agregarObservador(this);
    }

    public List<Batalla> getBatallas() {
        return batallas;
    }

    public Batalla buscarBatalla(String nombreLugar) {
        for (Batalla batalla : batallas) {
            if (batalla.getLugar().equals(nombreLugar)) {
                return batalla;
            }
        }
        // no devuelvo excepcion, por que si el lugar esta disponible
        // no puedo tirar una excepcion batalla...
        return null;
    }

    public void ubicarBarco(String lugarBatalla, int indiceBarco, int fila, int columna, boolean horizontal)
            throws BatallaException {
        Batalla batalla = buscarBatalla(lugarBatalla);
        if (batalla == null) {
            throw new BatallaException("La batalla indicada no existe.");
        }
        // para ubicar el barco, tenemos que conocer el oceanno del jugador
        Oceano oceano = batalla.getJugadorHumano().getOceano();
        // aca buscamos la flota que tenga el oceano que tenia el jugador...
        Flota flota = oceano.getFlota();
        // y a su vez, buscamos el barco que tiene la flota por su indice.
        Barco barco = flota.buscarUnBarcoPorSuIndice(indiceBarco);
        oceano.ubicarBarco(barco, fila, columna, horizontal);

    }

    //Esto lo vamos a usar para mostrar estadisticas de la campaña
    public int contarBatallasPorEstado(Batalla.Estados estadoBuscado) {
        int cantidad = 0;
        for (Batalla batalla : batallas) {
            Batalla.Estados estadoActual = batalla.getEstado().getEstado();
            if (estadoActual == estadoBuscado) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public double calcularPorcentajeExito(){
        int batallasTotal = batallas.size();
        //aca evitamos dividir por cero
        if(batallasTotal == 0){
            return 0.0;
        }
        int ganados = contarBatallasPorEstado(Batalla.Estados.ganada);
        return (100.0 * ganados / batallasTotal);
    }

    public void validarFlotaParaLibrarBatalla(String lugarBatalla) throws BatallaException{
        Batalla batalla = buscarBatalla(lugarBatalla);
        if(batalla == null){
            throw new BatallaException("La batalla ingresada no existe");
        }
        Oceano oceano = batalla.getJugadorHumano().getOceano();
        if(!oceano.estanTodosUbicados()){
            throw new BatallaException("Debe posicionar todos sus barcos primero");
        }
    }





    public void iniciarCombate(String lugarBatalla) throws BatallaException {
    Batalla batalla = buscarBatalla(lugarBatalla);

    if (batalla == null) {
        throw new BatallaException("La batalla indicada no existe");
    }

    batalla.iniciarCombate();
}

public Ronda jugarRonda(
        String lugarBatalla, int fila, int columna, int numeroRondaEsperada)
        throws BatallaException {

    Batalla batalla = buscarBatalla(lugarBatalla);

    if (batalla == null) {
        throw new BatallaException("La batalla indicada no existe");
    }

    return batalla.jugarRonda(fila, columna, numeroRondaEsperada);
}

public void agregarObservador(
        String lugarBatalla, ObservadorBatalla observador)
        throws BatallaException {

    Batalla batalla = buscarBatalla(lugarBatalla);

    if (batalla == null) {
        throw new BatallaException("La batalla indicada no existe");
    }

    if (!observadores.contains(observador)) {
    observadores.add(observador);
}
}

public void quitarObservador(
        String lugarBatalla, ObservadorBatalla observador)
        throws BatallaException {

    Batalla batalla = buscarBatalla(lugarBatalla);

    if (batalla == null) {
        throw new BatallaException("La batalla indicada no existe");
    }

observadores.remove(observador);
}


@Override
public void actualizar(EventoBatalla evento) {
    for (ObservadorBatalla observador : observadores) {
        observador.actualizar(evento);
    }
}

}
