package ru.sumbirsoft.chat.service;

import ru.sumbirsoft.chat.domain.User;
import ru.sumbirsoft.chat.dto.user.RequestUserDto;
import ru.sumbirsoft.chat.dto.user.ResponseUserDto;

import java.util.List;

public interface UserService {

    List<ResponseUserDto> findAll();
    ResponseUserDto findById(long id);
    ResponseUserDto edit(RequestUserDto requestUserDto);
    ResponseUserDto create(RequestUserDto requestUserDto);
    boolean deleteById(long id);
}
