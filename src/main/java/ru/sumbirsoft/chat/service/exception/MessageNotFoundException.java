package ru.sumbirsoft.chat.service.exception;

public class MessageNotFoundException extends RuntimeException{
    public MessageNotFoundException(long id) {
        super("Message with id=" + id + " not found.");
    }
}
