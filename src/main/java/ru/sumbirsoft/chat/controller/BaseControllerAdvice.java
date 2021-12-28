package ru.sumbirsoft.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.sumbirsoft.chat.exceptions.AbstractException;
import ru.sumbirsoft.chat.exceptions.ResourceNotFoundException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Catch exceptions to process.
 */

@ControllerAdvice
public class BaseControllerAdvice {

    private Object response(HttpStatus status, AbstractException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("message", e.getMessage());
        body.put("techInfo", e.getInfo());
        body.put("status", status.toString());
        body.put("timestamp", DateTimeFormatter.ISO_ZONED_DATE_TIME.format((ZonedDateTime.now())));

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public Object resourceNotFoundException(ResourceNotFoundException e) {
        return response(HttpStatus.NOT_FOUND, e);
    }
}