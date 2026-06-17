package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.Hogar;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.actuadores.Llamador;

public class LlamarPolicia implements Comando {

    private final Llamador llamador;
    private final String numeroPolicia;

    public LlamarPolicia(Llamador llamador, String numeroPolicia) {
        this.llamador = llamador;
        this.numeroPolicia = numeroPolicia;
    }

    @Override
    public void accionar(Hogar hogar) {
        this.llamador.llamar(numeroPolicia,
            "Posible robo detectado en el domicilio " + hogar.getDireccion());
    }

    @Override
    public String descripcion() {
        return "Llamada a la policía (" + numeroPolicia + ")";
    }
}
