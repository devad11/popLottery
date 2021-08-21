package devad.springframework.poplottery.web.service;

import devad.springframework.poplottery.web.model.TicketDto;

import java.util.UUID;

public interface TicketService {

    public TicketDto getTicketById(UUID ticketId);
}
