package uy.edu.ort.battleshipdemo.servicios;
import uy.edu.ort.battleshipdemo.Modelo.Batalla;
import java.util.ArrayList;
import java.util.List;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;

public class SistemaBatallas {
    private List<Batalla> batallas;

    private static SistemaBatallas instancia;

    public static SistemaBatallas getInstancia() {
        if (instancia == null) {
            instancia = new SistemaBatallas();
        }
        return instancia;
    }
    private SistemaBatallas() {
        this.batallas = new ArrayList<>();
    }

    public void agregarBatalla(Batalla batalla) throws BatallaException {
        if (batalla == null) {
            throw new BatallaException("La batalla no puede ser nula");
        }
        String nombreLugar = batalla.getLugar();
        if(nombreLugar == null || nombreLugar.isEmpty()) {
            throw new BatallaException("El lugar de la batalla no puede ser nulo o vacío");
        }
        if (buscarBatalla(nombreLugar) != null) {
            throw new BatallaException("Ya existe una batalla en el lugar: " + nombreLugar);
        }
        this.batallas.add(batalla);
    }

    public List<Batalla> getBatallas() {
        return batallas;
    }

    public Batalla buscarBatalla(String nombreLugar) {
        for (Batalla batalla : batallas) {
            if (batalla.getLugar().equals(nombreLugar)) {
                return batalla;
            }
        }
        //no devuelvo excepcion, por que si el lugar esta disponible
        //no puedo tirar una excepcion batalla... 
        return null;
    }
}
