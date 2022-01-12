package ru.sumbirsoft.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.sumbirsoft.chat.domain.Members;
import ru.sumbirsoft.chat.dto.room.RequestRoomDto;
import ru.sumbirsoft.chat.dto.room.ResponseRoomDto;
import ru.sumbirsoft.chat.service.RoomService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
public class RoomController {
    
    private final RoomService roomService;

    @GetMapping
    public List<ResponseRoomDto> getAll() {
        return roomService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseRoomDto getById(@PathVariable(name = "id") long id) {
        return roomService.findById(id);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('user:write')")
    public boolean delete(@RequestParam long id) {
        return roomService.deleteById(id);
    }

    @PostMapping
    public ResponseRoomDto createRoom(@RequestBody RequestRoomDto requestRoomDto,
                                      @RequestParam(name = "private") boolean isPrivate,
                                      Authentication authentication) {
        return roomService.createRoom(requestRoomDto, authentication, isPrivate);
    }

    @PostMapping("/{roomId}/user/{userId}")
    public boolean addUser(@PathVariable(name = "roomId") long roomId,
                                @PathVariable(name = "userId") long userId,
                                Authentication authentication) {
        return roomService.addUser(roomId, userId, authentication);
    }


    @DeleteMapping("/{roomId}/user/{userId}")
    public boolean deleteUserFromRoom(@PathVariable(name = "roomId") long roomId,
                                      @PathVariable(name = "userId") long userId,
                                      Authentication authentication) {
        return roomService.deleteUserFromRoom(roomId, userId, authentication);
    }

    @PutMapping("/{id}")
    public ResponseRoomDto renameRoom(@PathVariable(name = "id") long roomId,
                                      @RequestBody String name,
                                      Authentication authentication) {
        return roomService.renameRoom(roomId, name, authentication);
    }
}