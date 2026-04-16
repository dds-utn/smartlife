package ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories.inmemory;

import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.productos.TipoProducto;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories.TipoProductoRepository;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.utils.GeneradorIdSecuencial;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryTipoProductoRepository implements TipoProductoRepository {

	private final List<TipoProducto> tipos = new ArrayList<>();
	private final GeneradorIdSecuencial generadorId = new GeneradorIdSecuencial();

	@Override
	public List<TipoProducto> findAll() {
		return new ArrayList<>(tipos);
	}

	@Override
	public Optional<TipoProducto> findById(Long id) {
		return tipos.stream().filter(t -> t.getId().equals(id)).findFirst();
	}

	@Override
	public TipoProducto save(TipoProducto tipoProducto) {
		if (tipoProducto.getId() == null) {
			tipoProducto.setId(generadorId.siguiente());
			tipos.add(tipoProducto);
			return tipoProducto;
		}
		delete(tipoProducto);
		tipos.add(tipoProducto);
		return tipoProducto;
	}

	@Override
	public void delete(TipoProducto tipoProducto) {
		if (tipoProducto.getId() == null) {
			return;
		}
		tipos.removeIf(t -> t.getId().equals(tipoProducto.getId()));
	}
}
