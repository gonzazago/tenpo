package gonzalo.tenpo.delivery.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class InvalidRequestException  extends  RuntimeException{
    private String message;
}
