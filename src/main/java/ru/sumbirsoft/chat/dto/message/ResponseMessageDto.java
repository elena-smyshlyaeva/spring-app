package ru.sumbirsoft.chat.dto.message;

import lombok.Data;
import ru.sumbirsoft.chat.dto.room.ResponseRoomDto;
import ru.sumbirsoft.chat.dto.user.ResponseUserDto;
import java.sql.Date;

@Data
public class ResponseMessageDto {
    private Long msgId;
    private ResponseUserDto user;
    private ResponseRoomDto room;
    private String text;
    private Date sendDate;
}