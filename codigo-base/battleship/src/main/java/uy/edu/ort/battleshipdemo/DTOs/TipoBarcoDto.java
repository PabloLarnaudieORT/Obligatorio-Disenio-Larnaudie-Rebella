package uy.edu.ort.battleshipdemo.DTOs;
import uy.edu.ort.battleshipdemo.Modelo.TipoBarco;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;
import java.util.ArrayList;
import java.util.List;

public class TipoBarcoDto {
    private String nombre;
    private int tamaño;

    public String getNombre() {
        return nombre;
    }

    public int getTamanio() {
        return tamaño;
    }

    public TipoBarcoDto(TipoBarco tipoBarco) {
        this.nombre = tipoBarco.getNombre();
        this.tamaño = tipoBarco.getTamanio();
    }

    public static List<TipoBarcoDto> listaDtos(List<TipoBarco> tiposBarco) {
        List<TipoBarcoDto> tipoBarcoDtos = new ArrayList<>();
        for (TipoBarco tipo : tiposBarco) {
            tipoBarcoDtos.add(new TipoBarcoDto(tipo));
        }
        return tipoBarcoDtos;
    }

}
