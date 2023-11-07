package gonzalo.tenpo.domain.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@Table(name = "percentage")
public class Percentage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Double percentage;
    private LocalDateTime createAt;
}
