package ar.edu.utn.ba.ddsi.smartlife.security_service.controllers;

import ar.edu.utn.ba.ddsi.smartlife.security_service.dtos.evento.EventoResponse;
import ar.edu.utn.ba.ddsi.smartlife.security_service.services.EventoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security-service/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/{id}")
    public EventoResponse getById(@PathVariable Long id) {
        return eventoService.findById(id);
    }
}
