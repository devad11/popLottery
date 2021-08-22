package devad.springframework.poplottery.web.service;

import devad.springframework.poplottery.web.model.TicketDto;

import java.util.List;

public interface TicketService {

    TicketDto getTicketById(int ticketId);
    TicketDto createNewTicket(int noOfLines);
    List<TicketDto> listAllTickets();
    TicketDto amend(int id, int noOfLines);

    void checkTicket(int ticketId);
}
