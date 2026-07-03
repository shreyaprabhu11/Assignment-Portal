package com.example.demoStudent.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name="userregister")
public class User {

    private String name;

    @Id
    private String username;

    private String role;
    private String email;
    private String phone;
    private String pswrd;
    private String cpswrd;
    private String gender;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "token_expiry")
    private LocalDateTime tokenExpiry;



}
