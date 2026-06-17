package ar.edu.utn.ba.ddsi.smartlife.security_service.api;

import ar.edu.utn.ba.ddsi.smartlife.security_service.events.AmenazaDetectadaListener;
import ar.edu.utn.ba.ddsi.smartlife.security_service.events.dto.AmenazaDetectadaEvent;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Cubre los casos de prueba obligatorios de la nube (§6.9): el hogar entra en
 * peligro y oculta su información; el usuario lo marca seguro y vuelve a mostrarse.
 */
@SpringBootTest
class SecurityServiceApiIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private AmenazaDetectadaListener amenazaDetectadaListener;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void flujoAmenazaEnPeligroYRecuperacion() throws Exception {
        // El hogar 1 arranca seguro y muestra su información.
        mockMvc.perform(get("/security-service/hogares/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estado").value("SEGURO"));

        // Llega una amenaza de robo con riesgo alto (consumida del broker).
        amenazaDetectadaListener.onAmenazaDetectada(new AmenazaDetectadaEvent(
            1L, "ROBO", "ALTO", LocalDateTime.now(),
            List.of("Alarma del hogar activada", "Llamada a la policía (911)")));

        // El evento queda registrado y es consultable.
        MvcResult eventosResult = mockMvc.perform(get("/security-service/hogares/1/eventos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].tipo").value("ROBO"))
            .andExpect(jsonPath("$[0].gradoRiesgo").value("ALTO"))
            .andReturn();

        Number eventoId = JsonPath.read(eventosResult.getResponse().getContentAsString(), "$[0].id");
        mockMvc.perform(get("/security-service/eventos/{id}", eventoId.longValue()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tipo").value("ROBO"));

        // El hogar quedó EN_PELIGRO: su información no se muestra (403).
        mockMvc.perform(get("/security-service/hogares/1"))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.error").value("hogar_en_peligro"));

        // El usuario lo marca manualmente como SEGURO.
        mockMvc.perform(put("/security-service/hogares/1/seguridad")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"estado\":\"SEGURO\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estado").value("SEGURO"));

        // Vuelve a mostrarse la información.
        mockMvc.perform(get("/security-service/hogares/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.estado").value("SEGURO"));
    }
}
