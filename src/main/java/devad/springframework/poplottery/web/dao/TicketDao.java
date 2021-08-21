package devad.springframework.poplottery.web.dao;

import devad.springframework.poplottery.web.model.TicketDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketDao extends JpaRepository<TicketDto, Integer> {
}
