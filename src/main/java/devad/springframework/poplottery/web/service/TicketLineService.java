package devad.springframework.poplottery.web.service;

import devad.springframework.poplottery.web.model.TicketDto;
import devad.springframework.poplottery.web.model.TicketLineDto;

import java.util.List;

public interface TicketLineService {

    List<TicketLineDto> createLines(TicketDto ticketDto, int noOfLines);

    List<TicketLineDto> checkLines(List<TicketLineDto> ticket);
}
