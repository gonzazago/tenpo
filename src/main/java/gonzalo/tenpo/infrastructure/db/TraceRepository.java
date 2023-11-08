package gonzalo.tenpo.infrastructure.db;

import gonzalo.tenpo.domain.models.Trace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TraceRepository extends JpaRepository<Trace, UUID> {

    Page<Trace> findAll(Pageable pageable);
}
