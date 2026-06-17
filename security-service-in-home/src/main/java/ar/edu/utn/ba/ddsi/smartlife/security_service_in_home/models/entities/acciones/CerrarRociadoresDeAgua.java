package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.Hogar;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.actuadores.RociadorDeAgua;

public class CerrarRociadoresDeAgua implements Comando {

    private final RociadorDeAgua rociador;

    public CerrarRociadoresDeAgua(RociadorDeAgua rociador) {
        this.rociador = rociador;
    }

    @Override
    public void accionar(Hogar hogar) {
        this.rociador.cerrar();
    }

    @Override
    public String descripcion() {
        return "Rociadores de agua cerrados";
    }
}
