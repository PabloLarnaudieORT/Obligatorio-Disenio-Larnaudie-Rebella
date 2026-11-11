package uy.edu.ort.battleshipdemo.Modelo.Tactica;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import uy.edu.ort.battleshipdemo.Modelo.Celda;
import uy.edu.ort.battleshipdemo.Modelo.Disparo;

public class TacticaCazaYAtaque extends Tactica {

    private Random azar = new Random();
    private List<int[]> objetivosPendientes = new ArrayList<>();
    //es una matriz de verdadero o falso para recordar a qué casilletos ya se le disparoo
    private boolean[][] celdasDisparadas;
    private int filas;
    private int columnas;

    //se vuelve true cuando le pega a un barco. 
    private boolean modoAtaque;
    //guarda la posicion del primer golpe para usarla como punto de partida en el ataque.
    private int filaPrimerImpacto;
    private int columnaPrimerImpacto;

    //se vuelve true cuando se determina la orientación del barco (horizontal o vertical)
    private boolean orientacionDefinida;
    private boolean orientacionHorizontal;

    public TacticaCazaYAtaque() {
        super("Caza y ataque");
    }

//SELECCIONAR OBJETIVO: recorre el tablero buscando un objetivo al azar, si encuentra un impacto, entra en modo ataque y va a buscar los casilleros adyacentes hasta hundir el barco.
    @Override
public int[] seleccionarObjetivo(int filas, int columnas) {
    
    // Inicializar la matriz de celdas disparadas si es la primera vez que se llama al método
    if (celdasDisparadas == null) {
        this.filas = filas;
        this.columnas = columnas;
        celdasDisparadas = new boolean[filas][columnas];
    }

    // Si hay objetivos pendientes, selecciona el primero de la lista
    if (!objetivosPendientes.isEmpty()) {
        return objetivosPendientes.remove(0);
    }

    //en esta lista se va a ir guardando las coordenadas de casillas que no han sido disparadas, para luego elegir una al azar.
    List<int[]> objetivosDisponibles = new ArrayList<>();

    for (int fila = 0; fila < this.filas; fila++) {
        for (int columna = 0; columna < this.columnas; columna++) {
            if (!celdasDisparadas[fila][columna]) {
                objetivosDisponibles.add(new int[] { fila, columna });
            }
        }
    }

//se calcula la cantidad total de disponibles para luego elegir un índice al azar y devolver la coordenada correspondiente.
    int indiceAleatorio = azar.nextInt(objetivosDisponibles.size());
    return objetivosDisponibles.get(indiceAleatorio);
}

    @Override
    public void registraResultado(Disparo disparo) {
        Celda celda = disparo.getCelda();
        int fila = celda.getFila();
        int columna = celda.getColumna();

       //marcar la celda como disparada para no volver a disparar en ella
        celdasDisparadas[fila][columna] = true;

        //guarda en resultado el mensaje que devuelve el juego: agua, impacto o barco hundido.  
        String resultado = disparo.getResultado().getResultado();

       //si lo hunde, resetea todo para seguir buscando nuevamente en modo cazza.
        if ("barco hundido".equals(resultado)) {
            modoAtaque = false;
            orientacionDefinida = false;
            objetivosPendientes.clear();
            return;
        }

//si no es impacto, no hace nada más y sigue buscando en modo caza.
        if (!"impacto".equals(resultado)) {
            return;
        }

//si es impacto, entra en modo ataque y guarda la posición del primer impacto para usarla como referencia.
        if (!modoAtaque) {
            modoAtaque = true;
            filaPrimerImpacto = fila;
            columnaPrimerImpacto = columna;
            //agrega las celdas adyacentes a la lista de objetivos pendientes para seguir atacando.
            agregarAdyacentes(fila, columna);
            return;
        }

        if (!orientacionDefinida) {
            if (fila == filaPrimerImpacto) {
                orientacionDefinida = true;
                orientacionHorizontal = true;
                //si se determina que el barco es horizontal, se limpia la lista de objetivos pendientes y se agregan las celdas horizontales adyacentes al primer impacto y al impacto actual.
                objetivosPendientes.clear();
                agregarHorizontales(filaPrimerImpacto, columnaPrimerImpacto);
                agregarHorizontales(fila, columna);
            } else if (columna == columnaPrimerImpacto) {
                orientacionDefinida = true;
                orientacionHorizontal = false;
                objetivosPendientes.clear();
                agregarVerticales(filaPrimerImpacto, columnaPrimerImpacto);
                agregarVerticales(fila, columna);
            }
            return;
        }

//ya conoce la orientacion asi que solo agrega casillas hasta hundirol. 
        if (orientacionHorizontal) {
            agregarHorizontales(fila, columna);
        } else {
            agregarVerticales(fila, columna);
        }
    }


    private void agregarAdyacentes(int fila, int columna) {
        agregarObjetivo(fila - 1, columna);
        agregarObjetivo(fila + 1, columna);
        agregarObjetivo(fila, columna - 1);
        agregarObjetivo(fila, columna + 1);
    }

    private void agregarHorizontales(int fila, int columna) {
        agregarObjetivo(fila, columna - 1);
        agregarObjetivo(fila, columna + 1);
    }

    private void agregarVerticales(int fila, int columna) {
        agregarObjetivo(fila - 1, columna);
        agregarObjetivo(fila + 1, columna);
    }

//es el control antes de meter cualquire casilla con su coordenada a la lista de pendientes.
    private void agregarObjetivo(int fila, int columna) {
        if (fila < 0 || fila >= filas
                || columna < 0 || columna >= columnas
                || celdasDisparadas[fila][columna]) {
            return;
        }

//recorre los objetivos pendientes para ver que el nuevo objetivo no esté ya en la lista, si lo está, no lo agrega.
        for (int[] objetivo : objetivosPendientes) {
            if (objetivo[0] == fila && objetivo[1] == columna) {
                return;
            }
        }

        objetivosPendientes.add(new int[] { fila, columna });
    }
}