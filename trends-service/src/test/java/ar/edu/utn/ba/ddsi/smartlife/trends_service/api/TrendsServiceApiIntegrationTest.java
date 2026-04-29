package ar.edu.utn.ba.ddsi.smartlife.trends_service.api;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.evento.VentaRegistradaEvento;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.services.TrendProductoService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private TrendProductoService trendProductoService;

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
	}

	@Test
	void alta_producto_y_consulta_tendencia_ok() throws Exception {
		String body = """
			{
			  "comercioId": 1,
			  "nombre": "Yerba Premium",
			  "categoria": "Almacén",
			  "precioBase": 1500.0
			}
			""";

		MvcResult alta = mockMvc.perform(post("/trends-service/trends/productos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.productoId").isNumber())
			.andExpect(jsonPath("$.nivel").value("Normal"))
			.andExpect(jsonPath("$.detalle").isString())
			.andReturn();

		long productoId = extraerProductoId(alta.getResponse().getContentAsString());

		mockMvc.perform(get("/trends-service/trends/productos/{id}", productoId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.nivel").value("Normal"));

		mockMvc.perform(get("/trends-service/trends/productos/{id}/leyenda", productoId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.leyenda").value("Comercio Demo \u2013 Yerba Premium \u2013 Almacén"));
	}

	@Test
	void likes_incrementan_y_endpoint_responde_ok() throws Exception {
		long productoId = crearProducto("Mate", "Hogar", 10.0);

		mockMvc.perform(post("/trends-service/trends/productos/{id}/likes", productoId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.nivel").exists());
	}

	@Test
	void ventas_registradas_por_servicio_actualizan_tendencia() {
		long productoId = crearProductoDirecto();
		trendProductoService.procesarVentaRegistrada(new VentaRegistradaEvento(productoId, 1001));
		var respuesta = trendProductoService.obtenerTendencia(productoId);
		assertEquals("En auge", respuesta.nivel());
	}

	private long crearProducto(String nombre, String categoria, double precioBase) throws Exception {
		String body = """
			{
			  "comercioId": 1,
			  "nombre": "%s",
			  "categoria": "%s",
			  "precioBase": %s
			}
			""".formatted(nombre, categoria, precioBase);

		MvcResult result = mockMvc.perform(post("/trends-service/trends/productos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
			.andExpect(status().isCreated())
			.andReturn();

		return extraerProductoId(result.getResponse().getContentAsString());
	}

	private long crearProductoDirecto() {
		String body = """
			{
			  "comercioId": 1,
			  "nombre": "Bulk",
			  "categoria": "Test",
			  "precioBase": 1.0
			}
			""";
		try {
			MvcResult result = mockMvc.perform(post("/trends-service/trends/productos")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
				.andExpect(status().isCreated())
				.andReturn();
			return extraerProductoId(result.getResponse().getContentAsString());
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	private static long extraerProductoId(String json) {
		Object valor = JsonPath.read(json, "$.productoId");
		if (valor instanceof Number numero) {
			return numero.longValue();
		}
		throw new IllegalStateException("productoId inválido");
	}
}
