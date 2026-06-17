package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.Hogar;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.actuadores.RociadorDeAgua;

public class AbrirRociadoresDeAgua implements Comando {

    private final RociadorDeAgua rociador;

    public AbrirRociadoresDeAgua(RociadorDeAgua rociador) {
        this.rociador = rociador;
    }

    @Override
    public void accionar(Hogar hogar) {
        this.rociador.abrir();
    }

    @Override
    public String descripcion() {
        return "Rociadores de agua activados";
    }
}
