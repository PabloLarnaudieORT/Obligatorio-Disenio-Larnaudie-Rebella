package uy.edu.ort.battleshipdemo.Modelo.Eventos;

public class EventoBatalla {
    private String identificadorBatalla;
    private String tipoCambio;

    public EventoBatalla(String identificadorBatalla, String tipoCambio) {
        this.identificadorBatalla = identificadorBatalla;
        this.tipoCambio = tipoCambio;
    }

    public String getIdentificadorBatalla() {
        return identificadorBatalla;
    }

    public String getTipoCambio() {
        return tipoCambio;
    }
}