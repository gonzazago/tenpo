package gonzalo.tenpo.domain.services;

import gonzalo.tenpo.domain.models.Trace;
import org.springframework.data.domain.Page;

public interface TraceService {

    Page<Trace> findAll(Integer page, Integer size);
}
