package gonzalo.tenpo.delivery.exceptions;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RateLimitException extends RuntimeException{
    private String message;
}
