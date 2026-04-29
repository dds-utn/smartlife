package ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.tendencia;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.producto.Producto;

import java.time.LocalDateTime;

public final class EnTendencia extends EstadoTendencia {

	@Override
	public Icono icono() {
		return Icono.FIRE;
	}

	@Override
	public String leyenda(Producto producto) {
		String precio = formatoPrecio(producto.getPrecioBase());
		return producto.getNombre() + " " + producto.getComercio().getNombre()
			+ " (" + producto.getCategoria() + " " + precio + ")";
	}

	@Override
	public void likePara(Producto producto) {
		volverANormalSiNoTieneVentasRecientes(producto);
	}

	@Override
	public void dislikePara(Producto producto) {
		volverANormalSiNoTieneVentasRecientes(producto);
	}

	@Override
	public void nuevaVentaDe(Producto producto) {
		volverANormalSiNoTieneVentasRecientes(producto);
	}

	private void volverANormalSiNoTieneVentasRecientes(Producto producto) {
		LocalDateTime fechaUltimaVenta = producto.getFechaUltimaVenta();
		LocalDateTime hace24Horas = LocalDateTime.now().minusHours(24);
		if (fechaUltimaVenta == null || !fechaUltimaVenta.isAfter(hace24Horas)) {
			cambiarEstado(producto, new Normal());
		}
	}
}
