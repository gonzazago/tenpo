package gonzalo.tenpo.application.services;

import gonzalo.tenpo.domain.models.Trace;
import gonzalo.tenpo.domain.services.TraceService;
import gonzalo.tenpo.infrastructure.db.TraceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TraceServiceImpl implements TraceService {
    @Autowired
    private TraceRepository repository;
    @Override
    public Page<Trace> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        return repository.findAll(pageable);
    }
}
