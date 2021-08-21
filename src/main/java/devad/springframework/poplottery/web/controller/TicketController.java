package devad.springframework.poplottery.web.controller;

import devad.springframework.poplottery.web.model.TicketDto;
import devad.springframework.poplottery.web.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/api/v1/ticket")
@RestController
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable("ticketId") UUID ticketId) {
        return new ResponseEntity<>(ticketService.getTicketById(ticketId), HttpStatus.OK);
    }


}
