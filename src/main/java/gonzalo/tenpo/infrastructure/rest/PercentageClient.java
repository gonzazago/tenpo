package gonzalo.tenpo.infrastructure.rest;

import gonzalo.tenpo.infrastructure.rest.dto.PercentageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class PercentageClient extends  RestClient<PercentageDTO>{

    @Cacheable(cacheManager = "percentageCache", cacheNames = "percentageCache")
    public PercentageDTO getPercentage() {
        log.info("Get percentage");
        String baseUrl = "http://localhost:8081/percentage";
        try{
            return getWitRetry(baseUrl, PercentageDTO.class);
        }catch (HttpStatusCodeException e){
            throw handleException("percentageClient",e.getStatusCode(),e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
