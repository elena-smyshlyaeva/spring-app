package ru.sumbirsoft.chat.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.sumbirsoft.chat.domain.*;
import ru.sumbirsoft.chat.dto.room.RequestRoomDto;
import ru.sumbirsoft.chat.dto.room.ResponseRoomDto;
import ru.sumbirsoft.chat.exceptions.ResourceNotFoundException;
import ru.sumbirsoft.chat.mapper.RoomMapper;
import ru.sumbirsoft.chat.repository.MembersRepository;
import ru.sumbirsoft.chat.repository.RoomRepository;
import ru.sumbirsoft.chat.repository.UserRepository;
import ru.sumbirsoft.chat.service.RoomService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository repository;
    private final UserRepository userRepository;
    private final MembersRepository membersRepository;

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
        throw new ResourceNotFoundException("Room doesn't exist", Long.toString(id));
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
        throw new ResourceNotFoundException("Room doesn't exist", Long.toString(id));
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean deleteById(long id) {
        Optional<Room> roomOptional = repository.findById(id);
        if (roomOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ResourceNotFoundException("Room doesn't exist", Long.toString(id));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseRoomDto createRoom(RequestRoomDto requestRoomDto, Authentication authentication, boolean isPrivate) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (!userDetails.isAccountNonLocked())
            throw new RuntimeException("User has BAN status");

        Room room = roomMapper.requestRoomDtoToRoom(requestRoomDto);
        room.setOwner(userRepository.findByUsername(userDetails.getUsername()).get());
        room.setPrivate(isPrivate);

        return roomMapper.roomToResponseRoomDto(repository.save(room));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean addUser(long roomId, long userId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (!userDetails.isAccountNonLocked())
            throw new RuntimeException("User has BAN status");

        User addedUser = userRepository.getById(userId);
        Room room = repository.getById(roomId);

        Set<Members> userRooms = addedUser.getUserRooms();
        Set<Members> roomUsers = room.getRoomUsers();

        Members note = new Members();
        note.setRole(addedUser.getRole());
        note.setRoom(room);
        note.setUser(addedUser);

        MemberKey key = new MemberKey();
        key.setRoomId(roomId);
        key.setUserId(userId);

        note.setId(key);
        note.setStatus(Status.ACTIVE);

        userRooms.add(note);
        roomUsers.add(note);
        membersRepository.save(note);

        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean deleteUserFromRoom(long roomId, long userId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (!userDetails.isAccountNonLocked()) {
            throw new RuntimeException("User has BAN status");
        }

        User deletedUser = userRepository.getById(userId);
        Set<Members> userRooms = deletedUser.getUserRooms();
        Set<Members> roomUsers = repository.getById(roomId).getRoomUsers();

        userRooms.removeIf(members -> members.getRoom().getRoomId() == roomId);
        roomUsers.removeIf(members -> members.getUser().getUserId() == userId);

        membersRepository.delete(
                membersRepository
                        .findAll()
                        .stream()
                        .filter(members ->
                                members.getUser().getUserId() == userId && members.getRoom().getRoomId() == roomId)
                        .findAny().orElseThrow(() ->
                                new ResourceNotFoundException("Room or user doesn't exist", Long.toString(roomId)))
        );
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseRoomDto renameRoom(long roomId, String name, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository
                .findByUsername(userDetails.getUsername()).orElseThrow(() ->
                        new ResourceNotFoundException("User doesn't exist", userDetails.getUsername()));

        long ownerId = repository.getById(roomId).getOwner().getUserId();
        long userId = user.getUserId();

        MemberKey userRoomId = new MemberKey();
        userRoomId.setRoomId(roomId);
        userRoomId.setUserId(userId);

        Members members = membersRepository.getById(userRoomId);

        if ((userId == ownerId || members.getRole() == Role.ADMIN) && user.getStatus() == Status.ACTIVE) {
            Room room = repository.getById(roomId);
            room.setName(name);

            return roomMapper.roomToResponseRoomDto(repository.save(room));
        }
        throw new RuntimeException("You haven't enough credentials");
    }
}