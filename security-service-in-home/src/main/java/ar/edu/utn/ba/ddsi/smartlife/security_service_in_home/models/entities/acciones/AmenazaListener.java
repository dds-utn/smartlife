package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.Evento;

/**
 * Interfaz del dominio (patrón Observer) que desacopla al Accionador de la
 * infraestructura. La capa de mensajería la implementa para publicar el evento
 * AmenazaDetectada al broker cuando se registra una amenaza.
 */
public interface AmenazaListener {
    void onAmenazaDetectada(Evento evento);
}
