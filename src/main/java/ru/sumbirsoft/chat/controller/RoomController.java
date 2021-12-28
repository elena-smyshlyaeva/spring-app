package ru.sumbirsoft.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
    public ResponseRoomDto getById(@PathVariable(name = "id") long id) {
        return roomService.findById(id);
    }

    @PostMapping
    public ResponseRoomDto create(@RequestBody RequestRoomDto requestRoomDto) {
        return roomService.create(requestRoomDto);
    }

    @PutMapping
    public ResponseRoomDto edit(@PathVariable(name = "id") long id,
                                @RequestBody RequestRoomDto requestRoomDto) {
        return roomService.edit(id, requestRoomDto);
    }

    @DeleteMapping
    public boolean delete(@RequestParam long id) {
        return roomService.deleteById(id);
    }
}