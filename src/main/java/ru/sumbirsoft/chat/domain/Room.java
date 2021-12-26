package ru.sumbirsoft.chat.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "room")
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roomId;

    @Column(nullable = false)
    private String name;

    @Column
    private boolean isPrivate;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    User owner;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    Set<Members> roomUsers;
}
