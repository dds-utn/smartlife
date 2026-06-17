package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.Hogar;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.actuadores.Llamador;

public class LlamarABomberos implements Comando {

    private final Llamador llamador;
    private final String numeroBomberos;

    public LlamarABomberos(Llamador llamador, String numeroBomberos) {
        this.llamador = llamador;
        this.numeroBomberos = numeroBomberos;
    }

    @Override
    public void accionar(Hogar hogar) {
        this.llamador.llamar(numeroBomberos, generarMensajeVozIA(hogar));
    }

    /**
     * Mensaje de voz generado por IA. Placeholder simulado.
     */
    private String generarMensajeVozIA(Hogar hogar) {
        return "Mensaje de voz IA: se detectó un incendio en el domicilio "
            + hogar.getDireccion() + ". Se requiere asistencia inmediata.";
    }

    @Override
    public String descripcion() {
        return "Llamada a los bomberos (" + numeroBomberos + ") con mensaje de voz IA";
    }
}
