public class Flota {
    private List<Barco> barcos;

    public flota() {
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
}
