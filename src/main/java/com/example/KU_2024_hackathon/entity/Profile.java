package com.example.KU_2024_hackathon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "Profile")
public class Profile
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String email;
    @Column(nullable = false) private String password;
    @Column(nullable = false) private String nickname;
    @Column(nullable = false) private LocalDateTime created_at;
    @Column(nullable = false) private String role;

    @Column(nullable = false)
    @ColumnDefault("'fccf03'")
    private String joy;
    @Column(nullable = false)
    @ColumnDefault("'fc0303'")
    private String angry;
    @Column(nullable = false)
    @ColumnDefault("'0330fc'")
    private String sad;
    @Column(nullable = false)
    @ColumnDefault("'035711'")
    private String afraid;
    @Column(nullable = false)
    @ColumnDefault("'6df754'")
    private String admiration;
    @Column(nullable = false)
    @ColumnDefault("'57a8e6'")
    private String surprise;
    @Column(nullable = false)
    @ColumnDefault("'ffa114'")
    private String interest;
    @Column(nullable = false)
    @ColumnDefault("'b649ba'")
    private String boring;

}
