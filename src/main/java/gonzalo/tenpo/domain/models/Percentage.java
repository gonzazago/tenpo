package gonzalo.tenpo.domain.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
@Table(name = "percentage")
public class Percentage extends BaseEntity {


    private Double percentage;
    ;
}
