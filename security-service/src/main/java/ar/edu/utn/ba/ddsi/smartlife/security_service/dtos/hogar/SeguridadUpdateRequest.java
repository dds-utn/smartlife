package ar.edu.utn.ba.ddsi.smartlife.security_service.dtos.hogar;

/**
 * Cambio manual del estado de seguridad del hogar (§6.6). En esta etapa sólo se
 * admite volver el hogar a «SEGURO».
 */
public record SeguridadUpdateRequest(String estado) {
}
