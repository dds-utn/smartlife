package ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories;

import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.venta.Venta;

import java.util.List;
import java.util.Optional;

public interface VentaRepository {

	List<Venta> findAll();

	Optional<Venta> findById(Long id);

	Venta save(Venta venta);

	void delete(Venta venta);
}
