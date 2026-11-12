package uy.edu.ort.battleshipdemo.utilidades;

import uy.edu.ort.battleshipdemo.Modelo.ConfiguracionBatalla;
import uy.edu.ort.battleshipdemo.Modelo.Batalla;
import uy.edu.ort.battleshipdemo.Modelo.Jugador.JugadorHumano;
import uy.edu.ort.battleshipdemo.Modelo.Jugador.JugadorMaquina;
import uy.edu.ort.battleshipdemo.Modelo.Tactica.TacticaSecuencial;
import uy.edu.ort.battleshipdemo.Modelo.Barco;
import uy.edu.ort.battleshipdemo.Modelo.Celda;
import uy.edu.ort.battleshipdemo.Modelo.Flota;
import uy.edu.ort.battleshipdemo.Modelo.Oceano;
import uy.edu.ort.battleshipdemo.Modelo.DetalleFlota;
import uy.edu.ort.battleshipdemo.Modelo.TipoBarco;
import uy.edu.ort.battleshipdemo.fachadas.Fachada;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;
import uy.edu.ort.battleshipdemo.excepciones.TipoBarcoException;
import java.util.List;
import java.util.ArrayList;

/**
 * 
 * Datos de prueba para la aplicación y comprobaciones de configuración.
 * 
 */

public class DatosPrueba {

        // Precarga exigida por la letra. Invocar una sola vez al iniciar la aplicación.
        // Generado con ayuda de IA, separado de las comprobaciones de configuración.
        public static void cargarTiposBarco() throws TipoBarcoException {
                Fachada fachada = Fachada.getInstancia();
                fachada.agregarTipoBarco(new TipoBarco("Portaaviones", 5));
                fachada.agregarTipoBarco(new TipoBarco("Acorazado", 4));
                fachada.agregarTipoBarco(new TipoBarco("Crucero", 3));
                fachada.agregarTipoBarco(new TipoBarco("Submarino", 2));
                fachada.agregarTipoBarco(new TipoBarco("Destructor", 1));
        }

        // Precarga de ejemplo generada con ayuda de IA para comprobar la ubicación.
        // Llamar después de cargarTiposBarco(). No inicia combate ni ejecuta la táctica.
        public static void cargarBatallaPreparacion() throws BatallaException {
                Fachada fachada = Fachada.getInstancia();
                // Conservar una preparación existente, incluidas sus ubicaciones.
                if (fachada.buscarBatalla("PRUEBA") != null) {
                        return;
                }
                TipoBarco acorazado = fachada.buscarTipoBarco("Acorazado");
                TipoBarco submarino = fachada.buscarTipoBarco("Submarino");
                if (acorazado == null || submarino == null) {
                        throw new BatallaException("Cargá los tipos de barco antes de la batalla de ejemplo.");
                }
                List<DetalleFlota> detalles = new ArrayList<>();
                detalles.add(new DetalleFlota(acorazado, 1));
                detalles.add(new DetalleFlota(submarino, 1));
                ConfiguracionBatalla configuracion = crearConfiguracion(8, 8, detalles);
                configuracion.validar();

                JugadorHumano humano = new JugadorHumano("Humano de prueba");
                humano.setOceano(configuracion.crearOceano());
                JugadorMaquina maquina = new JugadorMaquina("Máquina de prueba", new TacticaSecuencial());
                maquina.setOceano(configuracion.crearOceano());

                Batalla batalla = new Batalla();
                batalla.setLugar("PRUEBA");
                batalla.setConfiguracion(configuracion);
                batalla.setJugadorHumano(humano);
                batalla.setJugadorMaquina(maquina);
                // El constructor de Batalla ya establece EstadoEnPreparacion.
                fachada.agregarBatalla(batalla);
        }

