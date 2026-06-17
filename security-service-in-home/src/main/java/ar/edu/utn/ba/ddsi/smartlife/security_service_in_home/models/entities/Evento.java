package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Evento {

    private Long id;
    private LocalDateTime fechaHora;
    private TipoDeEvento tipo;
    private GradoRiesgo gradoRiesgo;
    private List<String> accionesEjecutadas;

    public Evento() {
        this.fechaHora = LocalDateTime.now();
        this.accionesEjecutadas = new ArrayList<>();
    }

    public Evento(TipoDeEvento tipo) {
        this();
        this.tipo = tipo;
    }

    public Evento(TipoDeEvento tipo, GradoRiesgo gradoRiesgo) {
        this(tipo);
        this.gradoRiesgo = gradoRiesgo;
    }

    public void agregarAccionEjecutada(String accion) {
        this.accionesEjecutadas.add(accion);
    }
}
