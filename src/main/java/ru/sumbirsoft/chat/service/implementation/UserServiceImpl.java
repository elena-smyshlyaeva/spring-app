package ru.sumbirsoft.chat.service.implementation;

import org.springframework.stereotype.Service;
import ru.sumbirsoft.chat.dto.user.RequestUserDto;
import ru.sumbirsoft.chat.dto.user.ResponseUserDto;
import ru.sumbirsoft.chat.repository.UserRepository;
import ru.sumbirsoft.chat.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository repository;

    @Override
    public List<ResponseUserDto> findAll() {
        return null;
    }

    @Override
    public ResponseUserDto findById(long id) {
        return null;
    }

    @Override
    public ResponseUserDto edit(RequestUserDto requestUserDto) {
        return null;
    }

    @Override
    public ResponseUserDto create(RequestUserDto requestUserDto) {
        return null;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }
}
