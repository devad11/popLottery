package devad.springframework.poplottery.web.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public class TicketLineDto {
    @Id
    @GeneratedValue(generator ="UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, updatable = false, length = 36, columnDefinition = "varchar")
    private UUID id;

    @Size(min=3, max=3)
    private List<Integer> lineValues;

    private int lineResult;
}
