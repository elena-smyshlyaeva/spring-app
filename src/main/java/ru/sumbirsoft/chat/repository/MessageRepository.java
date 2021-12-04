package ru.sumbirsoft.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumbirsoft.chat.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
