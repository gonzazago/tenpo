package gonzalo.tenpo.delivery.dtos;

import gonzalo.tenpo.domain.models.Trace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TraceResponse {
    private Long count;
    private Page<Trace> traces;
}
