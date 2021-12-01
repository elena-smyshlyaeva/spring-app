package ru.sumbirsoft.chat.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "room")
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roomId;

    @Column(nullable = false)
    private String name;

    private boolean isPrivate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    User owner;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "room")
    Set<Members> members;
}
