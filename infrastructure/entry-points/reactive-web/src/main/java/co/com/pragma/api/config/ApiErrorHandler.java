package co.com.pragma.api.config;

import co.com.pragma.shared.DomainException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestControllerAdvice
public class ApiErrorHandler {
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String,String>> handle(DomainException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
    }
}
