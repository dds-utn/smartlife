package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.events;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.events.dto.DatoSensorEvent;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.sensores.CamaraDeSeguridad;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.sensores.DetectorFugaDeGas;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.sensores.SensorDeHumo;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.sensores.SensorDeMovimiento;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.sensores.SensorTensionLuz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SensorDataListener {

    private static final Logger log = LoggerFactory.getLogger(SensorDataListener.class);

    private final SensorDeMovimiento sensorDeMovimiento;
    private final CamaraDeSeguridad camaraDeSeguridad;
    private final SensorDeHumo sensorDeHumo;
    private final SensorTensionLuz sensorTensionLuz;
    private final DetectorFugaDeGas detectorFugaDeGas;

    public SensorDataListener(SensorDeMovimiento sensorDeMovimiento,
                              CamaraDeSeguridad camaraDeSeguridad,
                              SensorDeHumo sensorDeHumo,
                              SensorTensionLuz sensorTensionLuz,
                              DetectorFugaDeGas detectorFugaDeGas) {
        this.sensorDeMovimiento = sensorDeMovimiento;
        this.camaraDeSeguridad = camaraDeSeguridad;
        this.sensorDeHumo = sensorDeHumo;
        this.sensorTensionLuz = sensorTensionLuz;
        this.detectorFugaDeGas = detectorFugaDeGas;
    }

    @RabbitListener(queues = "${smartlife.events.queues.sensor-datos}")
    public void onDatoSensor(DatoSensorEvent event) {
        if (event == null || event.tipo() == null || event.dato() == null) {
            log.warn("DatoSensor recibido sin tipo/dato, se ignora");
            return;
        }
        log.info("Dato recibido del sensor tipo={}", event.tipo());
        switch (event.tipo().toUpperCase()) {
            case "MOVIMIENTO" -> sensorDeMovimiento.recibirDato(event.dato());
            case "CAMARA"     -> camaraDeSeguridad.recibirDato(event.dato());
            case "HUMO"       -> sensorDeHumo.recibirDato(event.dato());
            case "GAS"        -> detectorFugaDeGas.recibirDato(event.dato());
            case "TENSION"    -> sensorTensionLuz.recibirDato(event.dato());
            default           -> log.warn("Tipo de sensor desconocido: {}", event.tipo());
        }
    }
}
