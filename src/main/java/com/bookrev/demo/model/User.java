package com.bookrev.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue()
    private Long userId;

    private String email;
    private String username;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;


}
