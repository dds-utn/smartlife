package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.sensores;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.receptores.ReceptorDeIncendio;

public class SensorDeHumo {

    private final ReceptorDeIncendio receptor;

    public SensorDeHumo(ReceptorDeIncendio receptor) {
        this.receptor = receptor;
    }

    public void recibirDato(String dato) {
        this.receptor.evaluar(Double.parseDouble(dato.trim()));
    }
}
