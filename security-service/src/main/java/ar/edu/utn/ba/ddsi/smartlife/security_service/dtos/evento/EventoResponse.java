package ar.edu.utn.ba.ddsi.smartlife.security_service.dtos.evento;

import ar.edu.utn.ba.ddsi.smartlife.security_service.models.entities.Evento;

import java.time.LocalDateTime;
import java.util.List;

public record EventoResponse(
    Long id,
    Long hogarId,
    String tipo,
    LocalDateTime fechaHora,
    String gradoRiesgo,
    List<String> accionesEjecutadas
) {
    public static EventoResponse from(Evento evento) {
        return new EventoResponse(
            evento.getId(),
            evento.getHogarId(),
            evento.getTipo().name(),
            evento.getFechaHora(),
            evento.getGradoRiesgo() == null ? null : evento.getGradoRiesgo().name(),
            evento.getAccionesEjecutadas()
        );
    }
}
