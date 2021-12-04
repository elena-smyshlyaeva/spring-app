package ru.sumbirsoft.chat.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sumbirsoft.chat.dto.message.RequestMessageDto;
import ru.sumbirsoft.chat.dto.message.ResponseMessageDto;
import ru.sumbirsoft.chat.service.MessageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {
    MessageService messageService;
    ModelMapper modelMapper;

    @GetMapping
    public List<ResponseMessageDto> getAll() {
        return messageService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseMessageDto getById(@PathVariable(name = "id") long id) {
        return messageService.findById(id);
    }

    @PostMapping
    public ResponseMessageDto create(@RequestBody RequestMessageDto requestMessageDto) {
        return messageService.create(requestMessageDto);
    }

    @PutMapping
    public ResponseMessageDto edit(@RequestBody RequestMessageDto requestMessageDto) {
        return messageService.edit(requestMessageDto);
    }

    @DeleteMapping
    public boolean delete(@RequestParam long id) {
        return messageService.deleteById(id);
    }
}
