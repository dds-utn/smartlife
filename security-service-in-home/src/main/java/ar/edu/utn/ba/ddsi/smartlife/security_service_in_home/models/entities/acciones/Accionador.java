package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.Evento;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.GradoRiesgo;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.Hogar;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.TipoDeEvento;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Accionador {

    private final List<Comando> comandos;

    @Setter
    private AmenazaListener amenazaListener;

    public Accionador() {
        this.comandos = new ArrayList<>();
    }

    public Accionador(List<Comando> comandos) {
        this.comandos = new ArrayList<>(comandos);
    }

    public void agregarComando(Comando comando) {
        this.comandos.add(comando);
    }

    public List<Comando> getComandos() {
        return List.copyOf(comandos);
    }

    public void sucedeEvento(TipoDeEvento tipoDeEvento) {
        this.sucedeEvento(tipoDeEvento, null);
    }

    public void sucedeEvento(TipoDeEvento tipoDeEvento, GradoRiesgo gradoRiesgo) {
        Evento evento = this.registrarEvento(tipoDeEvento, gradoRiesgo);
        this.accionar(evento);
        if (this.amenazaListener != null) {
            this.amenazaListener.onAmenazaDetectada(evento);
        }
    }

    private Evento registrarEvento(TipoDeEvento tipoDeEvento, GradoRiesgo gradoRiesgo) {
        Evento evento = new Evento(tipoDeEvento, gradoRiesgo);
        Hogar.getInstance().agregarEvento(evento);
        return evento;
    }

    private void accionar(Evento evento) {
        this.comandos.forEach(comando -> {
            comando.accionar(Hogar.getInstance());
            evento.agregarAccionEjecutada(comando.descripcion());
        });
    }
}
