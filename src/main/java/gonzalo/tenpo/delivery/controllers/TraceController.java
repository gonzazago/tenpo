package gonzalo.tenpo.delivery.controllers;

import gonzalo.tenpo.delivery.dtos.ErrorResponse;
import gonzalo.tenpo.delivery.dtos.SumRequest;
import gonzalo.tenpo.delivery.dtos.SumResponse;
import gonzalo.tenpo.delivery.dtos.TraceResponse;
import gonzalo.tenpo.domain.actions.CalculateAction;
import gonzalo.tenpo.domain.actions.TraceAction;
import gonzalo.tenpo.domain.models.Trace;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static gonzalo.tenpo.application.commons.Constants.BASE_PATH;

@Slf4j
@Tag(name = "Traces", description = "List all traces with info of request")
@RestController
@RequestMapping(BASE_PATH)
public class TraceController {

    private final TraceAction traceAction;

    @Autowired
    public TraceController(TraceAction traceAction) {
        this.traceAction = traceAction;
    }

    @GetMapping("traces")
    @Operation(summary = "Retrieve Traces paginate", tags = {"traces", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = TraceResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")})
    })
    public ResponseEntity<TraceResponse> sum(@RequestParam(name = "page", defaultValue = "0") String page,
                                             @RequestParam(name = "size", defaultValue = "10") String size) {
        Page<Trace> traces = traceAction.findAll(Integer.valueOf(page), Integer.valueOf(size));
        TraceResponse response = TraceResponse.builder()
                .count(traces.getTotalElements())
                .traces(traces.stream().toList())
                .build();
        return ResponseEntity.ok().body(response);
    }

}
