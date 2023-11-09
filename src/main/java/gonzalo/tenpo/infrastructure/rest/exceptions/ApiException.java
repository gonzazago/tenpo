package gonzalo.tenpo.infrastructure.rest.exceptions;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;


@RequiredArgsConstructor
public class ApiException extends RuntimeException{

    private final String clientName;
    private final String title;
    private final String error;
    private final HttpStatusCode status;
    private final LocalDateTime dateTime;
    private final String message;
}
