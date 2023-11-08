package gonzalo.tenpo.delivery.controllers;

import gonzalo.tenpo.delivery.dtos.SumRequest;
import gonzalo.tenpo.delivery.dtos.SumResponse;
import gonzalo.tenpo.domain.actions.CalculateAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/v1/sum")
public class SumController {


    @Autowired
    private CalculateAction calculateAction;
    @PostMapping
    public ResponseEntity<SumResponse> sum(@RequestBody SumRequest body) throws Exception {
        return ResponseEntity.ok().body(
                SumResponse.builder()
                        .result(calculateAction.calculate(body.getFirst(),body.getSecond()))
                        .status("OK")
                        .build());
    }

}
