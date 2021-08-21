package devad.springframework.poplottery.web.controller;

import devad.springframework.poplottery.web.model.TicketDto;
import devad.springframework.poplottery.web.service.TicketService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/ticket")
@RestController
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/newticket") // todo create form
    public ResponseEntity createTicket(@RequestBody TicketDto ticketDto){
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable("ticketId") UUID ticketId) {
        return new ResponseEntity<>(ticketService.getTicketById(ticketId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TicketDto>> getTicket() {
        return new ResponseEntity<>(ticketService.listAllTickets(), HttpStatus.OK);
    }
}
