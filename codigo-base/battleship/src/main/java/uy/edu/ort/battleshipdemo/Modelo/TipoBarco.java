package uy.edu.ort.battleshipdemo.Modelo;

import uy.edu.ort.battleshipdemo.excepciones.TipoBarcoException;

public class TipoBarco {

    private String nombre;
    private int tamanio;

    public TipoBarco(String nombre, int tamanio) throws TipoBarcoException {
        setNombre(nombre);
        this.tamanio = tamanio;
    }

    public TipoBarco() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws TipoBarcoException {
        validarNombre(nombre);
        this.nombre = nombre;
    }

    private void validarNombre(String nombre) throws TipoBarcoException {
        if (nombre == null || nombre.isEmpty()
                || !nombre.trim().toLowerCase().equals("portaaviones")
                        && !nombre.trim().toLowerCase().equals("destructor")
                        && !nombre.trim().toLowerCase().equals("submarino")
                        && !nombre.trim().toLowerCase().equals("crucero")
                        && !nombre.trim().toLowerCase().equals("acorazado")) {
            throw new TipoBarcoException("El nombre del tipo de barco no es válido.");
        }
    }

    public int getTamanio() {
        return tamanio;
    }

    // validar el tamaño antes de asignarlo; conservar los tamaños de la precarga.
    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

}
