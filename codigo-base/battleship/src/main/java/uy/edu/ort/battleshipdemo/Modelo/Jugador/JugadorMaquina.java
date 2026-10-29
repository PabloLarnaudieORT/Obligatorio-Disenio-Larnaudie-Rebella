package uy.edu.ort.battleshipdemo.Modelo.Jugador;

import uy.edu.ort.battleshipdemo.Modelo.Tactica.Tactica;

public class JugadorMaquina extends Jugador {
    private String nombre;
    private Tactica tactica;

    public JugadorMaquina(String nombre, Tactica tactica) {
        this.nombre = nombre;
        this.tactica = tactica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tactica getTactica() {
        return tactica;
    }

    public void setTactica(Tactica tactica) {
        this.tactica = tactica;
    }

}
