package devad.springframework.poplottery.web.controller;

import devad.springframework.poplottery.web.controller.form.LineNumberForm;
import devad.springframework.poplottery.web.model.TicketDto;
import devad.springframework.poplottery.web.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/ticket")
@RestController
public class TicketController {

    private final TicketService ticketService;

    /**
     * Creates a new ticket with a specific number of lines
     * @param lineNumberForm the required number of lines. Must be at least 1
     * @return if valid, returns the ticket with all lines + http status
     */
    @PostMapping("/newticket")
    public ResponseEntity<TicketDto> createTicket(@Valid @ModelAttribute("aLineNumberForm") LineNumberForm lineNumberForm){
        TicketDto ticket = ticketService.createNewTicket(lineNumberForm.getNoOfLines());
        if(ticket == null)
        {
            return new ResponseEntity("Must add at least 1 line when creating new ticket", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(ticket, HttpStatus.CREATED);
    }

    /**
     * Returns a ticket based on id
     * @param ticketId the id of the required ticket
     * @return if exists, returns the ticket + http status
     */
    @Validated
    @GetMapping({"/{ticketId}"})
    public ResponseEntity<TicketDto> getTicket(@Min(1) @PathVariable("ticketId") int ticketId) {

        TicketDto ticket = ticketService.getTicketById(ticketId);

        if(ticket == null)
        {
            return new ResponseEntity("Ticket doesnt exist!", HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        }
    }

    /**
     * Simple index page
     * @return string of explaining rules
     */
    @GetMapping("/")
    public ResponseEntity<String> getIndexPage() {
        return new ResponseEntity("Lottery game\n" +
                "Each line can win\n" +
                "Sum of values equals 2 \t\t\t get 10\n" +
                "Three of the same \t\t\t\t get 5\n" +
                "The first number never repeats \t get 1", HttpStatus.I_AM_A_TEAPOT);

    }

    /**
     * Returns all the tickets
     * @return list of all tickets if any
     */
    @GetMapping("/all")
    public ResponseEntity<List<TicketDto>> getTicket() {
        if(ticketService.listAllTickets().size() == 0)
        {
            return new ResponseEntity("There are no tickets! :(", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ticketService.listAllTickets(), HttpStatus.OK);
    }

    /**
     * Adds new lines to the existing ones
     * @param ticketId id of the ticket
     * @param lineNumberForm contains the amount of lines to add to the ticket
     * @return the ticket with all lines old + new
     */
    @Validated
    @PutMapping({"/{ticketId}"})
    ResponseEntity<TicketDto> addNewLines(@Min(1) @PathVariable("ticketId") int ticketId, @Valid @ModelAttribute("aLineNumberForm") LineNumberForm lineNumberForm) {
        TicketDto ticket = ticketService.amend(ticketId, lineNumberForm.getNoOfLines());
        if (ticket == null)
        {
            return new ResponseEntity("The ticket id and the number of lines must be valid!", HttpStatus.NOT_FOUND);
        }
        else if (ticket.isChecked()){
            return new ResponseEntity("Ticket is checked already! Cannot amend!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    /**
     * Checks each line on the ticket and calculates the value of it
     * @param ticketId id of the ticket
     * @return the ticket which was checked
     */
    @PutMapping("/check/{ticketId}")
    ResponseEntity<TicketDto> checkTicket(@PathVariable("ticketId") int ticketId) {
        TicketDto ticket = ticketService.checkTicket(ticketId);

        if (ticket == null)
        {
            return new ResponseEntity("The ticket doesn't exist or already checked!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.ticketService.getTicketById(ticketId), HttpStatus.OK);
    }

}
