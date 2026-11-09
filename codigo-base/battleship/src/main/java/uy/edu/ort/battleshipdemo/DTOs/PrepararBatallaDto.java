package uy.edu.ort.battleshipdemo.DTOs;

import java.util.List;
import java.util.ArrayList;

public class PrepararBatallaDto {
    private String lugarBatalla;
    private int filas;
    private int columnas;
    private String nombreTactica;
    private List<CantidadBarcoDto> cantidadesBarcos = new ArrayList<>();

    public List<CantidadBarcoDto> getCantidadesBarcos() {
        return cantidadesBarcos;
    }

    public void setCantidadesBarcos(List<CantidadBarcoDto> cantidadesBarcos) {
        this.cantidadesBarcos = cantidadesBarcos;
    }

    public String getLugarBatalla() {
        return lugarBatalla;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public String getNombreTactica() {
        return nombreTactica;
    }

    public void setLugarBatalla(String lugarBatalla) {
        this.lugarBatalla = lugarBatalla;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public void setNombreTactica(String nombreTactica) {
        this.nombreTactica = nombreTactica;
    }

    public PrepararBatallaDto() {

    }

    public PrepararBatallaDto(String lugarBatalla, int filas, int columnas, String nombreTactica) {
        this.lugarBatalla = lugarBatalla;
        this.filas = filas;
        this.columnas = columnas;
        this.nombreTactica = nombreTactica;
    }

}
