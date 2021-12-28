package ru.sumbirsoft.chat.dto.user;

import lombok.Data;

@Data
public class ResponseUserDto {
    private Long userId;
    private String username;
}