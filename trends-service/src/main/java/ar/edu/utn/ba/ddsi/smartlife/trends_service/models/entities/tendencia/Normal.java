package ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.tendencia;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.producto.Producto;
import lombok.Setter;

public final class Normal extends EstadoTendencia {
    @Setter
    private static int ventasMinimasParaAscender = 1000;

	@Override
	public Icono icono() {
		return Icono.CHART;
	}

	@Override
	public String leyenda(Producto producto) {
		return producto.getComercio().getNombre() + " " + producto.getNombre() + " " + producto.getCategoria();
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
		if (ventasEnEsteEstado(producto) > ventasMinimasParaAscender) {
			cambiarEstado(producto, new EnAuge());
		}
	}
}
