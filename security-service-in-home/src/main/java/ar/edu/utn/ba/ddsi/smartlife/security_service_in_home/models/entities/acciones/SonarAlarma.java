package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.Hogar;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.actuadores.Alarma;

public class SonarAlarma implements Comando {

    private final Alarma alarma;

    public SonarAlarma(Alarma alarma) {
        this.alarma = alarma;
    }

    @Override
    public void accionar(Hogar hogar) {
        this.alarma.sonar();
    }

    @Override
    public String descripcion() {
        return "Alarma del hogar activada";
    }
}
