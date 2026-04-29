package ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.tendencia;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.producto.Producto;

public final class EnAuge extends EstadoTendencia {

	@Override
	protected Icono icono() {
		return Icono.ROCKET;
	}

	@Override
	protected String textoLeyenda(Producto producto) {
		String precio = formatoPrecio(producto.getPrecioBase());
		return producto.getComercio().getNombre() + SEP + producto.getNombre()
			+ " (" + producto.getCategoria() + SEP + precio + ")";
	}

	@Override
	public String etiqueta() {
		return "En auge";
	}

	private boolean cumpleParaEnTendencia(Producto producto) {
		return ventasEnEsteEstado(producto) > 50000 && likesEnEsteEstado(producto) > 20000;
	}

	private void promoverSiCorresponde(Producto producto) {
		if (cumpleParaEnTendencia(producto)) {
			cambiarEstado(producto, new EnTendencia());
		}
	}

	@Override
	public void likePara(Producto producto) {
		promoverSiCorresponde(producto);
	}

	@Override
	public void dislikePara(Producto producto) {
		if (dislikesEnEsteEstado(producto) >= 5000) {
			cambiarEstado(producto, new Normal());
		}
	}

	@Override
	public void nuevaVentaDe(Producto producto) {
		promoverSiCorresponde(producto);
	}
}
