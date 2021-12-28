package ru.sumbirsoft.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumbirsoft.chat.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
