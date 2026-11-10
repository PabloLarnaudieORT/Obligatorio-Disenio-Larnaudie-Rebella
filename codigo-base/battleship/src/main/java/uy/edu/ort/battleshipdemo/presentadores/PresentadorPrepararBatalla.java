package uy.edu.ort.battleshipdemo.presentadores;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Scope;
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
    // TipoBarcoDto.listaDeBarcosPreparadosDtos() convierte la lista de tipos de barco a una lista de
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

    private Command inicializarTablero(Oceano oceano) {
        int filas = oceano.getFilas();
        int columnas = oceano.getColumnas();
        InicializarDto datosDelTablero = new InicializarDto(filas, columnas, "/preparacion-batalla/ubicarBarco", null);
        return new Command("inicializar", datosDelTablero);

    }

    private Command barcosPreparacion(Flota flota) {
        return new Command("barcosPreparacion", BarcoPreparacionDto.listaDeBarcosPreparadosDtos(flota.getBarcos()));
    }

    // cada comando "mostrarAguasPropias indica fila y columna mostra el simbolo del
    // barco"
    // entonces precisamos commands para reunir todas las ordenes que precisa el
    // barco
    // el barco precisa 4 ordenes para 4 celdas.
    private Commands mostrarBarcosUbicados(Flota flota) {
        Commands comandos = Commands.create();
        for (Barco barco : flota.getBarcos()) {
            // Aca tengo que hacer 2 for... por que
            // no puedo aplicar esto dentro de barco y crear un command
            // desde dentro del barco.
            for (Celda celda : barco.getCeldas()) {
                ValorDto valorDto = new ValorDto(
                        celda.getFila(),
                        celda.getColumna(),
                        "barco");
                Command comando = new Command("mostrarAguasPropias", valorDto);
                comandos.add(comando);
            }
        }
        return comandos;
    }

    @PostMapping("/prepararBatalla")
    public Commands prepararBatalla(PrepararBatallaDto prepararBatallaDto) throws BatallaException {
        ConfiguracionBatalla configuracionBatalla = crearConfiguracion(prepararBatallaDto);
        return Commands.create(new Command("configuracionValidada", "La configuracion de la flota es valida"));
    }

    @PostMapping("/ubicarBarco")
    public Commands ubicarBarco(UbicarBarcoDto ubicarBarcoDto) throws BatallaException {
        fachada.ubicarBarco(ubicarBarcoDto.getLugarDeLaBatalla(), ubicarBarcoDto.getIndiceDelBarco(),
                ubicarBarcoDto.getFila(), ubicarBarcoDto.getColumna(), ubicarBarcoDto.getEsHorizontal());
        Batalla batalla = fachada.buscarBatalla(ubicarBarcoDto.getLugarDeLaBatalla());
        Flota flotaHumana = batalla.getJugadorHumano().getOceano().getFlota();
        Commands comandos = mostrarBarcosUbicados(flotaHumana);
        comandos.add(new Command("barcoUbicado", "Barco ubicado!"));
        comandos.add(barcosPreparacion(flotaHumana));
        Command estadoFlota = new Command(
                "flotaUbicada",
                flotaHumana.estanTodosUbicados());
        comandos.add(estadoFlota);
        return comandos;
    }

    @PostMapping("/abrirPreparacion")
    public Commands abrirPreparacion(String lugarBatalla) throws BatallaException {
        // primero buscamos la batalla
        Batalla batalla = fachada.buscarBatalla(lugarBatalla);
        if (batalla == null) {
            throw new BatallaException("La batalla no existe");
        }
        // obtenemos la batalla del jugador humano.
        Oceano oceano = batalla.getJugadorHumano().getOceano();
        // cuado tenemos el oceano, podemos acceder a la flota
        Flota flota = oceano.getFlota();
        Commands comandos = Commands.create();
        // Ahora creamos comandos, para lugarBatalla.
        comandos.add(new Command("lugarBatalla", batalla.getLugar()));
        comandos.add(inicializarTablero(oceano));
        comandos.add(barcosPreparacion(flota));
        Commands dibujarBarcos = mostrarBarcosUbicados(flota);
        // Aca incorporamos las ordenes del dibujo despues de inicializar el tablero.
        for (Command comando : dibujarBarcos.getCommands()) {
            comandos.add(comando);
        }
        //aca corroboramos que la flota se haya ubicado
        Command estadoFlota = new Command(
                "flotaUbicada",
                flota.estanTodosUbicados());
        comandos.add(estadoFlota);
        return comandos;
    }

}
