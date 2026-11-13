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
import java.util.Random;

import uy.edu.ort.battleshipdemo.DTOs.TipoBarcoDto;
import uy.edu.ort.battleshipdemo.DTOs.TacticaDto;
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
import uy.edu.ort.battleshipdemo.Modelo.Jugador.JugadorHumano;
import uy.edu.ort.battleshipdemo.Modelo.Jugador.JugadorMaquina;
import uy.edu.ort.battleshipdemo.Modelo.Tactica.Tactica;
import uy.edu.ort.battleshipdemo.Modelo.Tactica.TacticaSecuencial;
import uy.edu.ort.battleshipdemo.Modelo.Tactica.TacticaEspiral;
import uy.edu.ort.battleshipdemo.Modelo.Tactica.TacticaCazaYAtaque;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;

@RestController
@RequestMapping("/preparacion-batalla")
@Scope("session")
public class PresentadorPrepararBatalla {
    private Fachada fachada = Fachada.getInstancia();
    private Random azar = new Random();

    @PostMapping("/vistaConectada")
    public Commands vistaConectada() {
        return Commands.create(tiposBarco(), tacticas());
    }

    // fachada-getTiposBarco() obtiene los tipos del catalogo.
    // TipoBarcoDto.listaDeBarcosPreparadosDtos() convierte la lista de tipos de
    // barco a una lista de
    // DTOs.
    private Command tiposBarco() {
        return new Command(
                "tiposBarco",
                TipoBarcoDto.listaDtos(fachada.getTiposBarco()));
    }

    private Command tacticas() {
        List<Tactica> disponibles = new ArrayList<>();
        disponibles.add(new TacticaSecuencial());
        disponibles.add(new TacticaEspiral());
        disponibles.add(new TacticaCazaYAtaque());
        return new Command("tacticas", TacticaDto.listaTacticaDto(disponibles));
    }

    private Tactica crearTactica(String nombre) throws BatallaException {
        if ("Secuencial".equals(nombre)) {
            return new TacticaSecuencial();
        }
        if ("Espiral".equals(nombre)) {
            return new TacticaEspiral();
        }
        if ("Caza y ataque".equals(nombre)) {
            return new TacticaCazaYAtaque();
        }
        throw new BatallaException("Seleccioná una táctica de juego válida.");
    }

    private Oceano crearOceanoMaquina(ConfiguracionBatalla configuracion)
            throws BatallaException {
        for (int intento = 0; intento < 100; intento++) {
            Oceano oceano = configuracion.crearOceano();
            boolean flotaUbicada = true;

            for (Barco barco : oceano.getFlota().getBarcos()) {
                if (!ubicarBarcoAleatoriamente(oceano, barco)) {
                    flotaUbicada = false;
                    break;
                }
            }

            if (flotaUbicada) {
                return oceano;
            }
        }
        throw new BatallaException("No se pudo ubicar la flota de la máquina. Probá otra configuración.");
    }

    private boolean ubicarBarcoAleatoriamente(Oceano oceano, Barco barco) {
        int cantidadIntentos = oceano.getFilas() * oceano.getColumnas() * 2;

        for (int intento = 0; intento < cantidadIntentos; intento++) {
            int fila = azar.nextInt(oceano.getFilas());
            int columna = azar.nextInt(oceano.getColumnas());
            boolean horizontal = azar.nextBoolean();

            try {
                oceano.ubicarBarco(barco, fila, columna, horizontal);
                return true;
            } catch (BatallaException error) {
                // Esa posición no sirve; se prueba otra posición aleatoria.
            }
        }
        return false;
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
        InicializarDto datosDelTablero = new InicializarDto(filas, columnas, "/preparacion-batalla/ubicarBarco",
                "/preparacion-batalla/ubicarEnAguasEnemigas");
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
        Tactica tactica = crearTactica(prepararBatallaDto.getNombreTactica());

        JugadorHumano jugadorHumano = new JugadorHumano("Jugador humano");
        jugadorHumano.setOceano(configuracionBatalla.crearOceano());

        JugadorMaquina jugadorMaquina = new JugadorMaquina("Máquina", tactica);
        jugadorMaquina.setOceano(crearOceanoMaquina(configuracionBatalla));

        Batalla batalla = new Batalla();
        batalla.setLugar(prepararBatallaDto.getLugarBatalla());
        batalla.setConfiguracion(configuracionBatalla);
        batalla.setJugadorHumano(jugadorHumano);
        batalla.setJugadorMaquina(jugadorMaquina);
        fachada.agregarBatalla(batalla);

        Commands comandos = abrirPreparacion(batalla.getLugar());
        comandos.add(new Command("batallaCreada", batalla.getLugar()));
        return comandos;
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
        // aca corroboramos que la flota se haya ubicado
        Command estadoFlota = new Command(
                "flotaUbicada",
                flota.estanTodosUbicados());
        comandos.add(estadoFlota);
        return comandos;
    }

    @PostMapping("/ubicarEnAguasEnemigas")
    public Commands ubicarEnAguasEnemigas() throws BatallaException {
        throw new BatallaException("No puede ubicar barcos en aguas enemigas");
    }

    @PostMapping("/validarFlotaParaLibrarBatalla")
    public Commands validarFlotaParaLibrarBatalla(String lugarBatalla) throws BatallaException {
        fachada.validarFlotaParaLibrarBatalla(lugarBatalla);
        return Commands.create(
                new Command("flotaListaParaCombatir", "La flota está lista para librar batalla."));
    }

}
