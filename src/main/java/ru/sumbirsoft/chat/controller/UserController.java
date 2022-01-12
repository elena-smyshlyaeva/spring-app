package ru.sumbirsoft.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseUserDto getById(@PathVariable(name = "id") long id) {
        return userService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseUserDto create(@RequestBody RequestUserDto requestUserDto) {
        return userService.create(requestUserDto);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseUserDto edit(@PathVariable(name = "id") long id,
                                @RequestBody RequestUserDto requestUserDto) {
        return userService.edit(id, requestUserDto);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('user:write')")
    public boolean delete(@RequestParam long id) {
        return userService.deleteById(id);
    }

    @PutMapping("/{id}/moderator")
    @PreAuthorize("hasAuthority('user:appoint_moder')")
    public ResponseUserDto appointModer(@PathVariable (name = "id") long id) {
        return userService.appointModer(id);
    }

    @DeleteMapping("/{id}/moderator")
    @PreAuthorize("hasAuthority('user:delete_moder')")
    public ResponseUserDto deleteModer(@PathVariable (name = "id") long id){
        return userService.deleteModer(id);
    }

    @DeleteMapping("/{id}/status")
    @PreAuthorize("hasAuthority('user:ban')")
    public ResponseUserDto blockUser(@PathVariable(name = "id") long id) {
        return userService.blockUser(id);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('user:unban')")
    public ResponseUserDto unblockUser(@PathVariable(name = "id") long id) {
        return userService.unblockUser(id);
    }
}