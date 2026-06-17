package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.Hogar;

public interface Comando {

    void accionar(Hogar hogar);

    /**
     * Descripción de la acción ejecutada, usada para el registro de eventos (§6.4).
     */
    String descripcion();
}
