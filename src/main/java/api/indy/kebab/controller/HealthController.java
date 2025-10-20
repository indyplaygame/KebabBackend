package api.indy.kebab.controller;

import api.indy.kebab.model.response.MessageResponse;
import api.indy.kebab.util.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller for handling health check requests.
 * Provides an endpoint to verify server responsiveness.
 */
@RestController
public class HealthController {

    /**
     * Handles requests to ping the server.
     *
     * @return a {@link ResponseEntity} containing "Pong!" and HTTP status 200 OK.
     */
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("Pong!", HttpStatus.OK);
    }

    /**
     * Handles health check requests.
     *
     * @return a {@link ResponseEntity} containing server status and timestamp with HTTP status 200 OK.
     */
    @GetMapping("/health")
    public ResponseEntity<Object> healthCheck() {
        return new ResponseEntity<>(Map.of(
            "status", "OK",
            "timestamp", Util.getTimestamp()
        ), HttpStatus.OK);
    }
}
