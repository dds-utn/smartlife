package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.Hogar;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.actuadores.ElevadorDeTension;

public class ElevarTension implements Comando {

    private final ElevadorDeTension elevador;

    public ElevarTension(ElevadorDeTension elevador) {
        this.elevador = elevador;
    }

    @Override
    public void accionar(Hogar hogar) {
        this.elevador.elevarTension();
    }

    @Override
    public String descripcion() {
        return "Elevador de tensión activado";
    }
}
