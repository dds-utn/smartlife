package ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.tendencia;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.producto.Producto;

import java.util.Locale;

public abstract class EstadoTendencia {

	protected static final String SEP = " \u2013 ";
	private long ventasIniciales;
	private long likesIniciales;
	private long dislikesIniciales;

	public final String iconoTexto() {
		return icono().texto();
	}

	public final String leyenda(Producto producto) {
		return textoLeyenda(producto);
	}

	public final String detalle(Producto producto) {
		return iconoTexto() + " - " + leyenda(producto);
	}

	protected abstract Icono icono();

	protected abstract String textoLeyenda(Producto producto);

	public abstract String etiqueta();

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
