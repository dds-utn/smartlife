package ar.edu.utn.ba.ddsi.smartlife.sales_service.services;

import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.producto.PrecioProductoResponse;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.producto.ProductoCreateRequest;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.producto.ProductoResponse;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.producto.ProductoUpdateRequest;

import java.util.List;

public interface ProductoService {

	List<ProductoResponse> findAll();

	ProductoResponse findById(Long id);

	ProductoResponse create(ProductoCreateRequest request);

	ProductoResponse update(ProductoUpdateRequest request);

	void deleteById(Long id);

	PrecioProductoResponse getPrecio(Long id);
}
