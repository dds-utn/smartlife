package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.config;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.events.AmenazaEventPublisher;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.GradoRiesgo;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.Hogar;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones.AbrirRociadoresDeAgua;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones.Accionador;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones.DarAvisoPorPantalla;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones.ElevarTension;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones.LlamarABomberos;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones.LlamarAConfiable;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones.LlamarPolicia;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones.SonarAlarma;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.actuadores.Alarma;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.actuadores.ElevadorDeTension;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.actuadores.Llamador;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.actuadores.Pantalla;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.actuadores.RociadorDeAgua;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.receptores.ReceptorDeIncendio;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.receptores.ReceptorDeTensionLuz;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.receptores.ReceptorFugaDeGas;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.receptores.ReceptorRoboCamaraSeguridad;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.receptores.ReceptorRoboSensorMovimiento;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.sensores.CamaraDeSeguridad;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.sensores.DetectorFugaDeGas;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.sensores.SensorDeHumo;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.sensores.SensorDeMovimiento;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.sensores.SensorTensionLuz;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeguridadConfig {

    private static final double HUMO_VALOR_MAX = 60.0;
    private static final double TENSION_MIN = 200.0;
    private static final double TENSION_MAX = 240.0;

    private final GradoRiesgo gradoRiesgoTolerable;
    private final String numeroPolicia;
    private final String numeroBomberos;
    private final String numeroPersonaConfiable;
    private final boolean tieneRociadores;
    private final boolean tieneElevadorTension;

    private final Alarma alarma = new Alarma();
    private final Llamador llamador = new Llamador();
    private final Pantalla pantalla = new Pantalla();
    private final RociadorDeAgua rociadorDeAgua = new RociadorDeAgua();
    private final ElevadorDeTension elevadorDeTension = new ElevadorDeTension();

    private final AmenazaEventPublisher amenazaEventPublisher;

    public SeguridadConfig(
        AmenazaEventPublisher amenazaEventPublisher,
        @Value("${smartlife.hogar.id}") Long hogarId,
        @Value("${smartlife.hogar.direccion}") String direccion,
        @Value("${smartlife.hogar.nro-telefono}") String nroTelefono,
        @Value("${smartlife.seguridad.grado-riesgo-tolerable}") String gradoRiesgoTolerable,
        @Value("${smartlife.seguridad.numero-policia}") String numeroPolicia,
        @Value("${smartlife.seguridad.numero-bomberos}") String numeroBomberos,
        @Value("${smartlife.seguridad.numero-persona-confiable}") String numeroPersonaConfiable,
        @Value("${smartlife.seguridad.tiene-rociadores}") boolean tieneRociadores,
        @Value("${smartlife.seguridad.tiene-elevador-tension}") boolean tieneElevadorTension
    ) {
        this.amenazaEventPublisher = amenazaEventPublisher;
        this.gradoRiesgoTolerable = GradoRiesgo.valueOf(gradoRiesgoTolerable.trim().toUpperCase());
        this.numeroPolicia = numeroPolicia;
        this.numeroBomberos = numeroBomberos;
        this.numeroPersonaConfiable = numeroPersonaConfiable;
        this.tieneRociadores = tieneRociadores;
        this.tieneElevadorTension = tieneElevadorTension;

        Hogar.getInstance().configurar(hogarId, direccion, nroTelefono);
    }

    private Accionador accionadorRobo() {
        Accionador accionador = new Accionador();
        accionador.agregarComando(new SonarAlarma(alarma));
        accionador.agregarComando(new LlamarPolicia(llamador, numeroPolicia));
        accionador.agregarComando(new LlamarAConfiable(llamador, numeroPersonaConfiable));
        accionador.setAmenazaListener(amenazaEventPublisher);
        return accionador;
    }

    private Accionador accionadorIncendio() {
        Accionador accionador = new Accionador();
        accionador.agregarComando(new LlamarABomberos(llamador, numeroBomberos));
        if (tieneRociadores) {
            accionador.agregarComando(new AbrirRociadoresDeAgua(rociadorDeAgua));
        }
        accionador.setAmenazaListener(amenazaEventPublisher);
        return accionador;
    }

    private Accionador accionadorFugaDeGas() {
        Accionador accionador = new Accionador();
        accionador.agregarComando(new DarAvisoPorPantalla(pantalla, "Fuga de gas detectada en el hogar"));
        accionador.setAmenazaListener(amenazaEventPublisher);
        return accionador;
    }

    private Accionador accionadorTension() {
        Accionador accionador = new Accionador();
        accionador.agregarComando(new DarAvisoPorPantalla(pantalla, "Baja tensión eléctrica detectada"));
        if (tieneElevadorTension) {
            accionador.agregarComando(new ElevarTension(elevadorDeTension));
        }
        accionador.setAmenazaListener(amenazaEventPublisher);
        return accionador;
    }

    @Bean
    public SensorDeMovimiento sensorDeMovimiento() {
        return new SensorDeMovimiento(
            new ReceptorRoboSensorMovimiento(gradoRiesgoTolerable, accionadorRobo()));
    }

    @Bean
    public CamaraDeSeguridad camaraDeSeguridad() {
        return new CamaraDeSeguridad(
            new ReceptorRoboCamaraSeguridad(gradoRiesgoTolerable, accionadorRobo()));
    }

    @Bean
    public SensorDeHumo sensorDeHumo() {
        return new SensorDeHumo(new ReceptorDeIncendio(HUMO_VALOR_MAX, accionadorIncendio()));
    }

    @Bean
    public SensorTensionLuz sensorTensionLuz() {
        return new SensorTensionLuz(
            new ReceptorDeTensionLuz(TENSION_MIN, TENSION_MAX, accionadorTension()));
    }

    @Bean
    public DetectorFugaDeGas detectorFugaDeGas() {
        return new DetectorFugaDeGas(new ReceptorFugaDeGas(accionadorFugaDeGas()));
    }
}
