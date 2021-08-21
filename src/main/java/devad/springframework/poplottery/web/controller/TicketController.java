package devad.springframework.poplottery.web.controller;

import devad.springframework.poplottery.web.model.TicketDto;
import devad.springframework.poplottery.web.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api/v1/ticket")
@RestController
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/newticket") // todo create form (@RequestBody TicketDto ticketDto)
    public ResponseEntity createTicket(@RequestBody int NoOfLines){
        ticketService.createNewTicket(NoOfLines);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping({"/{ticketId}"})
    public ResponseEntity<TicketDto> getTicket(@PathVariable("ticketId") int ticketId) {

        TicketDto ticket = ticketService.getTicketById(ticketId);

        if(ticket == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<TicketDto>> getTicket() {
        return new ResponseEntity<>(ticketService.listAllTickets(), HttpStatus.OK);
    }

    @PutMapping({"/{ticketId}"})
    ResponseEntity<TicketDto> addNewLines(@PathVariable("ticketId") int ticketId, @RequestBody int noOfLines) {
        if (ticketId == 0 || noOfLines == 0)
        {
            // todo add error
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            ticketService.amend(ticketId, noOfLines);
        }

        return new ResponseEntity<>(ticketService.getTicketById(ticketId), HttpStatus.OK);
    }

}
