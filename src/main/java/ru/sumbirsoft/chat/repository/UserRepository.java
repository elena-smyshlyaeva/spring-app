package ru.sumbirsoft.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumbirsoft.chat.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
