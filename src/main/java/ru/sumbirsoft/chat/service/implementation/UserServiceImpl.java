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
}