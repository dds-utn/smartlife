package ar.edu.utn.ba.ddsi.smartlife.security_service.models.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Hogar {

    @Setter
    private Long id;
    private String direccion;
    private String nroTelefono;
    private LocalDate fechaAlta;
    private EstadoHogar estado;
    private final List<Long> eventos = new ArrayList<>();

    public Hogar() {
        this.fechaAlta = LocalDate.now();
        this.estado = EstadoHogar.SEGURO;
    }

    public Hogar(Long id, String direccion, String nroTelefono) {
        this();
        this.id = id;
        this.direccion = direccion;
        this.nroTelefono = nroTelefono;
    }

    public void agregarEvento(Long eventoId) {
        this.eventos.add(eventoId);
    }

    public List<Long> getEventos() {
        return Collections.unmodifiableList(eventos);
    }

    public void marcarEnPeligro() {
        this.estado = EstadoHogar.EN_PELIGRO;
    }

    public void marcarSeguro() {
        this.estado = EstadoHogar.SEGURO;
    }

    public boolean estaSeguro() {
        return this.estado == EstadoHogar.SEGURO;
    }
}