        // Precarga los ejemplos que pide el CU03: uno en combate, uno ganado y uno perdido.
        // Invocar después de cargar los tipos de barco.
        public static void cargarBatallasCU03() throws BatallaException {
                Fachada fachada = Fachada.getInstancia();

                if (fachada.buscarBatalla("CU03_COMBATE") == null) {
                        Batalla combate = crearBatallaCU03(fachada, "CU03_COMBATE", false);
                        fachada.agregarBatalla(combate);
                        fachada.iniciarCombate(combate.getLugar());
                }

                if (fachada.buscarBatalla("CU03_GANADA") == null) {
                        Batalla ganada = crearBatallaCU03(fachada, "CU03_GANADA", false);
                        fachada.agregarBatalla(ganada);
                        fachada.iniciarCombate(ganada.getLugar());
                        fachada.jugarRonda(ganada.getLugar(), 0, 0, 1);
                        fachada.jugarRonda(ganada.getLugar(), 0, 1, 2);
                        fachada.jugarRonda(ganada.getLugar(), 2, 0, 3);
                }

                if (fachada.buscarBatalla("CU03_PERDIDA") == null) {
                        Batalla perdida = crearBatallaCU03(fachada, "CU03_PERDIDA", true);
                        fachada.agregarBatalla(perdida);
                        fachada.iniciarCombate(perdida.getLugar());

                        int numeroRonda = 1;
                        for (int columna = 0; columna < 6; columna++) {
                                fachada.jugarRonda(perdida.getLugar(), 7, columna, numeroRonda);
                                numeroRonda++;
                        }
                        for (int columna = 0; columna < 6; columna++) {
                                fachada.jugarRonda(perdida.getLugar(), 6, columna, numeroRonda);
                                numeroRonda++;
                        }
                        for (int columna = 0; columna < 5; columna++) {
                                fachada.jugarRonda(perdida.getLugar(), 5, columna, numeroRonda);
                                numeroRonda++;
                        }
                }
        }

        private static Batalla crearBatallaCU03(
                        Fachada fachada, String lugar, boolean jugadorHumanoPierde)
                        throws BatallaException {
                TipoBarco submarino = fachada.buscarTipoBarco("Submarino");
                TipoBarco destructor = fachada.buscarTipoBarco("Destructor");
                if (submarino == null || destructor == null) {
                        throw new BatallaException("Primero hay que cargar los tipos de barco.");
                }

                List<DetalleFlota> detalles = new ArrayList<>();
                detalles.add(new DetalleFlota(submarino, 1));
                detalles.add(new DetalleFlota(destructor, 1));
                ConfiguracionBatalla configuracion = crearConfiguracion(8, 8, detalles);

                JugadorHumano humano = new JugadorHumano("Jugador humano");
                Oceano oceanoHumano = configuracion.crearOceano();
                humano.setOceano(oceanoHumano);

                JugadorMaquina maquina = new JugadorMaquina(
                                "Máquina", new TacticaSecuencial());
                Oceano oceanoMaquina = configuracion.crearOceano();
                maquina.setOceano(oceanoMaquina);

                if (jugadorHumanoPierde) {
                        oceanoHumano.ubicarBarco(
                                        oceanoHumano.getFlota().getBarcos().get(0), 0, 0, true);
                        oceanoHumano.ubicarBarco(
                                        oceanoHumano.getFlota().getBarcos().get(1), 2, 0, true);
                        oceanoMaquina.ubicarBarco(
                                        oceanoMaquina.getFlota().getBarcos().get(0), 6, 7, false);
                        oceanoMaquina.ubicarBarco(
                                        oceanoMaquina.getFlota().getBarcos().get(1), 4, 7, true);
                } else {
                        oceanoHumano.ubicarBarco(
                                        oceanoHumano.getFlota().getBarcos().get(0), 7, 0, true);
                        oceanoHumano.ubicarBarco(
                                        oceanoHumano.getFlota().getBarcos().get(1), 4, 7, true);
                        oceanoMaquina.ubicarBarco(
                                        oceanoMaquina.getFlota().getBarcos().get(0), 0, 0, true);
                        oceanoMaquina.ubicarBarco(
                                        oceanoMaquina.getFlota().getBarcos().get(1), 2, 0, true);
                }

                Batalla batalla = new Batalla();
                batalla.setLugar(lugar);
                batalla.setConfiguracion(configuracion);
                batalla.setJugadorHumano(humano);
                batalla.setJugadorMaquina(maquina);
                return batalla;
        }

