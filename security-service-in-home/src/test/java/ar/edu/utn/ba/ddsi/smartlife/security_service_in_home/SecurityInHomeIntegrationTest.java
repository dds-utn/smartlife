package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.events.SensorDataListener;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.events.dto.DatoSensorEvent;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.GradoRiesgo;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.Hogar;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.TipoDeEvento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityInHomeIntegrationTest {

    @Autowired
    private SensorDataListener sensorDataListener;

    @BeforeEach
    void setUp() {
        Hogar.getInstance().limpiarEventos();
    }

    @Test
    void roboConRiesgoAlto_activaAlarmaYLlamadas() {
        sensorDataListener.onDatoSensor(new DatoSensorEvent("MOVIMIENTO", "0.9;0.95"));

        var evento = Hogar.getInstance().ultimoEvento();
        assertNotNull(evento);
        assertEquals(TipoDeEvento.ROBO, evento.getTipo());
        assertEquals(GradoRiesgo.ALTO, evento.getGradoRiesgo());
        assertTrue(evento.getAccionesEjecutadas().contains("Alarma del hogar activada"));
        assertTrue(evento.getAccionesEjecutadas().contains("Llamada a la policía (911)"));
    }

    @Test
    void roboConRiesgoBajo_noGeneraEvento() {
        sensorDataListener.onDatoSensor(new DatoSensorEvent("MOVIMIENTO", "0.2;0.5"));

        assertNull(Hogar.getInstance().ultimoEvento());
    }

    @Test
    void incendio_llamaBomberosYActivaRociadores() {
        sensorDataListener.onDatoSensor(new DatoSensorEvent("HUMO", "85"));

        var evento = Hogar.getInstance().ultimoEvento();
        assertNotNull(evento);
        assertEquals(TipoDeEvento.INCENDIO, evento.getTipo());
        assertTrue(evento.getAccionesEjecutadas()
            .contains("Llamada a los bomberos (100) con mensaje de voz IA"));
        assertTrue(evento.getAccionesEjecutadas().contains("Rociadores de agua activados"));
    }

    @Test
    void fugaDeGas_muestraAvisoEnPantalla() {
        sensorDataListener.onDatoSensor(new DatoSensorEvent("GAS", "true"));

        var evento = Hogar.getInstance().ultimoEvento();
        assertNotNull(evento);
        assertEquals(TipoDeEvento.FUGA_DE_GAS, evento.getTipo());
        assertTrue(evento.getAccionesEjecutadas()
            .contains("Aviso mostrado en pantalla central: Fuga de gas detectada en el hogar"));
    }

    @Test
    void bajaTension_activaElevadorDeTension() {
        sensorDataListener.onDatoSensor(new DatoSensorEvent("TENSION", "180"));

        var evento = Hogar.getInstance().ultimoEvento();
        assertNotNull(evento);
        assertEquals(TipoDeEvento.TENSION_BAJA, evento.getTipo());
        assertTrue(evento.getAccionesEjecutadas().contains("Elevador de tensión activado"));
    }
}
