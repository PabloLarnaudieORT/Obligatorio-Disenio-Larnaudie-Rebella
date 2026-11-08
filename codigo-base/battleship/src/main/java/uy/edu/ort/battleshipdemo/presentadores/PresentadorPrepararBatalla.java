package uy.edu.ort.battleshipdemo.presentadores;
import java.util.List;
import java.util.ArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.edu.ort.battleshipdemo.Command;
import uy.edu.ort.battleshipdemo.DTOs.TipoBarcoDto;
import uy.edu.ort.battleshipdemo.DTOs.PrepararBatallaDto;
import uy.edu.ort.battleshipdemo.DTOs.CantidadBarcoDto;
import uy.edu.ort.battleshipdemo.fachadas.Fachada;
import org.springframework.web.bind.annotation.PostMapping;
import uy.edu.ort.battleshipdemo.Commands;
import uy.edu.ort.battleshipdemo.Modelo.ConfiguracionBatalla;
import uy.edu.ort.battleshipdemo.Modelo.DetalleFlota;
import uy.edu.ort.battleshipdemo.Modelo.TipoBarco;

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
    private ConfiguracionBatalla crearConfiguracion(PrepararBatallaDto datos){
        //primero creo una instancia de ConfiguracionBatalla
        ConfiguracionBatalla configuarcion = new ConfiguracionBatalla();
        List<DetalleFlota> listaDeFlotas = new ArrayList<>();
        //datos, tiene la cantidades de barcos, para armar la flota
        List<CantidadBarcoDto> cantidadesEnDto = datos.getCantidadesBarcos();
        //luego, uso las filas y columnas de los datos
        int filas = datos.getFilas();
        int columnas = datos.getColumnas();
        //y las uso para configurar todo.
        configuarcion.setFilas(filas);
        configuarcion.setColumnas(columnas);
        //despues, recorro las cantidades recibidas para construir los detalles de la flota.
        for (CantidadBarcoDto cantidadDto : cantidadesEnDto){
            TipoBarco tipoBarco = fachada.buscarTipoBarco(cantidadDto.getNombreTipo());
        }
        return configuarcion;
    }
}