        public static void probarConfiguracion() throws TipoBarcoException {

                TipoBarco submarino = new TipoBarco("Submarino", 2);

                TipoBarco acorazado = new TipoBarco("Acorazado", 4);

                TipoBarco crucero = new TipoBarco("Crucero", 3);

                System.out.println("--- Pruebas de configuración ---");

                probarValidas(submarino, acorazado, crucero);

                probarDimensiones(submarino);

                probarDetalles(submarino, acorazado);

                probarOcupacion(crucero, acorazado);

                System.out.println("--- Fin de las 17 pruebas ---");

        }

        private static void probarValidas(TipoBarco submarino, TipoBarco acorazado, TipoBarco crucero) {

                List<DetalleFlota> detalles1 = new ArrayList<>();
                detalles1.add(new DetalleFlota(submarino, 2));
                comprobar("6 x 6, dos submarinos: 4 celdas", crearConfiguracion(6, 6, detalles1), true, 4);

                List<DetalleFlota> detalles2 = new ArrayList<>();
                detalles2.add(new DetalleFlota(acorazado, 2));
                comprobar("6 x 6, dos acorazados: 8 celdas, debajo del 25%", crearConfiguracion(6, 6, detalles2), true, 8);

                List<DetalleFlota> detalles3 = new ArrayList<>();
                detalles3.add(new DetalleFlota(submarino, 6));
                comprobar("12 x 12, seis submarinos: cantidad y dimensiones máximas válidas", crearConfiguracion(12, 12, detalles3), true, 12);

                List<DetalleFlota> detalles4 = new ArrayList<>();
                detalles4.add(new DetalleFlota(acorazado, 1));
                detalles4.add(new DetalleFlota(submarino, 2));
                detalles4.add(new DetalleFlota(crucero, 0));
                comprobar("8 x 8, distintos tipos y un detalle con cantidad cero", crearConfiguracion(8, 8, detalles4), true, 8);

        }

        private static void probarDimensiones(TipoBarco submarino) {

                List<DetalleFlota> detalles5 = new ArrayList<>();
                detalles5.add(new DetalleFlota(submarino, 1));
                comprobar("Filas menores que 6", crearConfiguracion(5, 6, detalles5), false, -1);

                List<DetalleFlota> detalles6 = new ArrayList<>();
                detalles6.add(new DetalleFlota(submarino, 1));
                comprobar("Filas mayores que 12", crearConfiguracion(13, 6, detalles6), false, -1);

                List<DetalleFlota> detalles7 = new ArrayList<>();
                detalles7.add(new DetalleFlota(submarino, 1));
                comprobar("Columnas menores que 6", crearConfiguracion(6, 5, detalles7), false, -1);

                List<DetalleFlota> detalles8 = new ArrayList<>();
                detalles8.add(new DetalleFlota(submarino, 1));
                comprobar("Columnas mayores que 12", crearConfiguracion(6, 13, detalles8), false, -1);

        }

        private static void probarDetalles(TipoBarco submarino, TipoBarco acorazado) {

                List<DetalleFlota> detalles9 = new ArrayList<>();
                comprobar("Lista de flota nula", crearConfiguracion(6, 6, null), false, -1);
                comprobar("Lista de flota vacía", crearConfiguracion(6, 6, detalles9), false, -1);

                List<DetalleFlota> detalles10 = new ArrayList<>();
                detalles10.add(null);
                comprobar("Detalle nulo dentro de la lista", crearConfiguracion(6, 6, detalles10), false, -1);

                List<DetalleFlota> detalles11 = new ArrayList<>();
                detalles11.add(new DetalleFlota(null, 1));
                comprobar("Detalle sin tipo de barco", crearConfiguracion(6, 6, detalles11), false, -1);

                List<DetalleFlota> detalles12 = new ArrayList<>();
                detalles12.add(new DetalleFlota(submarino, -1));
                comprobar("Cantidad negativa", crearConfiguracion(6, 6, detalles12), false, -1);

                List<DetalleFlota> detalles13 = new ArrayList<>();
                detalles13.add(new DetalleFlota(submarino, 7));
                comprobar("Más de seis barcos del mismo tipo", crearConfiguracion(12, 12, detalles13), false, -1);

                List<DetalleFlota> detalles14 = new ArrayList<>();
                detalles14.add(new DetalleFlota(submarino, 0));
                detalles14.add(new DetalleFlota(acorazado, 0));
                comprobar("Todos los tipos con cantidad cero", crearConfiguracion(6, 6, detalles14), false, -1);

        }

