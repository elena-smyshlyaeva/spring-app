package ru.sumbirsoft.chat.dto.user;

import lombok.Data;
import ru.sumbirsoft.chat.domain.Room;

import java.util.Set;

@Data
public class RequestUserDto {
    private String username;
    private String login;
    private String password;
    Set<Room> ownerIn;
    Set<Room> rooms;
}
