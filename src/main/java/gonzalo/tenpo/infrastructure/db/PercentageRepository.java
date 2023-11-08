package gonzalo.tenpo.infrastructure.db;

import gonzalo.tenpo.domain.models.Percentage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PercentageRepository extends CrudRepository<Percentage, UUID> {
    
    Optional<Percentage> findFirstByOrderByCreateAtDesc();
}
