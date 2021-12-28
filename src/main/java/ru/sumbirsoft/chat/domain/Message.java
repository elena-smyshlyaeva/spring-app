package ru.sumbirsoft.chat.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "messages")
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long msgId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Date sendDate;
}
