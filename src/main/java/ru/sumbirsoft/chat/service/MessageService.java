package ru.sumbirsoft.chat.service;

import ru.sumbirsoft.chat.domain.User;
import ru.sumbirsoft.chat.dto.message.RequestMessageDto;
import ru.sumbirsoft.chat.dto.message.ResponseMessageDto;
import ru.sumbirsoft.chat.dto.user.RequestUserDto;
import ru.sumbirsoft.chat.dto.user.ResponseUserDto;

import java.util.List;

public interface MessageService {
    List<ResponseMessageDto> findAll();
    ResponseMessageDto findById(long id);
    ResponseMessageDto edit(long id, RequestMessageDto requestMessageDto);
    ResponseMessageDto create(RequestMessageDto requestMessageDto);
    boolean deleteById(long id);
}
