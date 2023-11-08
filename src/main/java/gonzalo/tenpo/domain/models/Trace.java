package gonzalo.tenpo.domain.models;

import jakarta.persistence.Column;
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
    @Column(name = "request", length = 2000)
    private String request;
    @Column(name = "response", length = 2000)
    private String response;
    private Integer statusCode;
    @Column(name = "headers", length = 2000)
    private String headers;
}
