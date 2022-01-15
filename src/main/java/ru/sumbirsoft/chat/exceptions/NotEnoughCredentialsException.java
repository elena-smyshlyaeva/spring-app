package ru.sumbirsoft.chat.exceptions;

public class NotEnoughCredentialsException extends AbstractException{
    public NotEnoughCredentialsException(String message, String info) {
        super(message, info);
    }

    public NotEnoughCredentialsException(String info) {
        super(info);
        super.message = "YOU HAVEN'T ENOUGH CREDENTIALS";
    }
}
