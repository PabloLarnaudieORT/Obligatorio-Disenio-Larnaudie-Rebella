package uy.edu.ort.battleshipdemo.Modelo;


public class Disparo {
    private Resultado resultado;
    private Celda celda;

    public Disparo(Celda celda, Resultado resultado) {
        this.celda = celda;
        this.resultado = resultado;
    }

    public Celda getCelda() { return celda; }
    public Resultado getResultado() { return resultado; }

}
