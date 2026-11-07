package uy.edu.ort.battleshipdemo.DTOs;

public class CantidadBarcoDto {
    private String nombreTipo;
    private int cantidad;
    
    public String getNombreTipo() {
        return nombreTipo;
    }
    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public CantidadBarcoDto(String nombreTipo, int cantidad) {
        this.nombreTipo = nombreTipo;
        this.cantidad = cantidad;
    }
    public CantidadBarcoDto(){
        
    }

}
