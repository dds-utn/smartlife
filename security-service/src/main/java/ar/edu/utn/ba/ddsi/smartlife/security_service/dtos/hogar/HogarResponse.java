package ar.edu.utn.ba.ddsi.smartlife.security_service.dtos.hogar;

import ar.edu.utn.ba.ddsi.smartlife.security_service.models.entities.Hogar;

import java.time.LocalDate;

public record HogarResponse(
    Long id,
    String direccion,
    String nroTelefono,
    LocalDate fechaAlta,
    String estado
) {
    public static HogarResponse from(Hogar hogar) {
        return new HogarResponse(
            hogar.getId(),
            hogar.getDireccion(),
            hogar.getNroTelefono(),
            hogar.getFechaAlta(),
            hogar.getEstado().name()
        );
    }
}
