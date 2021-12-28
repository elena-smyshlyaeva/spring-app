package ru.sumbirsoft.chat.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    private String info;

    public ResourceNotFoundException(String message, String info) {
        super();
        this.info = info;
    }
}