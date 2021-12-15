package ru.sumbirsoft.chat.mapper;

import org.mapstruct.Mapper;
import ru.sumbirsoft.chat.domain.User;
import ru.sumbirsoft.chat.dto.user.RequestUserDto;
import ru.sumbirsoft.chat.dto.user.ResponseUserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User responseUserDtoToUser(ResponseUserDto dto);
    ResponseUserDto userToResponseUserDto(User entity);

    User requestUserDtoToUser(RequestUserDto requestUserDto);
}
