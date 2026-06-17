package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.receptores;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.datos.DatoRobo;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.GradoRiesgo;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.TipoDeEvento;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones.Accionador;
import lombok.Getter;

@Getter
public abstract class ReceptorDeRobo {

    protected final GradoRiesgo gradoRiesgoTolerable;
    protected final Accionador accionador;

    protected ReceptorDeRobo(GradoRiesgo gradoRiesgoTolerable, Accionador accionador) {
        this.gradoRiesgoTolerable = gradoRiesgoTolerable;
        this.accionador = accionador;
    }

    public void evaluar(String datos) {
        DatoRobo dato = decodificarLosDatos(datos);
        GradoRiesgo gradoRiesgo = analizarGradoRiesgo(dato);
        if (existeRobo(gradoRiesgo)) {
            this.accionador.sucedeEvento(TipoDeEvento.ROBO, gradoRiesgo);
        }
    }

    protected abstract DatoRobo decodificarLosDatos(String datos);

    protected abstract GradoRiesgo analizarGradoRiesgo(DatoRobo dato);

    protected abstract boolean existeRobo(GradoRiesgo gradoRiesgo);

    protected boolean superaTolerancia(GradoRiesgo gradoRiesgo) {
        return gradoRiesgo.ordinal() > this.gradoRiesgoTolerable.ordinal();
    }

    protected DatoRobo parsearDato(String datos) {
        String[] partes = datos.trim().split("[;,]");
        double presencia = Double.parseDouble(partes[0].trim());
        double confiabilidad = partes.length > 1 ? Double.parseDouble(partes[1].trim()) : 1.0;
        return new DatoRobo(presencia, confiabilidad);
    }
}
