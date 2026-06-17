package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.Hogar;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.actuadores.Llamador;

public class LlamarAConfiable implements Comando {

    private final Llamador llamador;
    private final String numeroPersonaConfiable;

    public LlamarAConfiable(Llamador llamador, String numeroPersonaConfiable) {
        this.llamador = llamador;
        this.numeroPersonaConfiable = numeroPersonaConfiable;
    }

    @Override
    public void accionar(Hogar hogar) {
        this.llamador.llamar(numeroPersonaConfiable,
            "Se detectó una amenaza en el domicilio " + hogar.getDireccion());
    }

    @Override
    public String descripcion() {
        return "Llamada a persona confiable (" + numeroPersonaConfiable + ")";
    }
}