        private static void probarOcupacion(TipoBarco crucero, TipoBarco acorazado) {

                List<DetalleFlota> detalles15 = new ArrayList<>();
                detalles15.add(new DetalleFlota(crucero, 3));
                comprobar("6 x 6, tres cruceros: 9 celdas, exactamente 25%", crearConfiguracion(6, 6, detalles15), false, -1);

                List<DetalleFlota> detalles16 = new ArrayList<>();
                detalles16.add(new DetalleFlota(acorazado, 3));
                comprobar("6 x 6, tres acorazados: 12 celdas, supera el 25%", crearConfiguracion(6, 6, detalles16), false, -1);

        }

        // Comprobación manual con datos generados con ayuda de IA.
        // No registra batallas ni se ejecuta automáticamente al iniciar la aplicación.
        public static void probarCreacionFlotas() throws TipoBarcoException, BatallaException {
                TipoBarco acorazado = new TipoBarco("Acorazado", 4);
                TipoBarco submarino = new TipoBarco("Submarino", 2);
                TipoBarco destructor = new TipoBarco("Destructor", 1);

                List<DetalleFlota> detalles = new ArrayList<>();
                detalles.add(new DetalleFlota(acorazado, 2));
                detalles.add(new DetalleFlota(submarino, 1));
                detalles.add(new DetalleFlota(destructor, 0));
                // Diez celdas en un tablero de 8 x 8: menos del 25 %.
                ConfiguracionBatalla configuracion = crearConfiguracion(8, 8, detalles);
                Flota flotaHumana = configuracion.crearFlota();
                Flota flotaMaquina = configuracion.crearFlota();

                System.out.println("--- Comprobación de creación de flotas ---");
                List<Flota> flotasCreadas = new ArrayList<>();
                flotasCreadas.add(flotaHumana);
                flotasCreadas.add(flotaMaquina);
                for (Flota flota : flotasCreadas) {
                        int acorazados = 0;
                        int submarinos = 0;
                        int destructores = 0;
                        boolean sinUbicar = true;
                        boolean barcosDistintos = true;
                        for (Barco barco : flota.getBarcos()) {
                                if (barco.getTipo() == acorazado) acorazados++;
                                if (barco.getTipo() == submarino) submarinos++;
                                if (barco.getTipo() == destructor) destructores++;
                                if (barco.estaUbicado() || !barco.getCeldas().isEmpty()) sinUbicar = false;
                        }
                        for (int i = 0; i < flota.getBarcos().size(); i++) {
                                for (int j = i + 1; j < flota.getBarcos().size(); j++) {
                                        if (flota.getBarcos().get(i) == flota.getBarcos().get(j)) barcosDistintos = false;
                                }
                        }
                        boolean cantidadesCorrectas = flota.getBarcos().size() == 3
                                        && acorazados == 2 && submarinos == 1 && destructores == 0;
                        System.out.println((cantidadesCorrectas ? "OK" : "ERROR") + " | Dos acorazados, un submarino y ningún destructor.");
                        System.out.println((sinUbicar ? "OK" : "ERROR") + " | Todos los barcos comienzan sin celdas y sin ubicar.");
                        System.out.println((barcosDistintos ? "OK" : "ERROR") + " | Los barcos de una misma flota son objetos distintos.");
                }

                boolean independientes = flotaHumana != flotaMaquina
                                && flotaHumana.getBarcos() != flotaMaquina.getBarcos();
                for (Barco humano : flotaHumana.getBarcos()) {
                        for (Barco maquina : flotaMaquina.getBarcos()) {
                                if (humano == maquina || humano.getCeldas() == maquina.getCeldas()) independientes = false;
                        }
                }
                System.out.println((independientes ? "OK" : "ERROR") + " | Las flotas no comparten barcos ni listas de celdas.");

                // Cambiar una celda de un barco no debe afectar a los demás.
                flotaHumana.getBarcos().get(0).agregarCeldaOcupada(new Celda(0, 0, false));
                boolean cambioAislado = flotaHumana.getBarcos().get(0).getCeldas().size() == 1;
                for (int i = 1; i < flotaHumana.getBarcos().size(); i++) {
                        if (!flotaHumana.getBarcos().get(i).getCeldas().isEmpty()) cambioAislado = false;
                }
                for (Barco barco : flotaMaquina.getBarcos()) {
                        if (!barco.getCeldas().isEmpty()) cambioAislado = false;
                }
                System.out.println((cambioAislado ? "OK" : "ERROR") + " | Agregar una celda a un barco no cambia los demás.");
                System.out.println("--- Fin de la comprobación de creación de flotas ---");
        }

