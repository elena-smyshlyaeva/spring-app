package ru.sumbirsoft.chat.service.implementation;

import org.springframework.stereotype.Service;
import ru.sumbirsoft.chat.dto.message.RequestMessageDto;
import ru.sumbirsoft.chat.dto.message.ResponseMessageDto;
import ru.sumbirsoft.chat.repository.MessageRepository;
import ru.sumbirsoft.chat.service.MessageService;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    MessageRepository repository;

    @Override
    public List<ResponseMessageDto> findAll() {
        return null;
    }

    @Override
    public ResponseMessageDto findById(long id) {
        return null;
    }

    @Override
    public ResponseMessageDto edit(RequestMessageDto requestMessageDto) {
        return null;
    }

    @Override
    public ResponseMessageDto create(RequestMessageDto requestMessageDto) {
        return null;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }
}
