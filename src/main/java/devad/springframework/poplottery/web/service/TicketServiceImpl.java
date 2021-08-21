package devad.springframework.poplottery.web.service;

import devad.springframework.poplottery.web.dao.TicketDao;
import devad.springframework.poplottery.web.model.TicketDto;
import devad.springframework.poplottery.web.model.TicketLineDto;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketDao ticketDao;

    // todo update it to return from db
    @Override
    public TicketDto getTicketById(UUID ticketId) {
        return TicketDto.builder()
                .id(UUID.randomUUID())
                .ticketLines(
                        new ArrayList<>()
                        {{
                            add(TicketLineDto.builder()
                                .id(UUID.randomUUID())
                                .lineValues(new ArrayList<>()
                                {{
                                    add(0);
                                    add(1);
                                    add(2);
                                }}).build());
                        }}).build();
    }

    @Override
    public TicketDto saveNewTicket(TicketDto ticketDto) {
        return ticketDao.save(ticketDto);
    }

    @Override
    public List<TicketDto> listAllTickets() {
        return ticketDao.findAll();
    }
}
