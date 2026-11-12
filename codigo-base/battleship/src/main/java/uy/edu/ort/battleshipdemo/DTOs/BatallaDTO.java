package uy.edu.ort.battleshipdemo.DTOs;

import java.util.List;

public class BatallaDto {
    private String lugar;
    private String estado;
    private int ronda;
    private List<Integer> bajasEnemigas;

    // Constructor
    public BatallaDto(String lugar, String estado, int ronda,
            List<Integer> bajasEnemigas) {
        this.lugar = lugar;
        this.estado = estado;
        this.ronda = ronda;
        this.bajasEnemigas = bajasEnemigas;
    }

    public String getLugar() {
        return lugar;
    }

    public String getEstado() {
        return estado;
    }

    public int getRonda() {
        return ronda;
    }

    public List<Integer> getBajasEnemigas() {
        return bajasEnemigas;
    }
}