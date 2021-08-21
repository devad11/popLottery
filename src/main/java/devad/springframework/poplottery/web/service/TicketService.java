package devad.springframework.poplottery.web.service;

import devad.springframework.poplottery.web.model.TicketDto;

import java.util.List;
import java.util.UUID;

public interface TicketService {

    TicketDto getTicketById(UUID ticketId);
    TicketDto saveNewTicket(TicketDto ticketDto);
    List<TicketDto> listAllTickets();
}
