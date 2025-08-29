package co.com.pragma.api.error;

import co.com.pragma.shared.DomainException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private ApiError body(HttpStatus status, String code, String message, ServerWebExchange ex) {
        return new ApiError(
                Instant.now(),
                status.value(),
                code,
                message,
                ex.getRequest().getPath().value()
        );
    }

    @ExceptionHandler(DomainException.class)
    public Mono<ResponseEntity<ApiError>> handleDomain(DomainException ex, ServerWebExchange exchange) {
        var b = body(HttpStatus.CONFLICT, "domain_conflict", ex.getMessage(), exchange);
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(b));
    }

    @ExceptionHandler({DuplicateKeyException.class, DataIntegrityViolationException.class})
    public Mono<ResponseEntity<ApiError>> handleDuplicate(RuntimeException ex, ServerWebExchange exchange) {
        var b = body(HttpStatus.CONFLICT, "duplicate_key", "Ya existe un registro con datos únicos.", exchange);
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(b));
    }

    @ExceptionHandler({WebExchangeBindException.class, ServerWebInputException.class})
    public Mono<ResponseEntity<ApiError>> handleBadRequest(Exception ex, ServerWebExchange exchange) {
        var b = body(HttpStatus.BAD_REQUEST, "bad_request", ex.getMessage(), exchange);
        return Mono.just(ResponseEntity.badRequest().body(b));
    }

    @ExceptionHandler(Throwable.class)
    public Mono<ResponseEntity<ApiError>> handleAny(Throwable ex, ServerWebExchange exchange) {
        var b = body(HttpStatus.INTERNAL_SERVER_ERROR, "internal_error", "Error interno del servidor.", exchange);
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(b));
    }
}