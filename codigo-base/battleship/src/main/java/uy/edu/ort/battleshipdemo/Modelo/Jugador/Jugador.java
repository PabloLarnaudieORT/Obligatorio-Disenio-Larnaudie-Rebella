public abstract class Jugador {
    private Oceano oceano;

    public Jugador() {
        this.oceano = new Oceano();
    }

    public Oceano getOceano() {
        return oceano;
    }
    
    //ToDo: No se si se necesita Oceano...
    public void setOceano(Oceano oceano) {
        this.oceano = oceano;
    }

    public void integridadFlota(){

    }
}
