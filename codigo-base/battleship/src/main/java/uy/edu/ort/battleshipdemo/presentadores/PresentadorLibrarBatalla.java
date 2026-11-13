package uy.edu.ort.battleshipdemo.presentadores;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;



import uy.edu.ort.battleshipdemo.Command;
import uy.edu.ort.battleshipdemo.Commands;
import uy.edu.ort.battleshipdemo.InicializarDto;
import uy.edu.ort.battleshipdemo.DTOs.BatallaDto;
import uy.edu.ort.battleshipdemo.Modelo.Batalla;
import uy.edu.ort.battleshipdemo.fachadas.Fachada;
import uy.edu.ort.battleshipdemo.ValorDto;
import uy.edu.ort.battleshipdemo.Modelo.Disparo;
import uy.edu.ort.battleshipdemo.Modelo.Ronda;
import uy.edu.ort.battleshipdemo.Modelo.Barco;
import uy.edu.ort.battleshipdemo.Modelo.Celda;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;
import uy.edu.ort.battleshipdemo.Modelo.Eventos.EventoBatalla;
import uy.edu.ort.battleshipdemo.Modelo.Eventos.ObservadorBatalla;


@RestController
@RequestMapping("/librar-batalla")
@Scope("session")

public class PresentadorLibrarBatalla {

