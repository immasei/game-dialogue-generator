package com.example.game_dialogue_generator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

// orm::hibernate
@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer userid;

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false, length = 64)
    @JsonIgnore
    private String password;

    public Integer getUserId() {
        return userid;
    }

    public void setUserId(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}