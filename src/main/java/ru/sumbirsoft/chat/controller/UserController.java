package ru.sumbirsoft.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sumbirsoft.chat.dto.user.RequestUserDto;
import ru.sumbirsoft.chat.dto.user.ResponseUserDto;
import ru.sumbirsoft.chat.service.UserService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<ResponseUserDto> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseUserDto getById(@PathVariable(name = "id") long id) {
        return userService.findById(id);
    }

    @PostMapping
    public ResponseUserDto create(@RequestBody RequestUserDto requestUserDto) {
        return userService.create(requestUserDto);
    }

    @PutMapping
    public ResponseUserDto edit(@PathVariable(name = "id") long id,
                                @RequestBody RequestUserDto requestUserDto) {
        return userService.edit(id, requestUserDto);
    }

    @DeleteMapping
    public boolean delete(@RequestParam long id) {
        return userService.deleteById(id);
    }
}