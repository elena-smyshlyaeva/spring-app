package ru.sumbirsoft.chat.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class MemberKey implements Serializable {
    @Column(name = "user_id")
    long userId;

    @Column(name = "room_id")
    long roomId;
}
