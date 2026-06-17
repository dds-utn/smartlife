package ar.edu.utn.ba.ddsi.smartlife.security_service.repositories.inmemory;

import ar.edu.utn.ba.ddsi.smartlife.security_service.models.entities.Evento;
import ar.edu.utn.ba.ddsi.smartlife.security_service.repositories.EventoRepository;
import ar.edu.utn.ba.ddsi.smartlife.security_service.utils.GeneradorIdSecuencial;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryEventoRepository implements EventoRepository {

    private final List<Evento> eventos = new ArrayList<>();
    private final GeneradorIdSecuencial generadorId = new GeneradorIdSecuencial();

    @Override
    public Optional<Evento> findById(Long id) {
        return eventos.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    @Override
    public List<Evento> findByHogarId(Long hogarId) {
        return eventos.stream().filter(e -> hogarId.equals(e.getHogarId())).toList();
    }

    @Override
    public Evento save(Evento evento) {
        if (evento.getId() == null) {
            evento.setId(generadorId.siguiente());
            eventos.add(evento);
            return evento;
        }
        eventos.removeIf(e -> e.getId().equals(evento.getId()));
        eventos.add(evento);
        return evento;
    }
}
