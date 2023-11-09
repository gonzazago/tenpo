package gonzalo.tenpo.delivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import gonzalo.tenpo.delivery.controllers.SumController;
import gonzalo.tenpo.delivery.dtos.SumRequest;
import gonzalo.tenpo.delivery.interceptors.RateLimitInterceptor;
import gonzalo.tenpo.domain.actions.CalculateAction;
import gonzalo.tenpo.infrastructure.db.PercentageRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
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

    @AfterEach
    void finish() {
        Mockito.reset(rateLimitInterceptor);
    }


    @Test
    @Order(1)
    @Disabled
    public void testValidRequest() throws Exception {
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
    @Disabled
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
