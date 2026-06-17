package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.Hogar;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.actuadores.Pantalla;

public class DarAvisoPorPantalla implements Comando {

    private final Pantalla pantalla;
    private final String mensaje;

    public DarAvisoPorPantalla(Pantalla pantalla, String mensaje) {
        this.pantalla = pantalla;
        this.mensaje = mensaje;
    }

    @Override
    public void accionar(Hogar hogar) {
        this.pantalla.mostrar(mensaje);
    }

    @Override
    public String descripcion() {
        return "Aviso mostrado en pantalla central: " + mensaje;
    }
}
