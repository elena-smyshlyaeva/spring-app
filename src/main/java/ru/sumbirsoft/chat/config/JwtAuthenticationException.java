package ru.sumbirsoft.chat.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

/**
 * Class for processing exceptions with Json Web Tokens
 */

public class JwtAuthenticationException extends AuthenticationException {

    private HttpStatus httpStatus;

    public JwtAuthenticationException(String msg) {
        super(msg);
    }

    public JwtAuthenticationException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}