        // Datos y comprobaciones manuales generados con ayuda de IA.
        // Trabaja con océanos locales; no registra batallas ni cambia el arranque.
        public static void probarCreacionOceanos() throws TipoBarcoException, BatallaException {
                TipoBarco acorazado = new TipoBarco("Acorazado", 4);
                TipoBarco submarino = new TipoBarco("Submarino", 2);
                List<DetalleFlota> detalles = new ArrayList<>();
                detalles.add(new DetalleFlota(acorazado, 2));
                detalles.add(new DetalleFlota(submarino, 1));
                // Tablero rectangular para detectar si se intercambian filas y columnas.
                ConfiguracionBatalla configuracion = crearConfiguracion(8, 10, detalles);
                Oceano primero = configuracion.crearOceano();
                Oceano segundo = configuracion.crearOceano();
                List<Oceano> oceanos = new ArrayList<>();
                oceanos.add(primero);
                oceanos.add(segundo);

                boolean dimensionesCorrectas = true;
                boolean celdasCorrectas = true;
                boolean celdasIndependientes = primero != segundo
                                && primero.getCeldas() != segundo.getCeldas();
                boolean barcosSinUbicar = true;
                boolean composicionCorrecta = true;
                List<Celda> celdasVistas = new ArrayList<>();
                for (Oceano oceano : oceanos) {
                        if (oceano.getFilas() != 8 || oceano.getColumnas() != 10
                                        || oceano.getCeldas().length != 8) dimensionesCorrectas = false;
                        for (int fila = 0; fila < oceano.getCeldas().length; fila++) {
                                if (oceano.getCeldas()[fila].length != 10) dimensionesCorrectas = false;
                                for (int columna = 0; columna < oceano.getCeldas()[fila].length; columna++) {
                                        Celda celda = oceano.getCeldas()[fila][columna];
                                        if (celda == null) {
                                                celdasCorrectas = false;
                                        } else {
                                                if (celda.getFila() != fila || celda.getColumna() != columna
                                                                || celda.isDisparada()) celdasCorrectas = false;
                                                if (celdasVistas.contains(celda)) celdasIndependientes = false;
                                                celdasVistas.add(celda);
                                        }
                                }
                        }
                        int cantidadAcorazados = 0;
                        int cantidadSubmarinos = 0;
                        for (Barco barco : oceano.getFlota().getBarcos()) {
                                if (barco.getTipo() == acorazado) cantidadAcorazados++;
                                if (barco.getTipo() == submarino) cantidadSubmarinos++;
                                if (barco.estaUbicado() || !barco.getCeldas().isEmpty()) barcosSinUbicar = false;
                        }
                        if (oceano.getFlota().getBarcos().size() != 3
                                        || cantidadAcorazados != 2 || cantidadSubmarinos != 1) composicionCorrecta = false;
                }
                boolean flotasIndependientes = primero.getFlota() != segundo.getFlota()
                                && primero.getFlota().getBarcos() != segundo.getFlota().getBarcos();
                for (Barco barcoPrimero : primero.getFlota().getBarcos()) {
                        for (Barco barcoSegundo : segundo.getFlota().getBarcos()) {
                                if (barcoPrimero == barcoSegundo
                                                || barcoPrimero.getCeldas() == barcoSegundo.getCeldas()) flotasIndependientes = false;
                        }
                }

                System.out.println("--- Comprobación de creación de océanos ---");
                System.out.println((dimensionesCorrectas ? "OK" : "ERROR") + " | Ambos tableros tienen 8 filas y 10 columnas.");
                System.out.println((celdasCorrectas ? "OK" : "ERROR") + " | Celdas inicializadas con coordenadas correctas y sin disparos.");
                System.out.println((celdasIndependientes ? "OK" : "ERROR") + " | Los océanos y sus celdas son independientes.");
                System.out.println((composicionCorrecta ? "OK" : "ERROR") + " | Cada océano tiene dos acorazados y un submarino.");
                System.out.println((barcosSinUbicar ? "OK" : "ERROR") + " | Todos los barcos comienzan sin ubicar.");
                System.out.println((flotasIndependientes ? "OK" : "ERROR") + " | Las flotas, barcos y listas de celdas no se comparten.");

                Barco barcoUbicado = primero.getFlota().getBarcos().get(0);
                primero.ubicarBarco(barcoUbicado, 0, 0, true);
                boolean ubicacionCorrecta = barcoUbicado.estaUbicado()
                                && barcoUbicado.getCeldas().size() == 4;
                for (int columna = 0; columna < 4; columna++) {
                        if (!barcoUbicado.getCeldas().contains(primero.getCeldas()[0][columna])
                                        || primero.getFlota().buscarBarco(primero.getCeldas()[0][columna]) != barcoUbicado) ubicacionCorrecta = false;
                }
                boolean cambioAislado = true;
                for (int i = 1; i < primero.getFlota().getBarcos().size(); i++) {
                        if (!primero.getFlota().getBarcos().get(i).getCeldas().isEmpty()) cambioAislado = false;
                }
                for (Barco barco : segundo.getFlota().getBarcos()) {
                        if (barco.estaUbicado() || !barco.getCeldas().isEmpty()) cambioAislado = false;
                }
                for (Celda celda : celdasVistas) {
                        if (celda.isDisparada()) cambioAislado = false;
                }
                System.out.println((ubicacionCorrecta ? "OK" : "ERROR") + " | El acorazado queda ubicado en las cuatro celdas de su océano.");
                System.out.println((cambioAislado ? "OK" : "ERROR") + " | Ubicarlo no modifica los otros barcos ni marca disparos.");
                System.out.println("--- Fin de la comprobación de creación de océanos ---");
        }

