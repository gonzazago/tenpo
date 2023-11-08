package gonzalo.tenpo.domain.models;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trace extends BaseEntity {

    private String method;
    private String url;
    private String request;
    private String response;
    private Integer statusCode;
    private String headers;
}
