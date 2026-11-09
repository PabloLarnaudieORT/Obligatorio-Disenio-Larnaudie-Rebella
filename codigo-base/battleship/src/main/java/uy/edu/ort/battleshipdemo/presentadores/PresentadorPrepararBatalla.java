package uy.edu.ort.battleshipdemo.presentadores;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Scope;
import uy.edu.ort.battleshipdemo.Commands;
import uy.edu.ort.battleshipdemo.Command;

import java.util.List;
import java.util.ArrayList;

import uy.edu.ort.battleshipdemo.DTOs.TipoBarcoDto;
import uy.edu.ort.battleshipdemo.DTOs.PrepararBatallaDto;
import uy.edu.ort.battleshipdemo.DTOs.CantidadBarcoDto;
import uy.edu.ort.battleshipdemo.fachadas.Fachada;
import uy.edu.ort.battleshipdemo.Modelo.ConfiguracionBatalla;
import uy.edu.ort.battleshipdemo.Modelo.DetalleFlota;
import uy.edu.ort.battleshipdemo.Modelo.TipoBarco;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;

@RestController
@RequestMapping("/preparacion-batalla")
@Scope("session")
public class PresentadorPrepararBatalla {
    private Fachada fachada = Fachada.getInstancia();

    @PostMapping("/vistaConectada")
    public Commands vistaConectada() {
        return Commands.create(tiposBarco());
    }

    // fachada-getTiposBarco() obtiene los tipos del catalogo.
    // TipoBarcoDto.listaDtos() convierte la lista de tipos de barco a una lista de
    // DTOs.
    private Command tiposBarco() {
        return new Command(
                "tiposBarco",
                TipoBarcoDto.listaDtos(fachada.getTiposBarco()));
    }

    private ConfiguracionBatalla crearConfiguracion(PrepararBatallaDto datos) throws BatallaException {
        // primero creo una instancia de ConfiguracionBatalla
        ConfiguracionBatalla configuracion = new ConfiguracionBatalla();
        List<DetalleFlota> listaDeFlotas = new ArrayList<>();
        // datos, tiene la cantidades de barcos, para armar la flota
        List<CantidadBarcoDto> cantidadesEnDto = datos.getCantidadesBarcos();
        // luego, uso las filas y columnas de los datos
        int filas = datos.getFilas();
        int columnas = datos.getColumnas();
        // y las uso para configurar todo.
        configuracion.setFilas(filas);
        configuracion.setColumnas(columnas);
        // despues, recorro las cantidades recibidas para construir los detalles de la
        // flota.
        for (CantidadBarcoDto cantidadDto : cantidadesEnDto) {
            TipoBarco tipoBarco = fachada.buscarTipoBarco(cantidadDto.getNombreTipo());
            DetalleFlota detalle = new DetalleFlota(tipoBarco, cantidadDto.getCantidad());
            listaDeFlotas.add(detalle);
        }
        configuracion.setFlota(listaDeFlotas);
        configuracion.validar();
        return configuracion;
    }

    @PostMapping("/prepararBatalla")
    public Commands prepararBatalla(PrepararBatallaDto datos) throws BatallaException {
        ConfiguracionBatalla configuracionBatalla = crearConfiguracion(datos);
        return Commands.create(new Command("configuracionValidada", "La configuracion de la flota es valida"));
    }
}
