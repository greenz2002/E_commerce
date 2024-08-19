package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
//@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

//    @ElementCollection
//    private Set<String> role;

    @ManyToMany
    private Set<Roles> roles;
}
