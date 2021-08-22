package devad.springframework.poplottery.web.service;

import devad.springframework.poplottery.web.dao.TicketDao;
import devad.springframework.poplottery.web.model.TicketDto;
import devad.springframework.poplottery.web.model.TicketLineDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketDao ticketDao;
    @Autowired
    TicketLineService ticketLineScv;

    /**
     * Find a ticket with a specific id
     * @param ticketId id of a ticket
     * @return gets the specific ticket ordered by results
     */
    @Override
    public TicketDto getTicketById(int ticketId) {
        return this.ticketDao.findById(ticketId).orElse(null);
    }

    /**
     * Generates a new ticket with specific number of lines
     * @param noOfLines the number of lines to create for the ticket
     * @return saves and returns the generated ticket
     */
    @Override
    public TicketDto createNewTicket(int noOfLines) {
        if (noOfLines <= 0)
        {
            return null;
        }

        TicketDto ticket = TicketDto.builder().build();
        ticket = this.ticketDao.save(ticket);
        ticket.setTicketLines(this.ticketLineScv.createLines(ticket, noOfLines));
        return this.ticketDao.save(ticket);
    }

    /**
     * Fetches all tickets from the db
     * @return List of tickets
     */
    @Override
    public List<TicketDto> listAllTickets() {
        return this.ticketDao.findAll();
    }

    /**
     * Adds new lines to a ticket which not have been checked yet
     * @param id the id of the ticket
     * @param noOfLines indicates how many lines to add to ticket
     * @return the total number of lines (old + new)
     */
    @Override
    public TicketDto amend(int id, int noOfLines) {
        TicketDto ticket = this.ticketDao.findById(id).orElse(null);

        if (noOfLines > 0 && ticket != null)
        {
            if (ticket.isChecked())
            {
                return ticket;
            }
            else
            {
                List<TicketLineDto> lines = ticket.getTicketLines();
                List<TicketLineDto> newLines = this.ticketLineScv.createLines(ticket, noOfLines);
                List<TicketLineDto> allLines = Stream.concat(lines.stream(), newLines.stream()).collect(Collectors.toList());
                ticket.setTicketLines(allLines);
                return this.ticketDao.save(ticket);
            }
        }
        return null;
    }

    /**
     * Initiate to check the results of lines / change checked value
     * @param ticketId the id of the ticket to check
     */
    @Override
    public TicketDto checkTicket(int ticketId) {
        TicketDto ticket = this.ticketDao.findById(ticketId).orElse(null);

        if (ticket != null)
        {
            if (ticket.isChecked())
            {
                return null;
            }
            else
            {
                List<TicketLineDto> lines = ticket.getTicketLines();
                ticket.setTicketLines(this.ticketLineScv.checkLines(lines));
                ticket.setChecked(true);
                return this.ticketDao.save(ticket);
            }
        }
        return null;
    }
}
