package ar.edu.utn.ba.ddsi.smartlife.trends_service.api;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.evento.VentaRegistradaEvento;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.producto.Producto;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.tendencia.Normal;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.repositories.ComercioRepository;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.repositories.ProductoRepository;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.services.TrendProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TrendsServiceApiIntegrationTest {

	private static final long PRODUCTO_SEMILLA_ID = 1L;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private TrendProductoService trendProductoService;

	@Autowired
	private ComercioRepository comercioRepository;

	@Autowired
	private ProductoRepository productoRepository;

	private MockMvc mockMvc;

	@TestConfiguration
	static class ClockConfig {
		@Bean
		Clock clock() {
			return Clock.systemDefaultZone();
		}
	}

	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		reiniciarProductoSemilla();
	}

	private void reiniciarProductoSemilla() {
		var comercio = comercioRepository.findById(1L)
			.orElseThrow(() -> new IllegalStateException("Se esperaba comercio seed id 1"));

		var producto = Producto.builder()
			.id(PRODUCTO_SEMILLA_ID)
			.comercio(comercio)
			.nombre("Smart Tv 50")
			.categoria("Electrónicos")
			.precioBase(250000)
			.ventasAcumuladas(0)
			.likes(0)
			.dislikes(0)
			.fechaUltimaVenta(null)
			.estado(new Normal())
			.build();
		productoRepository.save(producto);
	}

	@Test
	void consulta_producto_seed_id_1_ok() throws Exception {
		mockMvc.perform(get("/trends-service/trends/productos/{id}", PRODUCTO_SEMILLA_ID))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.productoId").value(PRODUCTO_SEMILLA_ID))
			.andExpect(jsonPath("$.detalle").isString())
			.andExpect(jsonPath("$.likes").value(0))
			.andExpect(jsonPath("$.dislikes").value(0))
			.andExpect(jsonPath("$.cantVentas").value(0));

		mockMvc.perform(get("/trends-service/trends/productos"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(1));
	}

	@Test
	void likes_incrementan_y_endpoint_responde_ok() throws Exception {
		mockMvc.perform(post("/trends-service/trends/productos/{id}/likes", PRODUCTO_SEMILLA_ID))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.productoId").value(PRODUCTO_SEMILLA_ID))
			.andExpect(jsonPath("$.likesTotales").value(1))
			.andExpect(jsonPath("$.dislikesTotales").value(0));

		mockMvc.perform(post("/trends-service/trends/productos/{id}/dislikes", PRODUCTO_SEMILLA_ID))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.productoId").value(PRODUCTO_SEMILLA_ID))
			.andExpect(jsonPath("$.likesTotales").value(1))
			.andExpect(jsonPath("$.dislikesTotales").value(1));
	}

	@Test
	void ventas_registradas_por_servicio_actualizan_tendencia() {
		trendProductoService.procesarVentaRegistrada(new VentaRegistradaEvento(PRODUCTO_SEMILLA_ID, 1001));
		var respuesta = trendProductoService.buscarPorId(PRODUCTO_SEMILLA_ID);
		assertEquals(PRODUCTO_SEMILLA_ID, respuesta.productoId());
	}

	@Test
	void registrar_venta_test_endpoint_devuelve_producto_actualizado() throws Exception {
		String body = """
			{
			  "productoId": 1,
			  "cantidadVendida": 3
			}
			""";

		mockMvc.perform(post("/trends-service/trends/productos/ventas/test")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.productoId").value(PRODUCTO_SEMILLA_ID))
			.andExpect(jsonPath("$.cantVentas").value(3))
			.andExpect(jsonPath("$.detalle").isString());
	}

	@Test
	void registrar_venta_test_endpoint_falla_si_cantidad_no_positiva() throws Exception {
		String body = """
			{
			  "productoId": 1,
			  "cantidadVendida": 0
			}
			""";

		mockMvc.perform(post("/trends-service/trends/productos/ventas/test")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
			.andExpect(status().isBadRequest());
	}
}
