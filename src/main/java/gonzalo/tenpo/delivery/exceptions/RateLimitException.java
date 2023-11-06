package gonzalo.tenpo.delivery.exceptions;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class RateLimitException extends RuntimeException{
    private String message;
}
