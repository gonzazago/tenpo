package gonzalo.tenpo.delivery.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class SumRequest {
    @NotNull(message = "First Operand is required")
    private Double first;
    @NotNull(message = "Second Operand is required")
    private Double second;
}
