package ar.edu.utn.ba.ddsi.smartlife.security_service.controllers;

import ar.edu.utn.ba.ddsi.smartlife.security_service.dtos.evento.EventoResponse;
import ar.edu.utn.ba.ddsi.smartlife.security_service.dtos.hogar.HogarResponse;
import ar.edu.utn.ba.ddsi.smartlife.security_service.dtos.hogar.SeguridadUpdateRequest;
import ar.edu.utn.ba.ddsi.smartlife.security_service.services.HogarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/security-service/hogares")
public class HogarController {

    private final HogarService hogarService;

    public HogarController(HogarService hogarService) {
        this.hogarService = hogarService;
    }

    @GetMapping("/{id}")
    public HogarResponse getById(@PathVariable Long id) {
        return hogarService.findById(id);
    }

    @GetMapping("/{id}/eventos")
    public List<EventoResponse> getEventos(@PathVariable Long id) {
        return hogarService.findEventos(id);
    }

    @PutMapping("/{id}/seguridad")
    public HogarResponse actualizarSeguridad(@PathVariable Long id,
                                             @RequestBody SeguridadUpdateRequest request) {
        return hogarService.actualizarSeguridad(id, request);
    }
}
