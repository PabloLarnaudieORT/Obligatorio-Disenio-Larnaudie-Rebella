package uy.edu.ort.battleshipdemo.presentadores;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import uy.edu.ort.battleshipdemo.Commands;
import uy.edu.ort.battleshipdemo.Command;
import uy.edu.ort.battleshipdemo.InicializarDto;
import uy.edu.ort.battleshipdemo.ValorDto;

import java.util.List;

import java.util.ArrayList;

import uy.edu.ort.battleshipdemo.DTOs.TipoBarcoDto;
import uy.edu.ort.battleshipdemo.DTOs.PrepararBatallaDto;
import uy.edu.ort.battleshipdemo.DTOs.CantidadBarcoDto;
import uy.edu.ort.battleshipdemo.DTOs.BarcoPreparacionDto;
import uy.edu.ort.battleshipdemo.DTOs.BatallaSalaDto;
import uy.edu.ort.battleshipdemo.DTOs.UbicarBarcoDto;
import uy.edu.ort.battleshipdemo.fachadas.Fachada;
import uy.edu.ort.battleshipdemo.Modelo.ConfiguracionBatalla;
import uy.edu.ort.battleshipdemo.Modelo.DetalleFlota;
import uy.edu.ort.battleshipdemo.Modelo.Flota;
import uy.edu.ort.battleshipdemo.Modelo.TipoBarco;
import uy.edu.ort.battleshipdemo.Modelo.Oceano;
import uy.edu.ort.battleshipdemo.Modelo.Batalla;
import uy.edu.ort.battleshipdemo.Modelo.Celda;
import uy.edu.ort.battleshipdemo.Modelo.Barco;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;
import uy.edu.ort.battleshipdemo.Modelo.Eventos.EventoBatalla;
import uy.edu.ort.battleshipdemo.Modelo.Eventos.ObservadorBatalla;

@RestController
@RequestMapping("/sala")
@Scope("session")
public class PresentadorSala implements ObservadorBatalla {

    private Fachada fachada = Fachada.getInstancia();
    private SseEmitter emisor;

    @GetMapping(value = "/eventos", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter eventos() throws BatallaException {
        emisor = new SseEmitter();

        if (!fachada.getBatallas().isEmpty()) {
            String lugarBatalla = fachada.getBatallas().get(0).getLugar();
            fachada.agregarObservador(lugarBatalla, this);
        }

        return emisor;
    }

    private Command batallas() {
        return new Command(
                "batallas",
                BatallaSalaDto.listaDtos(fachada.getBatallas()));
    }

    @PostMapping("/vistaConectada")
    public Commands vistaConectada() {
        int ganadas = fachada.contarBatallasPorEstado(Batalla.Estados.ganada);
        int perdidas = fachada.contarBatallasPorEstado(Batalla.Estados.perdida);
        double porcentajeExito = fachada.calcularPorcentajeExito();
        //con esto mandamos a la vista las batallas ganas, perdidas y el porcentaje
        return Commands.create(
                batallas(),
                new Command("batallasGanadas", ganadas),
                new Command("batallasPerdidas", perdidas),
                new Command("porcentajeExito", porcentajeExito));
    }

    @Override
    public void actualizar(EventoBatalla evento) {
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
