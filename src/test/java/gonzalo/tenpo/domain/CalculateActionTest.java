package gonzalo.tenpo.domain;

import gonzalo.tenpo.delivery.exceptions.InvalidRequestException;
import gonzalo.tenpo.domain.actions.CalculateAction;
import gonzalo.tenpo.domain.services.PercentageService;
import gonzalo.tenpo.infrastructure.db.PercentageRepository;
import gonzalo.tenpo.infrastructure.rest.PercentageClient;
import gonzalo.tenpo.infrastructure.rest.dto.PercentageDTO;
import gonzalo.tenpo.infrastructure.rest.exceptions.PercentageException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CalculateActionTest {

    @MockBean
    private PercentageService percentageService;
    @MockBean
    private PercentageRepository repository;
    @MockBean
    private PercentageClient client;

    @Autowired
    private CalculateAction action;


    @Test
    public void getPercentage_Success() throws Exception {
        when(percentageService.getPercentage()).thenReturn(PercentageDTO.builder().percentage(10.0).build());
        Double expected = 11.0;
        Double res = action.calculate(5.0, 5.0);
        assertEquals(expected, res);
        Mockito.verify(percentageService, Mockito.times(1)).getPercentage();
    }

    @Test
    public void getPercentage_BadOperators() throws Exception {
        when(percentageService.getPercentage()).thenReturn(PercentageDTO.builder().percentage(10.0).build());
        assertThrows(InvalidRequestException.class, () -> {
            action.calculate(5.0, null);
            Mockito.verify(percentageService, Mockito.never()).getPercentage();
        });

    }

    @Test
    public void getPercentage_PercentageServiceException() throws Exception {
        when(percentageService.getPercentage()).thenThrow(new PercentageException("some error"));
        assertThrows(PercentageException.class, () -> {
            action.calculate(5.0, 5.5);
        });

    }
}
