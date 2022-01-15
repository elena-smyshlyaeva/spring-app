package ru.sumbirsoft.chat.dto.room;

import lombok.Data;
import ru.sumbirsoft.chat.domain.Members;
import ru.sumbirsoft.chat.dto.members.ResponseMembersDto;
import ru.sumbirsoft.chat.dto.message.ResponseMessageDto;
import ru.sumbirsoft.chat.dto.user.ResponseUserDto;
import java.util.Set;

@Data
public class ResponseRoomDto {
    private Long roomId;
    private String name;
    private boolean isPrivate;
    private ResponseUserDto owner;
    private Set<ResponseMembersDto> roomUsers;
}