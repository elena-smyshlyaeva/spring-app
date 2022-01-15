package ru.sumbirsoft.chat.exceptions;

public class BanStatusException extends AbstractException{
    public BanStatusException(String message, String info){
        super(message, info);
    }

    public BanStatusException(String info) {
        super(info);
        super.message = "USER HAS BAN STATUS";
    }
}
