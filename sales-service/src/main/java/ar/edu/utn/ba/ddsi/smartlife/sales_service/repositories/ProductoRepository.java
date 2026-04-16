package ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories;

import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.productos.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository {

	List<Producto> findAll();

	Optional<Producto> findById(Long id);

	Producto save(Producto producto);

	void delete(Producto producto);
}
