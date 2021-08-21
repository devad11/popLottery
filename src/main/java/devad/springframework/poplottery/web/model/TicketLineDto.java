package devad.springframework.poplottery.web.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TicketLineDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ElementCollection
    @Size(min=3, max=3)
    private List<Integer> lineValues;

    private int lineResult;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name= "ticketId", nullable = false, referencedColumnName = "id")
    private TicketDto ticket;
}
