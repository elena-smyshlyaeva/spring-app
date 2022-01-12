package ru.sumbirsoft.chat.service;

import ru.sumbirsoft.chat.dto.user.RequestUserDto;
import ru.sumbirsoft.chat.dto.user.ResponseUserDto;
import java.util.List;

public interface UserService {
    List<ResponseUserDto> findAll();
    ResponseUserDto findById(long id);
    ResponseUserDto edit(long id, RequestUserDto requestUserDto);
    ResponseUserDto create(RequestUserDto requestUserDto);
    boolean deleteById(long id);

    ResponseUserDto appointModer(long id);
    ResponseUserDto deleteModer(long id);

    ResponseUserDto blockUser(long id);
    ResponseUserDto unblockUser(long id);
}
