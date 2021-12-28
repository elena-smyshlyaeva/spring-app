package ru.sumbirsoft.chat.service;

import ru.sumbirsoft.chat.dto.message.RequestMessageDto;
import ru.sumbirsoft.chat.dto.message.ResponseMessageDto;
import java.util.List;

public interface MessageService {
    List<ResponseMessageDto> findAll();
    ResponseMessageDto findById(long id);
    ResponseMessageDto edit(long id, RequestMessageDto requestMessageDto);
    ResponseMessageDto create(RequestMessageDto requestMessageDto);
    boolean deleteById(long id);
}