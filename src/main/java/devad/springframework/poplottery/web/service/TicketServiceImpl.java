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

    public TicketDto getTicketById(int ticketId) {
        Optional<TicketDto> ticket = this.ticketDao.findById(ticketId);
        if (ticket.isPresent())
        {
            return ticket.get();
        }
        else
        {
            return null;
        }
    }

    @Override
    public TicketDto createNewTicket(int noOfLines) {
        TicketDto ticket = TicketDto.builder().build();
        ticket = this.ticketDao.save(ticket);
        ticket.setTicketLines(this.ticketLineScv.createLines(ticket, noOfLines));
        return this.ticketDao.save(ticket);
    }

    @Override
    public List<TicketDto> listAllTickets() {
        return this.ticketDao.findAll();
    }

    @Override
    public TicketDto amend(int id, int noOfLines) {
        Optional<TicketDto> ticketExist = this.ticketDao.findById(id);

        if (ticketExist.isPresent())
        {
            TicketDto ticket = ticketExist.get();
            if (ticket.isChecked())
            {
                return null;
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

    @Override
    public TicketDto checkTicket(int ticketId) {
        Optional<TicketDto> ticketExist = this.ticketDao.findById(ticketId);

        if (ticketExist.isPresent())
        {
            TicketDto ticket = ticketExist.get();
            if (ticket.isChecked())
            {
                // already checked
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
