package uy.edu.ort.battleshipdemo.Modelo;

import java.util.List;
import java.util.ArrayList;

public class Flota {
    private List<Barco> barcos;

    public Flota() {
        this.barcos = new ArrayList<>();
    }

    public void agregarBarco(Barco barco) {
        this.barcos.add(barco);
    }

    public void eliminarBarco(Barco barco) {
        this.barcos.remove(barco);
    }

    public List<Barco> getBarcos() {
        return barcos;
    }

    public Barco barcoEn(Celda celdaBuscada) {
        for (Barco barco : barcos) {
            if (barco.getCeldas().contains(celdaBuscada)) {
                return barco;
            }
        }
        return null;
    }

    public boolean tieneCantidadBarcosUbicados(TipoBarco tipoBuscado, int cantidadEsperada) {
        if (tipoBuscado == null || tipoBuscado.getNombre() == null) {
            return false;
        }

        int cantidad = 0;
        for (Barco barco : barcos) {
            if (barco == null || barco.getTipo() == null
                    || barco.getTipo().getNombre() == null) {
                return false;
            }

            boolean mismoTipo = barco.getTipo().getNombre()
                    .equalsIgnoreCase(tipoBuscado.getNombre());
            boolean ubicado = barco.getCeldas().size()
                    == barco.getTipo().getTamanio();

            if (mismoTipo && ubicado) {
                cantidad++;
            }
        }
        return cantidad == cantidadEsperada;
    }

   public int partesSanas() {
    int total = 0;

    for (Barco barco : barcos) {
        total += barco.partesSanas();
    }

    return total;
}

public int tamanioTotal() {
    int total = 0;

    for (Barco barco : barcos) {
        total += barco.getTipo().getTamanio();
    }

    return total;
}

public boolean estaHundida() {
    if (barcos.isEmpty()) {
        return false;
    }

    for (Barco barco : barcos) {
        if (!barco.estaHundido()) {
            return false;
        }
    }

    return true;
}
}