    private Fachada fachada = Fachada.getInstancia();

@GetMapping(value = "/eventos", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public SseEmitter eventos(String lugarBatalla) throws BatallaException {
    SseEmitter emisor = new SseEmitter();

    ObservadorBatalla observador = new ObservadorBatalla() {
        @Override
        public void actualizar(EventoBatalla evento) {
            if (evento.getIdentificadorBatalla().equals(lugarBatalla)) {
                try {
                    emisor.send(
                            SseEmitter.event()
                                    .name("actualizacion")
                                    .data(evento.getTipoCambio()));
                } catch (IOException error) {
                    emisor.completeWithError(error);
                }
            }
        }
    };

    fachada.agregarObservador(lugarBatalla, observador);
    emisor.onCompletion(() -> quitarObservador(lugarBatalla, observador));
    emisor.onTimeout(() -> {
        quitarObservador(lugarBatalla, observador);
        emisor.complete();
    });
    emisor.onError(error -> quitarObservador(lugarBatalla, observador));

    return emisor;
}

private void quitarObservador(String lugarBatalla, ObservadorBatalla observador) {
    try {
        fachada.quitarObservador(lugarBatalla, observador);
    } catch (BatallaException error) {
        // Si la batalla ya no está disponible, esta conexión ya no puede observarla.
    }
}


    @PostMapping("/vistaConectada")
    public Commands vistaConectada(String lugarBatalla) throws BatallaException {
        Batalla batalla = fachada.buscarBatalla(lugarBatalla);

        if (batalla == null) {
            throw new BatallaException("La batalla indicada no existe");
        }

        synchronized (batalla) {
            return crearComandosVista(batalla);
        }
    }

    private Commands crearComandosVista(Batalla batalla) {
        int numeroRonda = batalla.getRondas().size() + 1;

        if (batalla.getEstado().getEstado() != Batalla.Estados.enCombate
                && !batalla.getRondas().isEmpty()) {
            numeroRonda = batalla.getRondas().size();
        }

        BatallaDto datos = new BatallaDto(
                batalla.getLugar(),
                batalla.getEstado().getNombre(),
                numeroRonda,
                batalla.getBajasEnemigas());

        InicializarDto tablero = new InicializarDto(
                batalla.getConfiguracion().getFilas(),
                batalla.getConfiguracion().getColumnas(),
                "/librar-batalla/aguasPropias",
                "/librar-batalla/disparar");

        Commands comandos = Commands.create(
                new Command("batalla", datos),
                new Command("inicializar", tablero));

        for (Barco barco : batalla.getJugadorHumano().getOceano().getFlota().getBarcos()) {
            for (Celda celda : barco.getCeldas()) {
                comandos.add(new Command(
                        "mostrarAguasPropias",
                        new ValorDto(celda.getFila(), celda.getColumna(), "barco")));
            }
        }

        for (Ronda ronda : batalla.getRondas()) {
            agregarDisparo(comandos, ronda.getDisparoHumano(), false);

            if (ronda.getDisparoMaquina() != null) {
                agregarDisparo(comandos, ronda.getDisparoMaquina(), true);
            }
        }

        agregarBarcosEnemigosNoDescubiertos(comandos, batalla);
        comandos.add(new Command("bajasEnemigas", formatearBajasEnemigas(batalla)));
        return comandos;
    }



private void agregarDisparo(Commands comandos, Disparo disparo, boolean aguasPropias) {
    String valor = disparo.getResultado().getResultado();

    if ("barco hundido".equals(valor)) {
        valor = "impacto";
    }

    String nombreComando = "mostrarAguasEnemigas";

    if (aguasPropias) {
        nombreComando = "mostrarAguasPropias";
    }

    comandos.add(new Command(
            nombreComando,
            new ValorDto(
                    disparo.getCelda().getFila(),
                    disparo.getCelda().getColumna(),
                    valor)));
}

private void agregarBarcosEnemigosNoDescubiertos(Commands comandos, Batalla batalla) {
    if (batalla.getEstado().getEstado() != Batalla.Estados.perdida) {
        return;
    }

    for (Barco barco : batalla.getJugadorMaquina().getOceano().getFlota().getBarcos()) {
        for (Celda celda : barco.getCeldas()) {
            if (!celda.isDisparada()) {
                comandos.add(new Command(
                        "mostrarAguasEnemigas",
                        new ValorDto(celda.getFila(), celda.getColumna(), "barco")));
            }
        }
    }
}



    @PostMapping("/disparar")
    public Commands disparar(
            String lugarBatalla,
            int numeroRondaEsperada,
            int coordenadaVertical,
            int coordenadaHorizontal) {
    try {
        Ronda ronda = fachada.jugarRonda(
                lugarBatalla,
                coordenadaVertical,
                coordenadaHorizontal,
                numeroRondaEsperada);

        Batalla batalla = fachada.buscarBatalla(lugarBatalla);

        Commands comandos = Commands.create();
        agregarDisparo(comandos, ronda.getDisparoHumano(), false);

        Disparo disparoMaquina = ronda.getDisparoMaquina();

        if (disparoMaquina != null) {
            agregarDisparo(comandos, disparoMaquina, true);
        }

        // Si este disparo terminó la batalla en derrota, mostrar enseguida
        // las partes de los barcos enemigos que aún no se habían descubierto.
        agregarBarcosEnemigosNoDescubiertos(comandos, batalla);

        int numeroRonda = numeroRondaEsperada + 1;

        if (batalla.getEstado().getEstado() != Batalla.Estados.enCombate) {
            numeroRonda = batalla.getRondas().size();
        }

        BatallaDto datos = new BatallaDto(
                batalla.getLugar(),
                batalla.getEstado().getNombre(),
                numeroRonda,
                batalla.getBajasEnemigas());

        comandos.add(new Command("batalla", datos));
        comandos.add(new Command(
                "bajasEnemigas",
                formatearBajasEnemigas(batalla)));

        return comandos;

    } catch (BatallaException error) {
        return Commands.create(
                new Command("mostrarMensaje", error.getMessage()));
    }
}

private String formatearBajasEnemigas(Batalla batalla) {
    String bajas = "";

    for (Integer tamanio : batalla.getBajasEnemigas()) {
        if (!bajas.isEmpty()) {
            bajas = bajas + " ";
        }

        bajas = bajas + "{" + tamanio + "}";
    }

    if (bajas.isEmpty()) {
        return "Ninguna";
    }

    return bajas;
}


@PostMapping("/aguasPropias")
public Commands aguasPropias(
        int coordenadaVertical, int coordenadaHorizontal) {

    return Commands.create(
            new Command(
                    "mostrarMensaje",
                    "No se puede disparar en aguas propias"));
}


}
