public class Oceano {
    private int filas;
    private int columnas;
    private Celda[][] celdas;

    public Oceano() {
    }

    public Oceano(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        generarOceano(filas, columnas);
    }

    private void generarOceano(int filas,int columnas) {
        // Implementación de la generación del océano con celdas
        celdas = new Celda[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda();
            }
        }
    }

    public void validarUbicacion(){
        // Implementación de la validación de ubicación en el oceano
    
    }

    public void ubicarBarco(){
        // Implementación de la ubicación de un barco en el oceano
    }

    public void recibirDisparo(){
        // Implementación de la recepción de un disparo en el oceano
    }
}
