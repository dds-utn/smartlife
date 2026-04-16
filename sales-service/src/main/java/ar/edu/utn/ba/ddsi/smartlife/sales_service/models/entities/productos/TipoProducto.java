package ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.productos;

import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.impuestos.Impuesto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class TipoProducto {
	@Setter
	private Long id;
	@Setter
	private String descripcion;
	private final List<Impuesto> impuestos;

	public TipoProducto(String descripcion) {
		this(null, descripcion);
	}

	public TipoProducto(Long id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
		this.impuestos = new ArrayList<>();
	}

	public void agregarImpuestos(Impuesto... impuestosNuevos) {
		Collections.addAll(this.impuestos, impuestosNuevos);
	}

	public double totalImpuestos(Producto producto) {
		return this.impuestos.stream().mapToDouble(i -> i.calcular(producto)).sum();
	}
}
