package devad.springframework.poplottery.web.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class TicketDto {

    @Id
    @GeneratedValue(generator ="UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, updatable = false, length = 36, columnDefinition = "varchar")
    private UUID id;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp lastModified;

    private int noOfLines;
    private List<Integer> ticketLines;
    private int ticketResult;
}
