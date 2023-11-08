package gonzalo.tenpo.delivery.controllers;

import gonzalo.tenpo.delivery.dtos.SumRequest;
import gonzalo.tenpo.delivery.dtos.SumResponse;
import gonzalo.tenpo.delivery.dtos.TraceResponse;
import gonzalo.tenpo.domain.actions.CalculateAction;
import gonzalo.tenpo.domain.actions.TraceAction;
import gonzalo.tenpo.domain.models.Trace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static gonzalo.tenpo.application.commons.Constants.BASE_PATH;

@RestController
@Slf4j
@RequestMapping(BASE_PATH)
public class TraceController {


    @Autowired
    private TraceAction traceAction;

    @GetMapping("traces")
    public ResponseEntity<Page<Trace>> sum(@RequestParam(name = "page", defaultValue = "0") String page,
                                           @RequestParam(name = "size", defaultValue = "10") String size) {
        Page<Trace> traces = traceAction.findAll(Integer.valueOf(page), Integer.valueOf(size));
        return ResponseEntity.ok().body(traces);
    }

}
