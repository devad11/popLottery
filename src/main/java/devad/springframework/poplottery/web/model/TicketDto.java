package devad.springframework.poplottery.web.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TicketDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp lastModified;

    @JsonManagedReference
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    @Size(min=3)
    @Column(nullable = false)
    @OrderBy("lineResult DESC")
    private List<TicketLineDto> ticketLines;

    @Column(nullable = false)
    private boolean checked = false;
}
