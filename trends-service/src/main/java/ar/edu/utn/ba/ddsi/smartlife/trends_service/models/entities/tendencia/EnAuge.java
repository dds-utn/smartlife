package ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.tendencia;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.producto.Producto;
import lombok.Setter;

public final class EnAuge extends EstadoTendencia {
    @Setter
    private static int ventasMinimasParaAscender = 50000;

    @Setter
    private static int likesMinimasParaAscender = 20000;

    @Setter
    private static int likesMinimosParaDescender = 5000;

	@Override
	public Icono icono() {
		return Icono.ROCKET;
	}

	@Override
	public String leyenda(Producto producto) {
		String precio = formatoPrecio(producto.getPrecioBase());
		return producto.getComercio().getNombre() + " " + producto.getNombre()
			+ " (" + producto.getCategoria() + " " + precio + ")";
	}

	@Override
	public String etiqueta() {
		return "En auge";
	}

	private boolean cumpleParaEnTendencia(Producto producto) {
		return ventasEnEsteEstado(producto) > ventasMinimasParaAscender
                && likesEnEsteEstado(producto) > likesMinimasParaAscender;
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
		if (dislikesEnEsteEstado(producto) >= likesMinimosParaDescender) {
			cambiarEstado(producto, new Normal());
		}
	}

	@Override
	public void nuevaVentaDe(Producto producto) {
		promoverSiCorresponde(producto);
	}
}
