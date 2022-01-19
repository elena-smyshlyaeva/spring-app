package ru.sumbirsoft.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sumbirsoft.chat.dto.members.ResponseMembersDto;
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

    @PutMapping("/{id}/rooms/{roomId}/moderator")
    @PreAuthorize("hasAuthority('user:appoint_moder')")
    public ResponseMembersDto appointModer(@PathVariable (name = "id") long id,
                                           @PathVariable (name ="roomId") long roomId) {
        return userService.appointModer(id, roomId);
    }

    @DeleteMapping("/{id}/rooms/{roomId}/moderator")
    @PreAuthorize("hasAuthority('user:delete_moder')")
    public ResponseMembersDto deleteModer(@PathVariable (name = "id") long id,
                                          @PathVariable (name = "roomId") long roomId){
        return userService.deleteModer(id, roomId);
    }

    @DeleteMapping("/{id}/rooms/{roomId}/status")
    @PreAuthorize("hasAuthority('user:ban')")
    public ResponseMembersDto blockUser(@PathVariable(name = "id") long id,
                                     @PathVariable (name = "roomId") long roomId) {
        return userService.blockUser(id, roomId);
    }

    @PutMapping("/{id}/rooms/{roomId}/status")
    @PreAuthorize("hasAuthority('user:unban')")
    public ResponseMembersDto unblockUser(@PathVariable(name = "id") long id,
                                          @PathVariable (name = "roomId") long roomId) {
        return userService.unblockUser(id, roomId);
    }
}