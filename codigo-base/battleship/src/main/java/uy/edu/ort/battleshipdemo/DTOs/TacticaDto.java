package uy.edu.ort.battleshipdemo.DTOs;
import java.util.List;
import java.util.ArrayList;
import uy.edu.ort.battleshipdemo.Modelo.Tactica.Tactica;


public class TacticaDto {
    private String nombreTactica;

    public String getNombre() {
        return nombreTactica;
    }

    public TacticaDto(Tactica tactica) {
        this.nombreTactica = tactica.getNombre();
    }

    //Aca agarramos las tacticas que queremos, y las transformamos a dto
    //para mostrarlas
    public static List<TacticaDto> listaTacticaDto(List<Tactica> tacticasIngresadas){
        List<TacticaDto> listaDeTacticas = new ArrayList<>();
        for(Tactica tactica : tacticasIngresadas){
            TacticaDto tacticaDto = new TacticaDto(tactica);
            listaDeTacticas.add(tacticaDto);
        }
        return listaDeTacticas;
    }

}
