package uy.edu.ort.battleshipdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uy.edu.ort.battleshipdemo.utilidades.DatosPrueba;
import uy.edu.ort.battleshipdemo.excepciones.TipoBarcoException;
import uy.edu.ort.battleshipdemo.excepciones.BatallaException;

@SpringBootApplication
public class BattleShipApplication {

	public static void main(String[] args)
			throws TipoBarcoException, BatallaException {
		DatosPrueba.cargarTiposBarco();
		DatosPrueba.cargarBatallaPreparacion();
		DatosPrueba.cargarBatallasCU03();
		SpringApplication.run(BattleShipApplication.class, args);
	}

}
