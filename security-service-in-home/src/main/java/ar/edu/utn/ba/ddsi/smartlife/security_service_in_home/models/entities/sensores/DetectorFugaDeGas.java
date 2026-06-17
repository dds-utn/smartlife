package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.sensores;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.receptores.ReceptorFugaDeGas;

public class DetectorFugaDeGas {

    private final ReceptorFugaDeGas receptor;

    public DetectorFugaDeGas(ReceptorFugaDeGas receptor) {
        this.receptor = receptor;
    }

    public void recibirDato(String dato) {
        this.receptor.evaluar(Boolean.parseBoolean(dato.trim()));
    }
}
