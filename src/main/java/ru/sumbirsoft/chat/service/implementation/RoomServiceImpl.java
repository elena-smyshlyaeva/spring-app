package ru.sumbirsoft.chat.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.sumbirsoft.chat.domain.Room;
import ru.sumbirsoft.chat.dto.room.RequestRoomDto;
import ru.sumbirsoft.chat.dto.room.ResponseRoomDto;
import ru.sumbirsoft.chat.mapper.RoomMapper;
import ru.sumbirsoft.chat.repository.RoomRepository;
import ru.sumbirsoft.chat.service.RoomService;
import ru.sumbirsoft.chat.exceptions.RoomNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository repository;
    private final RoomMapper roomMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ResponseRoomDto> findAll() {
        return repository.findAll()
                .stream()
                .map(roomMapper::roomToResponseRoomDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseRoomDto findById(long id) {
        Optional<Room> roomOptional = repository.findById(id);
        if (roomOptional.isPresent()) {
            return roomMapper.roomToResponseRoomDto(roomOptional.get());
        }
        throw new RoomNotFoundException(id);
    }

    @Override
    @Transactional
    public ResponseRoomDto edit(long id, RequestRoomDto requestUserDto) {
        Optional<Room> roomOptional = repository.findById(id);
        if (roomOptional.isPresent()) {
            Room room = roomMapper.requestRoomDtoToRoom(requestUserDto);
            room.setRoomId(id);

            repository.save(room);
            return roomMapper.roomToResponseRoomDto(room);
        }
        throw new RoomNotFoundException(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseRoomDto create(RequestRoomDto requestUserDto) {
        return roomMapper.roomToResponseRoomDto(
                repository.save(roomMapper.requestRoomDtoToRoom(requestUserDto)
                )
        );
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        Optional<Room> roomOptional = repository.findById(id);
        if (roomOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new RoomNotFoundException(id);
    }
}
