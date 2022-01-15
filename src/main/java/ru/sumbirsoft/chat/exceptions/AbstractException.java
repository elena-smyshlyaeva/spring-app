package ru.sumbirsoft.chat.exceptions;

import lombok.Data;

@Data
public class AbstractException extends RuntimeException{
    protected String info;
    protected String message;

    public AbstractException(String message, String info) {
        super(message);
        this.info = info;
        this.message = message;
    }

    public AbstractException(String info) {
        super();
        this.info = info;
    }

}
