package gonzalo.tenpo.domain.actions;

import gonzalo.tenpo.domain.models.Trace;
import gonzalo.tenpo.domain.services.TraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class TraceAction {

    @Autowired
    private TraceService service;

    public Page<Trace> findAll(Integer page, Integer size) {
        return service.findAll(page, size);
    }
}
