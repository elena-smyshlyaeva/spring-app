package ru.sumbirsoft.chat.service;

import ru.sumbirsoft.chat.dto.room.RequestRoomDto;
import ru.sumbirsoft.chat.dto.room.ResponseRoomDto;

import java.util.List;

public interface RoomService {
    List<ResponseRoomDto> findAll();
    ResponseRoomDto findById(long id);
    ResponseRoomDto edit(long id, RequestRoomDto requestUserDto);
    ResponseRoomDto create(RequestRoomDto requestUserDto);
    boolean deleteById(long id);
}
