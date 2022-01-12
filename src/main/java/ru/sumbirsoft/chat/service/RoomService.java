package ru.sumbirsoft.chat.service;

import org.springframework.security.core.Authentication;
import ru.sumbirsoft.chat.domain.Members;
import ru.sumbirsoft.chat.dto.room.RequestRoomDto;
import ru.sumbirsoft.chat.dto.room.ResponseRoomDto;
import java.util.List;

public interface RoomService {
    List<ResponseRoomDto> findAll();
    ResponseRoomDto findById(long id);
    ResponseRoomDto edit(long id, RequestRoomDto requestUserDto);
    ResponseRoomDto create(RequestRoomDto requestUserDto);
    boolean deleteById(long id);

    ResponseRoomDto createRoom(RequestRoomDto requestRoomDto, Authentication authentication, boolean isPrivate);
    boolean addUser(long roomId, long userId, Authentication authentication);
    boolean deleteUserFromRoom(long roomId, long userId, Authentication authentication);
    ResponseRoomDto renameRoom(long roomId, String name, Authentication authentication);
}