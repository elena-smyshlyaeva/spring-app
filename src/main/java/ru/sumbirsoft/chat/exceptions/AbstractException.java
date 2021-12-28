package ru.sumbirsoft.chat.exceptions;

import lombok.Data;

@Data
public class AbstractException extends RuntimeException{
    protected String info;
}
