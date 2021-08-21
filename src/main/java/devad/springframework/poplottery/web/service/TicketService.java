package devad.springframework.poplottery.web.service;

import devad.springframework.poplottery.web.model.TicketDto;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    TicketDto getTicketById(int ticketId);
    TicketDto createNewTicket(int noOfLines);
    List<TicketDto> listAllTickets();

    TicketDto amend(int id, int noOfLines);
}
