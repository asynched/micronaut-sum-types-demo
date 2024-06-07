package tech.asynched.controllers;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.serde.annotation.Serdeable;

@Controller("/status")
public class StatusController {
    @Get
    public StatusResponse getStatus() {
        return StatusResponse.fromDefaults();
    }

    @Serdeable
    public record StatusResponse(String status) {
        public static StatusResponse fromDefaults() {
            return new StatusResponse("ok");
        }
    }
}
