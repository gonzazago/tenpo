package gonzalo.tenpo.infrastructure.rest.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PercentageException extends RuntimeException{
    private String message;
}
