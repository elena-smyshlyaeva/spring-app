package ru.sumbirsoft.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sumbirsoft.chat.dto.message.RequestMessageDto;
import ru.sumbirsoft.chat.dto.message.ResponseMessageDto;
import ru.sumbirsoft.chat.dto.user.RequestUserDto;
import ru.sumbirsoft.chat.service.MessageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public List<ResponseMessageDto> getAll() {
        return messageService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseMessageDto getById(@PathVariable(name = "id") long id) {
        return messageService.findById(id);
    }

    @PutMapping
    public ResponseMessageDto edit(@PathVariable(name = "id") long id,
                                   @RequestBody RequestMessageDto requestMessageDto) {
        return messageService.edit(id, requestMessageDto);
    }

    @PostMapping
    public ResponseMessageDto sendMessage(@RequestParam(name = "from") long from,
                                          @RequestParam(name = "to") long to,
                                          @RequestParam(name = "room") long room,
                                          @RequestBody RequestMessageDto requestMessageDto) {
        return messageService.sendMessage(from, to, room, requestMessageDto);
    }

    @GetMapping("/message")
    public ResponseMessageDto receiveMessage(@RequestParam(name = "id") long id) {
        return messageService.receiveMessage(id);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('message:delete')")
    public boolean delete(@RequestParam(name = "id") long id) {
        return messageService.deleteById(id);
    }
}