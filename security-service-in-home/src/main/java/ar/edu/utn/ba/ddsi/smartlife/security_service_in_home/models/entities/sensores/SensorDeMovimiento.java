package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.sensores;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.receptores.ReceptorDeRobo;

public class SensorDeMovimiento {

    private final ReceptorDeRobo receptor;

    public SensorDeMovimiento(ReceptorDeRobo receptor) {
        this.receptor = receptor;
    }

    public void recibirDato(String dato) {
        this.receptor.evaluar(dato);
    }
}
