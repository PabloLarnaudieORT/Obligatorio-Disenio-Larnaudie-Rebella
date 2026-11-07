package uy.edu.ort.battleshipdemo.utilidades;

import uy.edu.ort.battleshipdemo.Modelo.ConfiguracionBatalla;
import uy.edu.ort.battleshipdemo.Modelo.DetalleFlota;
import uy.edu.ort.battleshipdemo.Modelo.TipoBarco;
import uy.edu.ort.battleshipdemo.fachadas.Fachada;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;
import uy.edu.ort.battleshipdemo.excepciones.TipoBarcoException;

/**
 * Datos de prueba generados con ayuda de IA para comprobar las validaciones.
 * No carga batallas en el sistema ni modifica la clase de arranque.
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
        comprobar("6 x 6, dos submarinos: 4 celdas", crearConfiguracion(6, 6,
                new DetalleFlota[] {new DetalleFlota(submarino, 2)}), true, 4);

        comprobar("6 x 6, dos acorazados: 8 celdas, debajo del 25%", crearConfiguracion(6, 6,
                new DetalleFlota[] {new DetalleFlota(acorazado, 2)}), true, 8);

        comprobar("12 x 12, seis submarinos: cantidad y dimensiones máximas válidas", crearConfiguracion(12, 12,
                new DetalleFlota[] {new DetalleFlota(submarino, 6)}), true, 12);

        comprobar("8 x 8, distintos tipos y un detalle con cantidad cero", crearConfiguracion(8, 8,
                new DetalleFlota[] {new DetalleFlota(acorazado, 1), new DetalleFlota(submarino, 2),
                        new DetalleFlota(crucero, 0)}), true, 8);
    }

    private static void probarDimensiones(TipoBarco submarino) {
        comprobar("Filas menores que 6", crearConfiguracion(5, 6,
                new DetalleFlota[] {new DetalleFlota(submarino, 1)}), false, -1);
        comprobar("Filas mayores que 12", crearConfiguracion(13, 6,
                new DetalleFlota[] {new DetalleFlota(submarino, 1)}), false, -1);
        comprobar("Columnas menores que 6", crearConfiguracion(6, 5,
                new DetalleFlota[] {new DetalleFlota(submarino, 1)}), false, -1);
        comprobar("Columnas mayores que 12", crearConfiguracion(6, 13,
                new DetalleFlota[] {new DetalleFlota(submarino, 1)}), false, -1);
    }

    private static void probarDetalles(TipoBarco submarino, TipoBarco acorazado) {
        comprobar("Arreglo de flota nulo", crearConfiguracion(6, 6, null), false, -1);
        comprobar("Arreglo de flota vacío", crearConfiguracion(6, 6, new DetalleFlota[0]), false, -1);
        comprobar("Detalle nulo dentro del arreglo", crearConfiguracion(6, 6,
                new DetalleFlota[] {null}), false, -1);
        comprobar("Detalle sin tipo de barco", crearConfiguracion(6, 6,
                new DetalleFlota[] {new DetalleFlota(null, 1)}), false, -1);
        comprobar("Cantidad negativa", crearConfiguracion(6, 6,
                new DetalleFlota[] {new DetalleFlota(submarino, -1)}), false, -1);
        comprobar("Más de seis barcos del mismo tipo", crearConfiguracion(12, 12,
                new DetalleFlota[] {new DetalleFlota(submarino, 7)}), false, -1);
        comprobar("Todos los tipos con cantidad cero", crearConfiguracion(6, 6,
                new DetalleFlota[] {new DetalleFlota(submarino, 0), new DetalleFlota(acorazado, 0)}), false, -1);
    }

    private static void probarOcupacion(TipoBarco crucero, TipoBarco acorazado) {
        comprobar("6 x 6, tres cruceros: 9 celdas, exactamente 25%", crearConfiguracion(6, 6,
                new DetalleFlota[] {new DetalleFlota(crucero, 3)}), false, -1);
        comprobar("6 x 6, tres acorazados: 12 celdas, supera el 25%", crearConfiguracion(6, 6,
                new DetalleFlota[] {new DetalleFlota(acorazado, 3)}), false, -1);

    }

    private static ConfiguracionBatalla crearConfiguracion(int filas, int columnas, DetalleFlota[] detalles) {
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
