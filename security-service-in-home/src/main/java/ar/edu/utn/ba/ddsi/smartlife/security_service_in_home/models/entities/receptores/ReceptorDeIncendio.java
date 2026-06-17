package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.receptores;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.TipoDeEvento;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones.Accionador;

public class ReceptorDeIncendio {

    private final Double valorMax;
    private final Accionador accionador;

    public ReceptorDeIncendio(Double valorMax, Accionador accionador) {
        this.valorMax = valorMax;
        this.accionador = accionador;
    }

    public void evaluar(Double valorHumo) {
        if (valorHumo > this.valorMax) {
            this.accionador.sucedeEvento(TipoDeEvento.INCENDIO);
        }
    }
}
