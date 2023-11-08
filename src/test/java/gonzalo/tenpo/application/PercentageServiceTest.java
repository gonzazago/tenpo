package gonzalo.tenpo.application;

import gonzalo.tenpo.domain.models.Percentage;
import gonzalo.tenpo.domain.services.PercentageService;
import gonzalo.tenpo.infrastructure.db.PercentageRepository;
import gonzalo.tenpo.infrastructure.db.TraceRepository;
import gonzalo.tenpo.infrastructure.rest.PercentageClient;
import gonzalo.tenpo.infrastructure.rest.dto.PercentageDTO;
import gonzalo.tenpo.infrastructure.rest.exceptions.PercentageException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PercentageServiceTest {

    @MockBean
    private PercentageClient client;
    @MockBean
    private PercentageRepository repository;
    @MockBean
    TraceRepository traceRepository;
    @Autowired
    private PercentageService service;

    @Test
    public void getPercentageFromApiCall() {
        when(client.getPercentage()).thenReturn(new PercentageDTO(50.0));
        PercentageDTO percentageDTO = service.getPercentage();
        assertEquals(50, percentageDTO.getPercentage());
        Mockito.verify(repository, Mockito.never()).findFirstByOrderByCreateAtDesc();
        Mockito.verify(repository, Mockito.times(1)).save(any());
        Mockito.verify(client, Mockito.times(1)).getPercentage();
    }

    @Test
    public void getPercentageFromCache() {
        Percentage percentage = new Percentage(50.0);
        percentage.setCreateAt(LocalDateTime.now().minusMinutes(10));
        when(client.getPercentage()).thenThrow(new RuntimeException());

        when(repository.findFirstByOrderByCreateAtDesc())
                .thenReturn(Optional.of(percentage));
        PercentageDTO percentageDTO = service.getPercentage();
        assertEquals(50, percentageDTO.getPercentage());

        Mockito.verify(repository, Mockito.times(1)).findFirstByOrderByCreateAtDesc();
        Mockito.verify(repository, Mockito.never()).save(any());
        Mockito.verify(client, Mockito.times(1)).getPercentage();
    }


    @Test
    public void getPercentage_ThrowException() {
        Percentage percentage = new Percentage(50.0);
        percentage.setCreateAt(LocalDateTime.now().minusMinutes(140));
        when(client.getPercentage()).thenThrow(new RuntimeException());

        when(repository.findFirstByOrderByCreateAtDesc())
                .thenReturn(Optional.of(percentage));
        assertThrows(PercentageException.class,()->{
            PercentageDTO percentageDTO = service.getPercentage();
            assertEquals(50, percentageDTO.getPercentage());

            Mockito.verify(repository, Mockito.times(1)).findFirstByOrderByCreateAtDesc();
            Mockito.verify(repository, Mockito.never()).save(any());
            Mockito.verify(client, Mockito.times(1)).getPercentage();
        });

    }
}
