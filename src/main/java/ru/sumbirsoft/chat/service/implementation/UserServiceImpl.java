package ru.sumbirsoft.chat.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.sumbirsoft.chat.domain.User;
import ru.sumbirsoft.chat.dto.user.RequestUserDto;
import ru.sumbirsoft.chat.dto.user.ResponseUserDto;
import ru.sumbirsoft.chat.mapper.UserMapper;
import ru.sumbirsoft.chat.repository.UserRepository;
import ru.sumbirsoft.chat.service.UserService;
import ru.sumbirsoft.chat.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;


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
        throw new UserNotFoundException(id);
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
        throw new UserNotFoundException(id);
    }

    @Override
    @Transactional
    public ResponseUserDto create(RequestUserDto requestUserDto) {
        return userMapper.userToResponseUserDto(
                repository.save(
                        userMapper.requestUserDtoToUser(requestUserDto)
                )
        );
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new UserNotFoundException(id);
    }
}
