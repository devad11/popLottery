package devad.springframework.poplottery.web.controller;

import devad.springframework.poplottery.web.model.TicketDto;
import devad.springframework.poplottery.web.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/ticket")
@RestController
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/newticket")
    public ResponseEntity<TicketDto> createTicket(@RequestBody int NoOfLines){
        TicketDto ticket = ticketService.createNewTicket(NoOfLines);
        return new ResponseEntity(ticket, HttpStatus.CREATED);
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
        ResponseEntity<TicketDto> response;

        if (ticketId == 0 || noOfLines == 0)
        {
            // todo add error
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            TicketDto ticket = ticketService.amend(ticketId, noOfLines);
            response = new ResponseEntity<>(ticket, HttpStatus.OK);
        }

        return response;
    }

    @PutMapping("/check/{ticketId}")
    ResponseEntity<TicketDto> checkTicket(@PathVariable("ticketId") int ticketId) {
        ResponseEntity<TicketDto> response;

        if (ticketId == 0)
        {
            // todo add error
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            ticketService.checkTicket(ticketId);
            response = new ResponseEntity<>(this.ticketService.getTicketById(ticketId), HttpStatus.OK);
        }
        return response;
    }

}
