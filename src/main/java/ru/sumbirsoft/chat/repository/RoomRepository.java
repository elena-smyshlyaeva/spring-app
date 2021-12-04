package ru.sumbirsoft.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumbirsoft.chat.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
