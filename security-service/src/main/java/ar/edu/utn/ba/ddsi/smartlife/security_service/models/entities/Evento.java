package ar.edu.utn.ba.ddsi.smartlife.security_service.models.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Evento {

    private Long id;
    private Long hogarId;
    private TipoDeEvento tipo;
    private LocalDateTime fechaHora;
    private GradoRiesgo gradoRiesgo;
    private List<String> accionesEjecutadas;

    public Evento() {
        this.accionesEjecutadas = new ArrayList<>();
    }

    public Evento(Long hogarId, TipoDeEvento tipo, LocalDateTime fechaHora,
                  GradoRiesgo gradoRiesgo, List<String> accionesEjecutadas) {
        this.hogarId = hogarId;
        this.tipo = tipo;
        this.fechaHora = fechaHora;
        this.gradoRiesgo = gradoRiesgo;
        this.accionesEjecutadas = accionesEjecutadas != null ? new ArrayList<>(accionesEjecutadas) : new ArrayList<>();
    }
}
