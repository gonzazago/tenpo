package gonzalo.tenpo.delivery.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class SumRequest {
    @NotNull(message = "First Operand is required")
    @JsonProperty("first")
    private Double first;
    @NotNull(message = "Second Operand is required")
    @JsonProperty("second")
    private Double second;
}
