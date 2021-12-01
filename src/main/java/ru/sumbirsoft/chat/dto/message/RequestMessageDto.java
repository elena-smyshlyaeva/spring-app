package ru.sumbirsoft.chat.dto.message;

import lombok.Data;
import ru.sumbirsoft.chat.domain.Room;
import ru.sumbirsoft.chat.domain.User;

import java.sql.Date;

@Data
public class RequestMessageDto {
    private User user;
    private Room room;
    private String text;
    private Date date;
}
