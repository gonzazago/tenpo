package gonzalo.tenpo.delivery.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {

    private HttpStatus statusCode;
    private String message;
    private LocalDateTime localDateTime;
}
