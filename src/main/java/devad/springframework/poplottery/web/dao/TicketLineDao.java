package devad.springframework.poplottery.web.dao;

import devad.springframework.poplottery.web.model.TicketLineDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketLineDao extends JpaRepository<TicketLineDto, Integer> {

}
