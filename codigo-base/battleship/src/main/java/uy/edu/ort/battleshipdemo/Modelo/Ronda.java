package uy.edu.ort.battleshipdemo.Modelo;

public class Ronda {
    private int numeroRonda;
    private Disparo disparoHumano;
    private Disparo disparoMaquina;
    public Ronda(int numeroRonda, Disparo disparoHumano, Disparo disparoMaquina) {
        setNumeroRonda(numeroRonda);
        this.disparoHumano = disparoHumano;
        this.disparoMaquina = disparoMaquina;
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
    public Disparo getDisparoHumano() { return disparoHumano; }
    public void setDisparoHumano(Disparo disparoHumano) { this.disparoHumano = disparoHumano; }

    // Puede quedar nulo si el disparo humano termina la batalla.
    public Disparo getDisparoMaquina() { return disparoMaquina; }
    public void setDisparoMaquina(Disparo disparoMaquina) { this.disparoMaquina = disparoMaquina; }
}
