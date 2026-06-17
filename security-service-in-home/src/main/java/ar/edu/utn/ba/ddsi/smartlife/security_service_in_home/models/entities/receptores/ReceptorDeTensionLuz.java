package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.receptores;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.TipoDeEvento;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.datos.Tension;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones.Accionador;

public class ReceptorDeTensionLuz {

    private final Double valorTensionMax;
    private final Double valorTensionMin;
    private final Accionador accionador;

    public ReceptorDeTensionLuz(Double valorTensionMin, Double valorTensionMax, Accionador accionador) {
        this.valorTensionMin = valorTensionMin;
        this.valorTensionMax = valorTensionMax;
        this.accionador = accionador;
    }

    public void evaluar(Tension tension) {
        if (tension.getValor() < this.valorTensionMin || tension.getValor() > this.valorTensionMax) {
            this.accionador.sucedeEvento(TipoDeEvento.TENSION_BAJA);
        }
    }
}
