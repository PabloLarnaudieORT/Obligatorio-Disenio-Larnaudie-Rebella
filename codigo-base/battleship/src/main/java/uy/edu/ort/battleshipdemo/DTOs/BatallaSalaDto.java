package uy.edu.ort.battleshipdemo.DTOs;

import uy.edu.ort.battleshipdemo.Modelo.Batalla;
import java.util.ArrayList;
import java.util.List;

public class BatallaSalaDto {
    private String lugar;
    private int fila;
    private int columna;

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public BatallaSalaDto(Batalla batalla) {
        lugar = batalla.getLugar();
        fila = batalla.getConfiguracion().getFilas();
        columna = batalla.getConfiguracion().getColumnas();
    }

    public BatallaSalaDto(String lugar, int fila, int columna) {
        this.lugar = lugar;
        this.fila = fila;
        this.columna = columna;
    }

    public static List<BatallaSalaDto> listaDtos(List<Batalla> batallas){
        List<BatallaSalaDto> batallaSalasDto = new ArrayList<>();
        for(Batalla batalla : batallas){
            BatallaSalaDto batallaSalaDto = new BatallaSalaDto(batalla);
            batallaSalasDto.add(batallaSalaDto);
        }
        return batallaSalasDto;
    }


}
