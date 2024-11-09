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
    @Builder.Default
    private String joy = "fccf03";
    @Column(nullable = false)
    @Builder.Default
    private String angry = "fc0303";
    @Column(nullable = false)
    @Builder.Default
    private String sad = "0330fc";
    @Column(nullable = false)
    @Builder.Default
    private String afraid = "035711";
    @Column(nullable = false)
    @Builder.Default
    private String admiration = "6df754";
    @Column(nullable = false)
    @Builder.Default
    private String surprise = "57a8e6";
    @Column(nullable = false)
    @Builder.Default
    private String interest = "ffa114";
    @Column(nullable = false)
    @Builder.Default
    private String boring = "b649ba";
}
