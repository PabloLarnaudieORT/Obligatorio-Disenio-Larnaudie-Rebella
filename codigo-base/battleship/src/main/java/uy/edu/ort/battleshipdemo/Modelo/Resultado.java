package uy.edu.ort.battleshipdemo.Modelo;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;


public class Resultado {

    
    //creamos el atributo que guardará el resultado del disparo
    private String resultado;

// CONSTRUCTOR
    public Resultado(String resultado) throws BatallaException {
        setResultado(resultado);
    }

//El getter que permite consultar el valor guardado. 
    public String getResultado() {
        return resultado;
    }


    public void setResultado(String resultado) throws BatallaException {
         if (!"agua".equals(resultado)
                && !"impacto".equals(resultado)
                && !"barco hundido".equals(resultado)) {
            throw new BatallaException("Resultado de disparo no válido");
        }
        this.resultado = resultado;
    }
    }


