package ru.sumbirsoft.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumbirsoft.chat.domain.MemberKey;
import ru.sumbirsoft.chat.domain.Members;

public interface MembersRepository extends JpaRepository<Members, MemberKey> {
}
