package ru.sumbirsoft.chat.dto.message;

import lombok.Data;
import ru.sumbirsoft.chat.dto.room.RequestRoomDto;
import ru.sumbirsoft.chat.dto.user.RequestUserDto;
import java.sql.Date;

@Data
public class RequestMessageDto {
    private RequestUserDto user;
    private RequestRoomDto room;
    private String text;
    private Date date;
}