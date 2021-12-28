package ru.sumbirsoft.chat.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends AbstractException {
    public ResourceNotFoundException(String message, String info) {
        super();
        super.info = info;
    }
}