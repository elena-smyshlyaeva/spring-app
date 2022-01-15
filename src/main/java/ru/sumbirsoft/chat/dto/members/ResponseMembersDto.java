package ru.sumbirsoft.chat.dto.members;

import lombok.Data;
import ru.sumbirsoft.chat.domain.Role;
import ru.sumbirsoft.chat.domain.Status;

@Data
public class ResponseMembersDto {
    private long userId;
    private Role role;
    private Status status;
}
