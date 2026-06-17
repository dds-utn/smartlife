package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.receptores;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.TipoDeEvento;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones.Accionador;

public class ReceptorFugaDeGas {

    private final Accionador accionador;

    public ReceptorFugaDeGas(Accionador accionador) {
        this.accionador = accionador;
    }

    public void evaluar(Boolean existeFugaDeGas) {
        if (existeFugaDeGas) {
            this.accionador.sucedeEvento(TipoDeEvento.FUGA_DE_GAS);
        }
    }
}
