package ru.sumbirsoft.chat.dto.user;

import lombok.Data;
import ru.sumbirsoft.chat.domain.UserRoom;
import ru.sumbirsoft.chat.domain.Room;

import java.util.Set;

@Data
public class ResponseUserDto {
    private long userId;
    private String username;
    private Set<Room> ownerIn;
    private Set<UserRoom> memberIn;
}
