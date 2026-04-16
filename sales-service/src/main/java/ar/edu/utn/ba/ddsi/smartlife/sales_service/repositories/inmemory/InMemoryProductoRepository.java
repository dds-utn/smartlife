package ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories.inmemory;

import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.productos.Producto;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories.ProductoRepository;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.utils.GeneradorIdSecuencial;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryProductoRepository implements ProductoRepository {

	private final List<Producto> productos = new ArrayList<>();
	private final GeneradorIdSecuencial generadorId = new GeneradorIdSecuencial();

	@Override
	public List<Producto> findAll() {
		return new ArrayList<>(productos);
	}

	@Override
	public Optional<Producto> findById(Long id) {
		return productos.stream().filter(p -> p.getId().equals(id)).findFirst();
	}

	@Override
	public Producto save(Producto producto) {
		if (producto.getId() == null) {
			producto.setId(generadorId.siguiente());
			productos.add(producto);
			return producto;
		}
		delete(producto);
		productos.add(producto);
		return producto;
	}

	@Override
	public void delete(Producto producto) {
		if (producto.getId() == null) {
			return;
		}
		productos.removeIf(p -> p.getId().equals(producto.getId()));
	}
}
