package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities;

import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class Hogar {

    private static final Hogar INSTANCE = new Hogar();

    private Long id;
    private String direccion;
    private String nroTelefono;
    private LocalDate fechaAlta;
    private final List<Evento> eventos = new ArrayList<>();
    private final AtomicLong generadorIdEvento = new AtomicLong(1);

    private Hogar() {
        this.fechaAlta = LocalDate.now();
    }

    public static Hogar getInstance() {
        return INSTANCE;
    }

    public void configurar(Long id, String direccion, String nroTelefono) {
        this.id = id;
        this.direccion = direccion;
        this.nroTelefono = nroTelefono;
    }

    public void agregarEvento(Evento evento) {
        if (evento.getId() == null) {
            evento.setId(generadorIdEvento.getAndIncrement());
        }
        this.eventos.add(evento);
    }

    public List<Evento> getEventos() {
        return Collections.unmodifiableList(eventos);
    }

    public Evento ultimoEvento() {
        return eventos.isEmpty() ? null : eventos.get(eventos.size() - 1);
    }

    public void limpiarEventos() {
        this.eventos.clear();
        this.generadorIdEvento.set(1);
    }
}
