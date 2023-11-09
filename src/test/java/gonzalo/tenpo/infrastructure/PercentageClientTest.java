package gonzalo.tenpo.infrastructure;

import gonzalo.tenpo.TestConfig;
import gonzalo.tenpo.infrastructure.db.PercentageRepository;
import gonzalo.tenpo.infrastructure.rest.PercentageClient;
import gonzalo.tenpo.infrastructure.rest.dto.PercentageDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
import redis.embedded.RedisServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
public class PercentageClientTest {



    @Autowired
    private PercentageClient client;
    @MockBean
    private PercentageRepository repository;

    @Mock
    private RestTemplate restTemplate;


    private static ClientAndServer mockServer;
    private static RedisServer redisServer;
    private static Jedis jedis;


    @BeforeAll
    public static void startServer() {
        redisServer = RedisServer.builder().port(6379).setting("bind 127.0.0.1").build();
        mockServer = ClientAndServer.startClientAndServer(1080);
        redisServer.start();
        jedis = new Jedis("localhost", redisServer.ports().get(0));

    }

    @AfterAll
    public static void stopServer() {
        if (redisServer != null) {
            redisServer.stop();
        }
        mockServer.stop();
    }

    @Test
    public void getPercentageByApiCall() {

        when(restTemplate.getForObject("/percentage", PercentageDTO.class))
                .thenReturn(new PercentageDTO(50.0));
        PercentageDTO percentageDTO = client.getPercentage();
        assertEquals(50, percentageDTO.getPercentage());

    }

}
