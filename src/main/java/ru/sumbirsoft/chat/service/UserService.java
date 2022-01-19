package ru.sumbirsoft.chat.service;

import org.springframework.security.core.Authentication;
import ru.sumbirsoft.chat.dto.members.ResponseMembersDto;
import ru.sumbirsoft.chat.dto.user.RequestUserDto;
import ru.sumbirsoft.chat.dto.user.ResponseUserDto;
import java.util.List;

public interface UserService {
    List<ResponseUserDto> findAll();
    ResponseUserDto findById(long id);
    ResponseUserDto edit(long id, RequestUserDto requestUserDto);
    ResponseUserDto create(RequestUserDto requestUserDto);
    boolean deleteById(long id);

    ResponseMembersDto appointModer(long id, long roomId);
    ResponseMembersDto deleteModer(long id, long roomId);

    ResponseMembersDto blockUser(long id, long roomId);
    ResponseMembersDto unblockUser(long id, long roomId);

    void renameUser(String oldName, String newName, Authentication authentication);

    long getIdByUsername (String username);

    String processCommand(String command, String parameters, Authentication authentication);
}