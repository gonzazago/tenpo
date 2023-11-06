package gonzalo.tenpo.delivery.controllers;

import gonzalo.tenpo.delivery.dtos.ErrorResponse;
import gonzalo.tenpo.delivery.exceptions.InvalidRequestException;
import gonzalo.tenpo.delivery.exceptions.RateLimitException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestControllerAdviceHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(InvalidRequestException ex) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_GATEWAY)
                .message(ex.getMessage())
                .localDateTime(LocalDateTime.now())
                .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleDefaultException(Exception ex) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .localDateTime(LocalDateTime.now())
                .build()
        );
    }

    @ExceptionHandler(RateLimitException.class)
    public ResponseEntity<ErrorResponse> rateLimitHandler(Exception ex) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .statusCode(HttpStatus.TOO_MANY_REQUESTS)
                .message(ex.getMessage())
                .localDateTime(LocalDateTime.now())
                .build()
        );
    }
}
