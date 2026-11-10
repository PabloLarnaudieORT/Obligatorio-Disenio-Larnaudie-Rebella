package uy.edu.ort.battleshipdemo.DTOs;

import uy.edu.ort.battleshipdemo.Modelo.Barco;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;
import java.util.ArrayList;
import java.util.List;

public class BarcoPreparacionDto {
    private String nombreTipo;
    private int indice;
    private int tamaño;
    private boolean barcoUbicado;

    public String getNombreTipo() {
        return nombreTipo;
    }

    public int getIndice() {
        return indice;
    }

    public int getTamaño() {
        return tamaño;
    }

    public boolean getBarcoUbicado() {
        return barcoUbicado;
    }

    public BarcoPreparacionDto(String nombreTipo, int indice, int tamaño, boolean estaUbicado) {
        this.nombreTipo = nombreTipo;
        this.indice = indice;
        this.tamaño = tamaño;
        this.barcoUbicado = estaUbicado;
    }

    public BarcoPreparacionDto(Barco barco, int indice) {
        this.nombreTipo = barco.getTipo().getNombre();
        this.indice = indice;
        this.tamaño = barco.getTamañoDelBarco();
        this.barcoUbicado = barco.estaUbicado();
    }

    public static List<BarcoPreparacionDto> listaDeBarcosPreparadosDtos(List<Barco> barcos) {
        List<BarcoPreparacionDto> dtos = new ArrayList<>();
        int indice = 0;
        for (Barco barco : barcos) {
            if (!barco.estaUbicado()) {
                BarcoPreparacionDto barcoPreparacionDto = new BarcoPreparacionDto(barco, indice);
                dtos.add(barcoPreparacionDto);
            }
            indice++;
        }
        return dtos;
    }

}
