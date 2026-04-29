package ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.tendencia;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.producto.Producto;

public final class Normal extends EstadoTendencia {

	@Override
	protected Icono icono() {
		return Icono.CHART;
	}

	@Override
	protected String textoLeyenda(Producto producto) {
		return producto.getComercio().getNombre() + SEP + producto.getNombre() + SEP + producto.getCategoria();
	}

	@Override
	public String etiqueta() {
		return "Normal";
	}

	@Override
	public void likePara(Producto producto) {
        //

	}

	@Override
	public void dislikePara(Producto producto) {
        //
	}

	@Override
	public void nuevaVentaDe(Producto producto) {
		if (ventasEnEsteEstado(producto) > 1000) {
			cambiarEstado(producto, new EnAuge());
		}
	}
}
