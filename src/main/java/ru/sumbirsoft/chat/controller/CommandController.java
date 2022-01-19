package ru.sumbirsoft.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sumbirsoft.chat.service.implementation.CommandServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/commands")
public class CommandController {

    private final CommandServiceImpl commandService;

    @PostMapping
    public String sendCommand(@RequestBody String message,
                              Authentication authentication) {
        return commandService.parse(message, authentication);
    }
}