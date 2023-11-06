package gonzalo.tenpo.domain.actions;

import gonzalo.tenpo.delivery.exceptions.InvalidRequestException;
import gonzalo.tenpo.infrastructure.rest.PercentageClient;
import gonzalo.tenpo.infrastructure.rest.dto.PercentageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CalculateAction {

    @Autowired
    private PercentageClient client;
    public Double calculate(Double firstOperator, Double secondOperator) throws Exception {

        if(firstOperator == null || secondOperator == null){
            throw new InvalidRequestException("all operator are required");
        }
        PercentageDTO percentageDTO = client.getPercentage();
        log.info("percentage: {}",percentageDTO);

        return firstOperator+secondOperator;
    }
}
