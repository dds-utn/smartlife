package ar.edu.utn.ba.ddsi.smartlife.sales_service.api;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SalesServiceApiIntegrationTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void altaProductoYConsultaPrecioFinal_ok() throws Exception {
		String requestBody = """
			{
			  "comercioId": 1,
			  "tipoProductoId": 2,
			  "precioBase": 100.0,
			  "descripcion": "Smart TV 50"
			}
			""";

		MvcResult altaProducto = mockMvc.perform(post("/sales-service/productos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").isNumber())
			.andExpect(jsonPath("$.comercioId").value(1))
			.andExpect(jsonPath("$.tipoProductoId").value(2))
			.andExpect(jsonPath("$.tipoProductoDescripcion").value("Electrónico"))
			.andReturn();

		String productoBody = altaProducto.getResponse().getContentAsString();
		Number productoIdNumero = JsonPath.read(productoBody, "$.id");
		long productoId = productoIdNumero.longValue();

		mockMvc.perform(get("/sales-service/productos/{id}/precio", productoId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.productoId").value(productoId))
			.andExpect(jsonPath("$.precioBase").value(100.0))
			.andExpect(jsonPath("$.impuestos").value(87.0))
			.andExpect(jsonPath("$.precioFinal").value(187.0));
	}

	@Test
	void altaVentaConDosItems_ok() throws Exception {
		long producto1 = crearProducto("Cafetera Express", 1, 200.0);
		long producto2 = crearProducto("Licuadora", 1, 100.0);

		String requestBody = """
			{
			  "comercioId": 1,
			  "items": [
			    {"productoId": %d, "cantidad": 1},
			    {"productoId": %d, "cantidad": 2}
			  ]
			}
			""".formatted(producto1, producto2);

		MvcResult ventaResult = mockMvc.perform(post("/sales-service/ventas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").isNumber())
			.andExpect(jsonPath("$.comercioId").value(1))
			.andReturn();

		String ventaBody = ventaResult.getResponse().getContentAsString();
		double totalPrecioBase = JsonPath.read(ventaBody, "$.totalPrecioBase");
		double totalImpuestos = JsonPath.read(ventaBody, "$.totalImpuestos");
		double totalFinal = JsonPath.read(ventaBody, "$.totalFinal");

		assertEquals(400.0, totalPrecioBase, 0.01);
		assertEquals(187.15, totalImpuestos, 0.01);
		assertEquals(587.15, totalFinal, 0.01);
	}

	@Test
	void altaVentaSinItems_badRequest() throws Exception {
		String requestBody = """
			{
			  "comercioId": 1,
			  "items": []
			}
			""";

		mockMvc.perform(post("/sales-service/ventas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.error").value("bad_request"));
	}

	private long crearProducto(String descripcion, long tipoProductoId, double precioBase) throws Exception {
		String requestBody = """
			{
			  "comercioId": 1,
			  "tipoProductoId": %d,
			  "precioBase": %s,
			  "descripcion": "%s"
			}
			""".formatted(tipoProductoId, precioBase, descripcion);

		MvcResult result = mockMvc.perform(post("/sales-service/productos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isCreated())
			.andReturn();

		String productoBody = result.getResponse().getContentAsString();
		Number productoId = JsonPath.read(productoBody, "$.id");
		return productoId.longValue();
	}
}
