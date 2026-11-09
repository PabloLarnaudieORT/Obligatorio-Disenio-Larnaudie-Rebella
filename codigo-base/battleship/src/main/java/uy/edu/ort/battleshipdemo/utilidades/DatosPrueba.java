package uy.edu.ort.battleshipdemo.utilidades;

import uy.edu.ort.battleshipdemo.Modelo.ConfiguracionBatalla;
import uy.edu.ort.battleshipdemo.Modelo.DetalleFlota;
import uy.edu.ort.battleshipdemo.Modelo.TipoBarco;
import uy.edu.ort.battleshipdemo.fachadas.Fachada;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;
import uy.edu.ort.battleshipdemo.excepciones.TipoBarcoException;
import java.util.List;
import java.util.ArrayList;

/**
 * 
 * Datos de prueba generados con ayuda de IA para comprobar las validaciones.
 * 
 * No carga batallas en el sistema ni modifica la clase de arranque.
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
