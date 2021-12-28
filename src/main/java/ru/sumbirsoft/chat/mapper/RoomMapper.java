package ru.sumbirsoft.chat.mapper;

import org.mapstruct.Mapper;
import ru.sumbirsoft.chat.domain.Room;
import ru.sumbirsoft.chat.dto.room.RequestRoomDto;
import ru.sumbirsoft.chat.dto.room.ResponseRoomDto;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    Room responseRoomDtoToRoom(ResponseRoomDto responseRoomDto);
    ResponseRoomDto roomToResponseRoomDto(Room room);

    Room requestRoomDtoToRoom(RequestRoomDto requestRoomDto);
}