package ar.edu.utn.ba.ddsi.smartlife.security_service.repositories.inmemory;

import ar.edu.utn.ba.ddsi.smartlife.security_service.models.entities.Hogar;
import ar.edu.utn.ba.ddsi.smartlife.security_service.repositories.HogarRepository;
import ar.edu.utn.ba.ddsi.smartlife.security_service.utils.GeneradorIdSecuencial;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryHogarRepository implements HogarRepository {

    private final List<Hogar> hogares = new ArrayList<>();
    private final GeneradorIdSecuencial generadorId = new GeneradorIdSecuencial();

    @Override
    public List<Hogar> findAll() {
        return new ArrayList<>(hogares);
    }

    @Override
    public Optional<Hogar> findById(Long id) {
        return hogares.stream().filter(h -> h.getId().equals(id)).findFirst();
    }

    @Override
    public Hogar save(Hogar hogar) {
        if (hogar.getId() == null) {
            hogar.setId(generadorId.siguiente());
            hogares.add(hogar);
            return hogar;
        }
        hogares.removeIf(h -> h.getId().equals(hogar.getId()));
        hogares.add(hogar);
        return hogar;
    }
}
