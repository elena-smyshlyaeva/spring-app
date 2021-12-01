package ru.sumbirsoft.chat.dto.room;

import lombok.Data;
import ru.sumbirsoft.chat.domain.Members;
import ru.sumbirsoft.chat.domain.User;

import java.util.Set;

@Data
public class ResponseRoomDto {
    private long roomId;
    private String name;
    private boolean isPrivate;
    private User owner;
    private Set<Members> members;
}
