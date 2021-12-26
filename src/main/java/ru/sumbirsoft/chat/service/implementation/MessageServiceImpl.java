package ru.sumbirsoft.chat.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.sumbirsoft.chat.domain.Message;
import ru.sumbirsoft.chat.dto.message.RequestMessageDto;
import ru.sumbirsoft.chat.dto.message.ResponseMessageDto;
import ru.sumbirsoft.chat.mapper.MessageMapper;
import ru.sumbirsoft.chat.repository.MessageRepository;
import ru.sumbirsoft.chat.service.MessageService;
import ru.sumbirsoft.chat.exceptions.MessageNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repository;
    private final MessageMapper messageMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ResponseMessageDto> findAll() {
        return repository.findAll()
                .stream()
                .map(messageMapper::messageToResponseMessageDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseMessageDto findById(long id) {
        Optional<Message> messageOptional = repository.findById(id);
        if (messageOptional.isPresent()) {
            return messageMapper.messageToResponseMessageDto(messageOptional.get());
        }
        throw new MessageNotFoundException(id);
    }

    @Override
    @Transactional
    public ResponseMessageDto edit(long id, RequestMessageDto requestMessageDto) {
        Optional<Message> messageOptional = repository.findById(id);
        if (messageOptional.isPresent()) {
            Message message = messageMapper.requestMessageDtoToMessage(requestMessageDto);
            message.setMsgId(id);

            repository.save(message);
            return messageMapper.messageToResponseMessageDto(message);
        }
        throw new MessageNotFoundException(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseMessageDto create(RequestMessageDto requestMessageDto) {
        return messageMapper.messageToResponseMessageDto(
                repository.save(messageMapper.requestMessageDtoToMessage(requestMessageDto)
                )
        );
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        Optional<Message> messageOptional = repository.findById(id);
        if (messageOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new MessageNotFoundException(id);
    }
}
