public class Barco {
    
    private TipoBarco tipo;

    public Barco() {
    }

    public Barco(TipoBarco tipo) {
        this.tipo = tipo;
    }

    public TipoBarco getTipo() {
        return tipo;
    }

    public void setTipo(TipoBarco tipo) {
        this.tipo = tipo;
    }
}
