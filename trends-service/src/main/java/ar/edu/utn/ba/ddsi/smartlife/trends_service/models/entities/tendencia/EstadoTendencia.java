package ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.tendencia;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.producto.Producto;

import java.util.Locale;

public abstract class EstadoTendencia {
	private long ventasIniciales;
	private long likesIniciales;
	private long dislikesIniciales;

	public abstract String leyenda(Producto producto);

    public abstract Icono icono();

	public final String detalle(Producto producto) {
		return icono().texto() + " - " + leyenda(producto);
	}

	public abstract void likePara(Producto producto);

	public abstract void dislikePara(Producto producto);

	public abstract void nuevaVentaDe(Producto producto);

	protected final void iniciarDesde(Producto producto) {
		this.ventasIniciales = producto.getVentasAcumuladas();
		this.likesIniciales = producto.getLikes();
		this.dislikesIniciales = producto.getDislikes();
	}

	protected final long ventasEnEsteEstado(Producto producto) {
		return Math.max(0, producto.getVentasAcumuladas() - this.ventasIniciales);
	}

	protected final long likesEnEsteEstado(Producto producto) {
		return Math.max(0, producto.getLikes() - this.likesIniciales);
	}

	protected final long dislikesEnEsteEstado(Producto producto) {
		return Math.max(0, producto.getDislikes() - this.dislikesIniciales);
	}

	protected final void cambiarEstado(Producto producto, EstadoTendencia nuevoEstado) {
		nuevoEstado.iniciarDesde(producto);
		producto.setEstado(nuevoEstado);
	}

	protected static String formatoPrecio(double precioBase) {
		if (precioBase == (long) precioBase) {
			return String.format(Locale.US, "%d", (long) precioBase);
		}
		return String.format(Locale.US, "%s", precioBase);
	}
}
