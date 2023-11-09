package gonzalo.tenpo.delivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import gonzalo.tenpo.TestConfig;
import gonzalo.tenpo.delivery.controllers.SumController;
import gonzalo.tenpo.delivery.controllers.TraceController;
import gonzalo.tenpo.delivery.dtos.SumRequest;
import gonzalo.tenpo.delivery.interceptors.RateLimitInterceptor;
import gonzalo.tenpo.domain.actions.CalculateAction;
import gonzalo.tenpo.domain.models.Trace;
import gonzalo.tenpo.infrastructure.db.PercentageRepository;
import gonzalo.tenpo.infrastructure.db.TraceRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
public class TraceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RateLimitInterceptor rateLimitInterceptor;
    @Autowired
    private TraceRepository repository;


    @AfterEach
    void finish() {
        Mockito.reset(rateLimitInterceptor);
    }


    @Test
    public void testValidRequest() throws Exception {
        mockMvc.perform(get("/api/v1/traces")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        System.out.println("Waiting write trace in DB");
        Thread.sleep(1000);
         List<Trace> traces = repository.findAll();
        assertEquals(traces.size(),1);
    }


}
