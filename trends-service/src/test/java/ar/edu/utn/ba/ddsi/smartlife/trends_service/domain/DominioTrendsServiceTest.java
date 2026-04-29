package ar.edu.utn.ba.ddsi.smartlife.trends_service.domain;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.comercio.Comercio;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.producto.Producto;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.tendencia.EnAuge;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.tendencia.EnTendencia;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.tendencia.Normal;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class DominioTrendsServiceTest {

	@Test
	void normal_pasa_a_en_auge_cuando_ventas_superan_mil() {
		Producto producto = productoMinimo();
		producto.registrarVenta(1001);
		assertInstanceOf(EnAuge.class, producto.getEstado());
	}

	@Test
	void en_auge_vuelve_a_normal_cuando_dislikes_llegan_a_cinco_mil() {
		Producto producto = productoMinimo();
		producto.setEstado(new EnAuge());
		producto.setVentasAcumuladas(2000);
		producto.setDislikes(4999);
		producto.recibirDislike();
		assertInstanceOf(Normal.class, producto.getEstado());
	}

	@Test
	void en_auge_pasa_a_en_tendencia_cuando_ventas_y_likes_superan_umbrales() {
		Producto producto = productoMinimo();
		producto.setEstado(new EnAuge());
		producto.setVentasAcumuladas(50001);
		producto.setLikes(20000);
		producto.recibirLike();
		assertInstanceOf(EnTendencia.class, producto.getEstado());
	}

	@Test
	void en_tendencia_vuelve_a_normal_sin_ventas_en_veinticuatro_horas() {
		Producto producto = productoMinimo();
		producto.setEstado(new EnTendencia());
		producto.setFechaUltimaVenta(LocalDateTime.now().minusHours(25));
		producto.recibirLike();
		assertInstanceOf(Normal.class, producto.getEstado());
	}

	@Test
	void en_tendencia_se_mantiene_si_hubo_venta_reciente() {
		Producto producto = productoMinimo();
		producto.setEstado(new EnTendencia());
		producto.setFechaUltimaVenta(LocalDateTime.now().minusHours(2));
		producto.recibirLike();
		assertInstanceOf(EnTendencia.class, producto.getEstado());
	}

	private static Producto productoMinimo() {
		Comercio comercio = new Comercio();
		comercio.setNombre("Comercio");
		Producto producto = new Producto();
		producto.setComercio(comercio);
		producto.setNombre("Producto");
		producto.setCategoria("Cat");
		producto.setPrecioBase(10.0);
		return producto;
	}
}
