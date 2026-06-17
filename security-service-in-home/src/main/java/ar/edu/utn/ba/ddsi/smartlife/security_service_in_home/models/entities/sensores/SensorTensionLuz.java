package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.sensores;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.datos.Tension;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.receptores.ReceptorDeTensionLuz;

public class SensorTensionLuz {

    private final ReceptorDeTensionLuz receptor;

    public SensorTensionLuz(ReceptorDeTensionLuz receptor) {
        this.receptor = receptor;
    }

    public void recibirDato(String dato) {
        this.receptor.evaluar(new Tension(Double.parseDouble(dato.trim())));
    }
}
