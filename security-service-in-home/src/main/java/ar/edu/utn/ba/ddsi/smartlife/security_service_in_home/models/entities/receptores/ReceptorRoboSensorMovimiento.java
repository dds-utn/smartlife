package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.receptores;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.datos.DatoRobo;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.GradoRiesgo;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones.Accionador;

public class ReceptorRoboSensorMovimiento extends ReceptorDeRobo {

    public ReceptorRoboSensorMovimiento(GradoRiesgo gradoRiesgoTolerable, Accionador accionador) {
        super(gradoRiesgoTolerable, accionador);
    }

    @Override
    protected DatoRobo decodificarLosDatos(String datos) {
        // TODO: implementación de ejemplo; el parseo real depende del protocolo del sensor de movimiento
        return parsearDato(datos);
    }

    @Override
    protected GradoRiesgo analizarGradoRiesgo(DatoRobo dato) {
        double nivel = dato.getNivelDePresenciaHumana();
        if (nivel >= 0.7) {
            return GradoRiesgo.ALTO;
        }
        if (nivel >= 0.4) {
            return GradoRiesgo.MEDIO;
        }
        return GradoRiesgo.BAJO;
    }

    @Override
    protected boolean existeRobo(GradoRiesgo gradoRiesgo) {
        return superaTolerancia(gradoRiesgo);
    }
}
