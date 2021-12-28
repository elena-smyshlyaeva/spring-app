package ru.sumbirsoft.chat.dto.room;

import lombok.Data;
import ru.sumbirsoft.chat.dto.user.RequestUserDto;
import java.util.Set;

@Data
public class RequestRoomDto {
    private String name;
    private boolean isPrivate;
    private RequestUserDto owner;
    private Set<RequestUserDto> members;
}