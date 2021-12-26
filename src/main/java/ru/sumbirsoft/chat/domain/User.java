package ru.sumbirsoft.chat.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    Set<Room> ownerIn;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    Set<Members> userRooms;
}
