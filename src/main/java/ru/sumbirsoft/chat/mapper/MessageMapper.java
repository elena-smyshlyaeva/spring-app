package ru.sumbirsoft.chat.mapper;

import org.mapstruct.Mapper;
import ru.sumbirsoft.chat.domain.Message;
import ru.sumbirsoft.chat.dto.message.RequestMessageDto;
import ru.sumbirsoft.chat.dto.message.ResponseMessageDto;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message responseMessageDtoToMessage(ResponseMessageDto responseMessageDto);
    ResponseMessageDto messageToResponseMessageDto(Message message);

    Message requestMessageDtoToMessage(RequestMessageDto requestMessageDto);
}