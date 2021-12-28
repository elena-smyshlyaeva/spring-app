package ru.sumbirsoft.chat.dto.user;

import lombok.Data;

@Data
public class RequestUserDto {
    private String username;
    private String password;
}
