package devad.springframework.poplottery.web.service;

import devad.springframework.poplottery.web.model.TicketDto;

import java.util.List;

public interface TicketService {

    TicketDto getTicketById(long ticketId);
    TicketDto createNewTicket(int noOfLines);
    void saveNewTicket(TicketDto ticket);
    List<TicketDto> listAllTickets();
}
