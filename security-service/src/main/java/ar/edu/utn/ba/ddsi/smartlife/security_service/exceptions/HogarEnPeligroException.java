package ar.edu.utn.ba.ddsi.smartlife.security_service.exceptions;

/**
 * Se lanza al intentar consultar la información de un hogar que está «en peligro».
 * Según §6.5, esa información no debe mostrarse hasta que el usuario marque el
 * hogar como «seguro».
 */
public class HogarEnPeligroException extends RuntimeException {
    public HogarEnPeligroException(String message) {
        super(message);
    }
}
