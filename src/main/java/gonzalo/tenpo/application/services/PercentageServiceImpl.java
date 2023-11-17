package gonzalo.tenpo.application.services;

import gonzalo.tenpo.domain.models.Percentage;
import gonzalo.tenpo.domain.services.PercentageService;
import gonzalo.tenpo.infrastructure.db.PercentageRepository;
import gonzalo.tenpo.infrastructure.rest.PercentageClient;
import gonzalo.tenpo.infrastructure.rest.dto.PercentageDTO;
import gonzalo.tenpo.infrastructure.rest.exceptions.ApiException;
import gonzalo.tenpo.infrastructure.rest.exceptions.PercentageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class PercentageServiceImpl implements PercentageService {


    private final PercentageClient client;

    private final PercentageRepository percentageRepository;
    @Autowired
    public PercentageServiceImpl(PercentageClient client, PercentageRepository percentageRepository) {
        this.client = client;
        this.percentageRepository = percentageRepository;
    }

    @Override
    public PercentageDTO getPercentage() {
        PercentageDTO percentage;
        Percentage percentageEntity;
        try {
            percentage = client.getPercentage();
            percentageRepository.save(
                    Percentage.builder()
                            .percentage(percentage.getPercentage()).build());
        } catch (Exception ex) {
            log.error("error on get percentage [error]: [{}]", ex.getMessage());
            percentageEntity = percentageRepository.findFirstByOrderByCreateAtDesc().filter(
                    p -> !p.getCreateAt()
                            .isBefore(
                                    LocalDateTime.now().minus(30, ChronoUnit.MINUTES)
                            )
            ).orElseThrow(() -> new PercentageException("no percentage available"));
            percentage = new PercentageDTO(percentageEntity.getPercentage());
        }
        return percentage;
    }
}
