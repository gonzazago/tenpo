package gonzalo.tenpo.infrastructure;

import gonzalo.tenpo.domain.models.Percentage;
import gonzalo.tenpo.infrastructure.db.PercentageRepository;
import gonzalo.tenpo.infrastructure.rest.PercentageClient;
import gonzalo.tenpo.infrastructure.rest.dto.PercentageDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PercentageClientTest {

    @Autowired
    private PercentageClient client;
    @MockBean
    private PercentageRepository repository;

    @Mock
    private RestTemplate restTemplate;


    @Test
    public void getPercentageByApiCall() {

        when(restTemplate.getForObject("/percentage", PercentageDTO.class))
                .thenReturn(new PercentageDTO(50.0));
        PercentageDTO percentageDTO = client.getPercentage();
        assertEquals(50, percentageDTO.getPercentage());

    }

}
