package gonzalo.tenpo.delivery.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SumRequest {
    @NotNull(message = "First Operand is required")
    private Double first;
    @NotNull(message = "Second Operand is required")
    private Double second;
}
