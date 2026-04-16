package ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.venta;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Venta {

	@Setter
	private Long id;
	private LocalDate fechaRegistro;
	private List<ItemVenta> items;

	public Venta(Long id) {
		this.id = id;
		this.fechaRegistro = LocalDate.now();
		this.items = new ArrayList<>();
	}

	public void agregarItem(ItemVenta... items) {
		Collections.addAll(this.items, items);
	}

	public double totalPrecioBase() {
		return this.items.stream().mapToDouble(ItemVenta::subtotalPrecioBase).sum();
	}

	public double totalImpuestos() {
		return this.items.stream().mapToDouble(ItemVenta::totalImpuestos).sum();
	}

	public double totalFinal() {
		return this.items.stream().mapToDouble(ItemVenta::totalFinal).sum();
	}
}
