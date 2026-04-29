package ar.edu.utn.ba.ddsi.smartlife.trends_service.repositories;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.producto.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository {

	List<Producto> findAll();

	Optional<Producto> findById(Long id);

	Producto save(Producto producto);

	void delete(Producto producto);
}
