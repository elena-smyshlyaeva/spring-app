package ru.sumbirsoft.chat.domain;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Composite key for ManyToMany-relationship table.
 */

@Embeddable
@Data
public class MemberKey implements Serializable {
    @Column(name = "user_id")
    Long userId;

    @Column(name = "room_id")
    Long roomId;
}