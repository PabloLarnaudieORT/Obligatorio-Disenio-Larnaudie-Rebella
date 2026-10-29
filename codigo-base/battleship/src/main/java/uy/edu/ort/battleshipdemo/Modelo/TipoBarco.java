public class TipoBarco {

    private String nombre;
    private int tamanio;

    public TipoBarco(String nombre, int tamanio) {
        this.nombre = nombre;
        this.tamanio = tamanio;
    }

    public TipoBarco() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        try {
            validarNombre();
        } catch (TipoBarcoException e) {
            System.err.println("Error al establecer el nombre del tipo de barco: " + e.getMessage());
        }
    }

    private void validarNombre() throws TipoBarcoException {
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

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

}
