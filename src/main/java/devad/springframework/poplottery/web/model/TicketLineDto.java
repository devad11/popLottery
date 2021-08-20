package devad.springframework.poplottery.web.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TicketLineDto {
    @Id
    @GeneratedValue(generator ="UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, updatable = false, length = 36, columnDefinition = "varchar")
    private UUID id;

    @ElementCollection
    @Size(min=3, max=3)
    private List<Integer> lineValues;

    private int lineResult;

    @ManyToOne
    @JoinColumn(name= "ticketId", nullable = false, referencedColumnName = "id")
    private TicketDto ticket;
}
