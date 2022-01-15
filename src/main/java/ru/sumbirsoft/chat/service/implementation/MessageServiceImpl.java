package ru.sumbirsoft.chat.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.sumbirsoft.chat.domain.Message;
import ru.sumbirsoft.chat.dto.message.RequestMessageDto;
import ru.sumbirsoft.chat.dto.message.ResponseMessageDto;
import ru.sumbirsoft.chat.exceptions.BanStatusException;
import ru.sumbirsoft.chat.exceptions.ResourceNotFoundException;
import ru.sumbirsoft.chat.mapper.MessageMapper;
import ru.sumbirsoft.chat.mapper.UserMapper;
import ru.sumbirsoft.chat.repository.MessageRepository;
import ru.sumbirsoft.chat.repository.RoomRepository;
import ru.sumbirsoft.chat.repository.UserRepository;
import ru.sumbirsoft.chat.service.MessageService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repository;
    private final MessageMapper messageMapper;

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

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
        throw new ResourceNotFoundException("Message doesn't exist", Long.toString(id));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseMessageDto edit(long id, RequestMessageDto requestMessageDto) {
        Optional<Message> messageOptional = repository.findById(id);
        if (messageOptional.isPresent()) {
            Message message = messageMapper.requestMessageDtoToMessage(requestMessageDto);
            message.setMsgId(id);

            repository.save(message);
            return messageMapper.messageToResponseMessageDto(message);
        }
        throw new ResourceNotFoundException("Message doesn't exist", Long.toString(id));
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean deleteById(long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (!userDetails.isAccountNonLocked()) {
            throw new BanStatusException(userDetails.getUsername());
        }

        Optional<Message> messageOptional = repository.findById(id);
        if (messageOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ResourceNotFoundException("Message doesn't exist", Long.toString(id));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseMessageDto sendMessage(RequestMessageDto message, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (!userDetails.isAccountNonLocked()) {
            throw new BanStatusException(userDetails.getUsername());
        }

        return messageMapper.messageToResponseMessageDto(repository
                .save(messageMapper.requestMessageDtoToMessage(message)));
    }
}