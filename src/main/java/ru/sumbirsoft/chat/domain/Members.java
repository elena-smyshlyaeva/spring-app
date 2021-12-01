package ru.sumbirsoft.chat.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "members")
@Data
public class Members {
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    Room room;

    private String role;
}
