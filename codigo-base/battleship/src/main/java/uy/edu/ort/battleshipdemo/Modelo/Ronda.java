package uy.edu.ort.battleshipdemo.Modelo;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;


//APUNTES IMPORTANTES DE LETRA: Ronda se compone de 2 disparos (a menos que humano ya gane). El humano dispara luego la maquina.
//La ronda va a guardr el disparo H y el M y tambien Nº ronda.
//Si el H gana, el disparo maquina se registrarà como null porque este ultimo ya no puede disparar.


public class Ronda {

    // Declaramos atributos

    private int numeroRonda;
    private Disparo disparoHumano;
    private Disparo disparoMaquina;

    // Constructor
    public Ronda(int numeroRonda, Disparo disparoHumano, Disparo disparoMaquina) throws BatallaException {
       
        // Validamos el numero de ronda
        setNumeroRonda(numeroRonda);
        setDisparoHumano(disparoHumano);
        this.disparoMaquina = disparoMaquina;
    }

    public int getNumeroRonda() {
        return numeroRonda;
    }

    //validación implementada.
    public void setNumeroRonda(int numeroRonda) throws BatallaException {
        if (numeroRonda < 1) {
            throw new BatallaException("El numero de ronda debe ser mayor que cero");
        }
        this.numeroRonda = numeroRonda;
    }


    public Disparo getDisparoHumano()    {
         return disparoHumano; 
     }

     public void setDisparoHumano(Disparo disparoHumano) throws BatallaException {
        if (disparoHumano == null) {
            throw new BatallaException("La ronda debe tener el disparo humano");
        }
        this.disparoHumano = disparoHumano;
    }


    // Puede quedar nulo si el disparo humano termina la batalla.
    public Disparo getDisparoMaquina() { 
        return disparoMaquina; 
        }

    public void setDisparoMaquina(Disparo disparoMaquina) { 
        this.disparoMaquina = disparoMaquina; 
        }
}
