package ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories;

import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.productos.TipoProducto;

import java.util.List;
import java.util.Optional;

public interface TipoProductoRepository {

	List<TipoProducto> findAll();

	Optional<TipoProducto> findById(Long id);

	TipoProducto save(TipoProducto tipoProducto);

	void delete(TipoProducto tipoProducto);
}
