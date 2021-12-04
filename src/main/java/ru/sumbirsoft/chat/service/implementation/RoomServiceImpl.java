package ru.sumbirsoft.chat.service.implementation;

import org.springframework.stereotype.Service;
import ru.sumbirsoft.chat.dto.room.RequestRoomDto;
import ru.sumbirsoft.chat.dto.room.ResponseRoomDto;
import ru.sumbirsoft.chat.repository.RoomRepository;
import ru.sumbirsoft.chat.service.RoomService;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    RoomRepository repository;

    @Override
    public List<ResponseRoomDto> findAll() {
        return null;
    }

    @Override
    public ResponseRoomDto findById(long id) {
        return null;
    }

    @Override
    public ResponseRoomDto edit(RequestRoomDto requestUserDto) {
        return null;
    }

    @Override
    public ResponseRoomDto create(RequestRoomDto requestUserDto) {
        return null;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }
}
