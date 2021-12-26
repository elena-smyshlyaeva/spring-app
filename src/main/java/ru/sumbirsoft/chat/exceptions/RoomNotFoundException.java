package ru.sumbirsoft.chat.exceptions;

public class RoomNotFoundException extends RuntimeException{
    public RoomNotFoundException(long id) {
        super("Room with id=" + id + " not found.");
    }
}
