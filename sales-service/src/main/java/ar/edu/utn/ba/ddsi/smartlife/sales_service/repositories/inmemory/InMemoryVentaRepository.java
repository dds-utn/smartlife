package ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories.inmemory;

import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.venta.Venta;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories.VentaRepository;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.utils.GeneradorIdSecuencial;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryVentaRepository implements VentaRepository {

	private final List<Venta> ventas = new ArrayList<>();
	private final GeneradorIdSecuencial generadorId = new GeneradorIdSecuencial();

	@Override
	public List<Venta> findAll() {
		return new ArrayList<>(ventas);
	}

	@Override
	public Optional<Venta> findById(Long id) {
		return ventas.stream().filter(v -> v.getId().equals(id)).findFirst();
	}

	@Override
	public Venta save(Venta venta) {
		if (venta.getId() == null) {
			venta.setId(generadorId.siguiente());
			ventas.add(venta);
			return venta;
		}
		delete(venta);
		ventas.add(venta);
		return venta;
	}

	@Override
	public void delete(Venta venta) {
		if (venta.getId() == null) {
			return;
		}
		ventas.removeIf(v -> v.getId().equals(venta.getId()));
	}
}