        // Comprobaciones manuales generadas con ayuda de IA; no se invocan al arrancar.
        // Retira únicamente sus propios ejemplos al finalizar, sin vaciar el registro.
        public static void probarLugaresBatalla() throws TipoBarcoException, BatallaException {
                Fachada fachada = Fachada.getInstancia();
                List<Batalla> ejemplos = new ArrayList<>();
                List<String> lugaresOmitidos = new ArrayList<>();
                lugaresOmitidos.add(null);
                lugaresOmitidos.add("");
                lugaresOmitidos.add("   ");
                List<String> generados = new ArrayList<>();
                System.out.println("--- Comprobación de lugares de batalla ---");
                try {
                        int numeroCaso = 0;
                        for (String omitido : lugaresOmitidos) {
                                numeroCaso++;
                                Batalla batalla = crearBatallaParaComprobarLugar(omitido);
                                ejemplos.add(batalla);
                                fachada.agregarBatalla(batalla);
                                String generado = batalla.getLugar();
                                boolean formatoCorrecto = generado != null && generado.length() == 6;
                                if (formatoCorrecto) {
                                        for (int i = 0; i < generado.length(); i++) {
                                                if ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(generado.charAt(i)) < 0) formatoCorrecto = false;
                                        }
                                }
                                System.out.println((formatoCorrecto ? "OK" : "ERROR")
                                                + " | Lugar omitido, caso " + numeroCaso + ": seis letras mayúsculas.");
                                generados.add(generado);
                        }
                        boolean distintos = true;
                        for (int i = 0; i < generados.size(); i++) {
                                for (int j = i + 1; j < generados.size(); j++) {
                                        if (generados.get(i).equals(generados.get(j))) distintos = false;
                                }
                        }
                        System.out.println((distintos ? "OK" : "ERROR") + " | Los nombres generados registrados son distintos.");

                        String elegido = "Lugar de comprobación";
                        while (fachada.buscarBatalla(elegido) != null) elegido += "X";
                        Batalla original = crearBatallaParaComprobarLugar(elegido);
                        ejemplos.add(original);
                        fachada.agregarBatalla(original);
                        boolean conservado = elegido.equals(original.getLugar())
                                        && fachada.buscarBatalla(elegido) == original;
                        System.out.println((conservado ? "OK" : "ERROR") + " | Se conserva el nombre ingresado.");

                        int cantidadAntes = fachada.getBatallas().size();
                        Batalla repetida = crearBatallaParaComprobarLugar(elegido);
                        ejemplos.add(repetida);
                        boolean mensajeCorrecto = false;
                        try {
                                fachada.agregarBatalla(repetida);
                        } catch (BatallaException error) {
                                mensajeCorrecto = "No se puede librar más de una batalla en el mismo lugar, cambie de nombre"
                                                .equals(error.getMessage());
                        }
                        System.out.println((mensajeCorrecto ? "OK" : "ERROR") + " | Lugar duplicado rechazado con el mensaje de la letra.");
                        boolean registroIntacto = fachada.getBatallas().size() == cantidadAntes
                                        && !fachada.getBatallas().contains(repetida)
                                        && fachada.buscarBatalla(elegido) == original;
                        System.out.println((registroIntacto ? "OK" : "ERROR") + " | La batalla rechazada no se agrega y se conserva la original.");
                } finally {
                        for (Batalla ejemplo : ejemplos) fachada.getBatallas().remove(ejemplo);
                }
                System.out.println("--- Fin de la comprobación de lugares ---");
        }

        private static Batalla crearBatallaParaComprobarLugar(String lugar) throws TipoBarcoException, BatallaException {
                List<DetalleFlota> detalles = new ArrayList<>();
                detalles.add(new DetalleFlota(new TipoBarco("Acorazado", 4), 1));
                detalles.add(new DetalleFlota(new TipoBarco("Submarino", 2), 1));
                ConfiguracionBatalla configuracion = crearConfiguracion(8, 8, detalles);
                JugadorHumano humano = new JugadorHumano("Humano de ejemplo");
                humano.setOceano(configuracion.crearOceano());
                JugadorMaquina maquina = new JugadorMaquina("Máquina de ejemplo", new TacticaSecuencial());
                maquina.setOceano(configuracion.crearOceano());
                Batalla batalla = new Batalla();
                batalla.setLugar(lugar);
                batalla.setConfiguracion(configuracion);
                batalla.setJugadorHumano(humano);
                batalla.setJugadorMaquina(maquina);
                return batalla;
        }

        private static ConfiguracionBatalla crearConfiguracion(int filas, int columnas, List<DetalleFlota> detalles) {

                ConfiguracionBatalla configuracion = new ConfiguracionBatalla();
                configuracion.setFilas(filas);

                configuracion.setColumnas(columnas);

                configuracion.setFlota(detalles);

                return configuracion;

        }

        private static void comprobar(String caso, ConfiguracionBatalla configuracion,

                        boolean debeSerValida, int celdasEsperadas) {

                try {

                        configuracion.validar();

                        if (!debeSerValida) {

                                System.out.println("ERROR | " + caso + " | Debió rechazarse.");

                        } else if (configuracion.calcularOcupados() != celdasEsperadas) {

                                System.out.println("ERROR | " + caso + " | El total de celdas no coincide.");

                        } else {

                                System.out.println("OK | " + caso + " | Configuración aceptada.");

                        }

                } catch (BatallaException error) {

                        String prefijo = debeSerValida ? "ERROR" : "OK";

                        System.out.println(prefijo + " | " + caso + " | Rechazada: " + error.getMessage());

                }

        }

}
