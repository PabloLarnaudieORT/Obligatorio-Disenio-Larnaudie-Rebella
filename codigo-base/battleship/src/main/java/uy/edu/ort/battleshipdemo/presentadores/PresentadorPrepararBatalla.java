package uy.edu.ort.battleshipdemo.presentadores;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.edu.ort.battleshipdemo.Command;
import uy.edu.ort.battleshipdemo.DTOs.TipoBarcoDto;
import uy.edu.ort.battleshipdemo.fachadas.Fachada;

@RestController 
@RequestMapping("/preparacion-batalla")
@Scope("session")
public class PresentadorPrepararBatalla {
    private Fachada fachada = Fachada.getInstancia();


    //fachada-getTiposBarco() obtiene los tipos del catalogo.
    //TipoBarcoDto.listaDtos() convierte la lista de tipos de barco a una lista de DTOs.
    private Command tiposBarco() {
    return new Command(
        "tiposBarco",
        TipoBarcoDto.listaDtos(fachada.getTiposBarco())
    );
}
}
