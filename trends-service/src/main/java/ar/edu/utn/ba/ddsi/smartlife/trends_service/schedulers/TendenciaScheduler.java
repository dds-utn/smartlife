package ar.edu.utn.ba.ddsi.smartlife.trends_service.schedulers;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.services.TrendProductoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TendenciaScheduler {
    private final TrendProductoService trendProductoService;

    public TendenciaScheduler(TrendProductoService trendProductoService) {
        this.trendProductoService = trendProductoService;
    }

    @Scheduled(fixedRate = 300000) // 300000 ms = 5 minutos
    public void evaluarProductosEnTendencia() {
        trendProductoService.evaluarTransicionesDeEstadoPorTiempo();
    }
}
