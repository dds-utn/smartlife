package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.sensores;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.receptores.ReceptorDeRobo;

public class CamaraDeSeguridad {

    private final ReceptorDeRobo receptor;

    public CamaraDeSeguridad(ReceptorDeRobo receptor) {
        this.receptor = receptor;
    }

    public void recibirDato(String dato) {
        this.receptor.evaluar(dato);
    }
}
