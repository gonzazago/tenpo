package gonzalo.tenpo.delivery.controllers;

import gonzalo.tenpo.delivery.dtos.ErrorResponse;
import gonzalo.tenpo.delivery.dtos.SumRequest;
import gonzalo.tenpo.delivery.dtos.SumResponse;
import gonzalo.tenpo.delivery.dtos.TraceResponse;
import gonzalo.tenpo.domain.actions.CalculateAction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static gonzalo.tenpo.application.commons.Constants.BASE_PATH;

@RestController
@Slf4j
@RequestMapping(BASE_PATH)
@Tag(name = "Sum", description = "This endpoint is responsible the add two numbers")
public class SumController {

    private final CalculateAction calculateAction;
    @Autowired
    public SumController(CalculateAction calculateAction) {
        this.calculateAction = calculateAction;
    }

    @PostMapping("/sum")
    @Operation(summary = "add two numbers", tags = {"sum", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = SumResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "428", content = {
                    @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")})
    })
    public ResponseEntity<SumResponse> sum(@RequestBody SumRequest body) throws Exception {
        return ResponseEntity.ok().body(
                SumResponse.builder()
                        .result(calculateAction.calculate(body.getFirst(), body.getSecond()))
                        .status("OK")
                        .build());
    }

}
