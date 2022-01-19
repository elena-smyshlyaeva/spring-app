package ru.sumbirsoft.chat.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.sumbirsoft.chat.domain.*;
import ru.sumbirsoft.chat.dto.members.ResponseMembersDto;
import ru.sumbirsoft.chat.dto.user.RequestUserDto;
import ru.sumbirsoft.chat.dto.user.ResponseUserDto;
import ru.sumbirsoft.chat.exceptions.NotEnoughCredentialsException;
import ru.sumbirsoft.chat.exceptions.ResourceNotFoundException;
import ru.sumbirsoft.chat.mapper.MembersMapper;
import ru.sumbirsoft.chat.mapper.UserMapper;
import ru.sumbirsoft.chat.repository.MembersRepository;
import ru.sumbirsoft.chat.repository.RoomRepository;
import ru.sumbirsoft.chat.repository.UserRepository;
import ru.sumbirsoft.chat.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;

    private final MembersRepository membersRepository;
    private final MembersMapper membersMapper;

    private final PasswordEncoder passwordEncoder;

    private final RoomRepository roomRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ResponseUserDto> findAll() {
        return repository.findAll()
                .stream()
                .map(userMapper::userToResponseUserDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseUserDto findById(long id) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            return userMapper.userToResponseUserDto(userOptional.get());
        }
        throw new ResourceNotFoundException("User doesn't exist", Long.toString(id));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseUserDto edit(long id, RequestUserDto requestUserDto) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            User user = userMapper.requestUserDtoToUser(requestUserDto);
            user.setUserId(id);
            repository.save(user);

            return userMapper.userToResponseUserDto(user);
        }
        throw new ResourceNotFoundException("User doesn't exist", Long.toString(id));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseUserDto create(RequestUserDto requestUserDto) {
        requestUserDto.setPassword(passwordEncoder.encode(requestUserDto.getPassword()));

        return userMapper.userToResponseUserDto(
                repository.save(
                        userMapper.requestUserDtoToUser(requestUserDto)
                )
        );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean deleteById(long id) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            repository.deleteById(id);

            return true;
        }
        throw new ResourceNotFoundException("User doesn't exist", Long.toString(id));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseMembersDto appointModer(long id, long roomId) {
        MemberKey key = new MemberKey();
        key.setUserId(id);
        key.setRoomId(roomId);

        Members entry = membersRepository.getById(key);
        entry.setRole(Role.MODERATOR);
        membersRepository.save(entry);
        return membersMapper.membersToResponseMembersDto(entry);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseMembersDto deleteModer(long id, long roomId) {
        MemberKey key = new MemberKey();
        key.setUserId(id);
        key.setRoomId(roomId);

        Members entry = membersRepository.getById(key);
        entry.setRole(Role.USER);
        membersRepository.save(entry);
        return membersMapper.membersToResponseMembersDto(entry);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseMembersDto blockUser(long id, long roomId) {
        MemberKey key = new MemberKey();
        key.setUserId(id);
        key.setRoomId(roomId);

        Members entry = membersRepository.getById(key);
        entry.setStatus(Status.BANNED);
        membersRepository.save(entry);
        return membersMapper.membersToResponseMembersDto(entry);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseMembersDto unblockUser(long id, long roomId) {
        MemberKey key = new MemberKey();
        key.setUserId(id);
        key.setRoomId(roomId);

        Members entry = membersRepository.getById(key);
        entry.setStatus(Status.ACTIVE);
        membersRepository.save(entry);
        return membersMapper.membersToResponseMembersDto(entry);
    }

    @Override
    @Transactional(readOnly = true)
    public long getIdByUsername(String username) {
        User user = repository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User not found", username)
        );
        return user.getUserId();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void renameUser(String oldName, String newName, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User querySender = repository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("User not found", userDetails.getUsername())
        );

        if (querySender.getRole() != Role.ADMIN)
            throw new NotEnoughCredentialsException(querySender.getUsername());

        User changeNameUser = repository.findByUsername(oldName).orElseThrow(
                () -> new ResourceNotFoundException("User not found", oldName)
        );
        changeNameUser.setUsername(newName);
        repository.save(changeNameUser);
    }

    private long getRoomIdByName(String name) {
        Room room = roomRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Room not found", name)
        );
        return room.getRoomId();
    }

    @Override
    public String processCommand(String command, String parameters, Authentication authentication) {
        String[] tokens = parameters.split(" ");
        String username = tokens[0];
        switch (command) {
            case "rename": {
                if (tokens.length > 1) {
                    String newUsername = tokens[1];
                    renameUser(username, newUsername, authentication);
                    return "User {" + username + "} now has name {" + newUsername + "}!";
                }
            }
            case "moderator": {
                if (tokens.length > 2) {
                    String roomName = tokens[1];
                    String param = tokens[2];
                    if (param.equals("-n")) {
                        appointModer(getIdByUsername(username), getRoomIdByName(roomName));
                        return "User {" + username + "} has role MODERATOR now!";
                    }
                    else {
                        if (param.equals("-d")) {
                            deleteModer(getIdByUsername(username), getRoomIdByName(roomName));
                            return "User {" + username + "} has role USER now.";
                        }
                    }
                    throw new IllegalArgumentException();
                }
            }
        }
        throw new IllegalArgumentException();
    }
}