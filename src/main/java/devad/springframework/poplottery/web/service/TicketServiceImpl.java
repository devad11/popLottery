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
        Optional<TicketDto> ticket = ticketDao.findById(ticketId);
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
        ticket = ticketDao.save(ticket);
        ticket.setTicketLines(ticketLineScv.createLines(ticket, noOfLines));
        return ticketDao.save(ticket);
    }

    @Override
    public List<TicketDto> listAllTickets() {
        return ticketDao.findAll();
    }

    @Override
    public void amend(int id, int noOfLines) {
        Optional<TicketDto> ticketExist = ticketDao.findById(id);

        if (ticketExist.isPresent())
        {
            TicketDto ticket = ticketDao.findById(id).get();
            List<TicketLineDto> lines = ticket.getTicketLines();
            List<TicketLineDto> newLines = ticketLineScv.createLines(ticket, noOfLines);
            List<TicketLineDto> allLines = Stream.concat(lines.stream(), newLines.stream()).collect(Collectors.toList());
            ticket.setTicketLines(allLines);
            ticketDao.save(ticket);
        }

    }
}
