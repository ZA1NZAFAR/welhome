package fr.efrei.backend.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    protected ResponseEntity<String> handleHttpClientErrorUnauthorized(HttpClientErrorException.Unauthorized exception) {
        return new ResponseEntity<>("User unauthenticated: invalid token", HttpStatus.UNAUTHORIZED);
    }
}
