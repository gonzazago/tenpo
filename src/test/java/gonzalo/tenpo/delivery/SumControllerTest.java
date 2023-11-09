package gonzalo.tenpo.delivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import gonzalo.tenpo.TestConfig;
import gonzalo.tenpo.delivery.controllers.SumController;
import gonzalo.tenpo.delivery.dtos.SumRequest;
import gonzalo.tenpo.delivery.interceptors.RateLimitInterceptor;
import gonzalo.tenpo.domain.actions.CalculateAction;
import gonzalo.tenpo.infrastructure.db.PercentageRepository;
import gonzalo.tenpo.infrastructure.rest.dto.PercentageDTO;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import redis.clients.jedis.Jedis;
import redis.embedded.RedisServer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SumControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RateLimitInterceptor rateLimitInterceptor;
    @Mock
    private CalculateAction action;

    @MockBean
    private PercentageRepository repository;
    @Autowired
    SumController controller;

    @Autowired
    private ObjectMapper objectMapper;

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

    @AfterEach
    void finish() {
        Mockito.reset(rateLimitInterceptor);
    }

    @AfterAll
    public static void stopServer() {
        if (redisServer != null) {
            redisServer.stop();
        }
        mockServer.stop();
    }


    @Test
    @Order(1)
//    @Disabled
    public void testValidRequest() throws Exception {
        PercentageDTO dto = PercentageDTO.builder().percentage(50.0).build();
        mockServer.when(HttpRequest.request("/api/data"))
                .respond(
                        HttpResponse.response(objectMapper.writeValueAsString(dto))
                                .withHeader("Content-Type", "application/json")
                );
        SumRequest request = SumRequest.builder().first(12.0).second(12.0).build();
        String requestBody = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/v1/sum")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void testInValidRequest() throws Exception {
        SumRequest request = SumRequest.builder().first(12.0).second(null).build();
        String requestBody = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/v1/sum")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void testRateLimit() throws Exception {

        SumRequest request = SumRequest.builder().first(12.0).second(12.0).build();
        String requestBody = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/v1/sum")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/v1/sum")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isTooManyRequests());
    }

}
