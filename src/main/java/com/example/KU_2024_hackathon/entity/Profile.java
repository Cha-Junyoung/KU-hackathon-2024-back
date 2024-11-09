package com.example.KU_2024_hackathon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "profile")
public class Profile
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50) private String email;
    @Column(nullable = false, length = 255) private String password;
    @Column(nullable = false, length = 50) private String nickname;
    @Column(nullable = false, length = 50) private LocalDateTime created_at;
    @Column(nullable = false, length = 50) private String role;
}
