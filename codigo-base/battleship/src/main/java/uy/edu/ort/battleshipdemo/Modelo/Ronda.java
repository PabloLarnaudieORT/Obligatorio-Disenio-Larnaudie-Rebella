package uy.edu.ort.battleshipdemo.Modelo;
import uy.edu.ort.battleshipdemo.Modelo.Disparo;

public class Ronda {
    private int numeroRonda;
    private Disparo disparo;
    public Ronda(int numeroRonda, Disparo disparo) {
        setNumeroRonda(numeroRonda);
        this.disparo = disparo;
    }
    public int getNumeroRonda() {
        return numeroRonda;
    }
    public void setNumeroRonda(int numeroRonda) {
        if (numeroRonda < 1) {
            throw new IllegalArgumentException("El numero de ronda debe ser mayor que cero");
        }
        this.numeroRonda = numeroRonda;
    }
    public Disparo getDisparo() {
        return disparo;
    }
    public void setDisparo(Disparo disparo) {
        this.disparo = disparo;
    }
}
