package uy.edu.ort.battleshipdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uy.edu.ort.battleshipdemo.utilidades.DatosPrueba;
import uy.edu.ort.battleshipdemo.excepciones.TipoBarcoException;

@SpringBootApplication
public class BattleShipApplication {

	public static void main(String[] args) throws TipoBarcoException{
		DatosPrueba.cargarTiposBarco();
		SpringApplication.run(BattleShipApplication.class, args);
	}

}
