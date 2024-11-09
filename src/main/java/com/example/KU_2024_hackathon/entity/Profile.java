package com.example.KU_2024_hackathon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "Profile")
public class Profile extends Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    @Builder.Default
    private String joy = "6cff03";
    @Column(nullable = false)
    @Builder.Default
    private String angry = "ed0505";
    @Column(nullable = false)
    @Builder.Default
    private String sad = "ed6605";
    @Column(nullable = false)
    @Builder.Default
    private String afraid = "ed9805";
    @Column(nullable = false)
    @Builder.Default
    private String admiration = "1cff03";
    @Column(nullable = false)
    @Builder.Default
    private String surprise = "9eff03";
    @Column(nullable = false)
    @Builder.Default
    private String interest = "ddff03";
    @Column(nullable = false)
    @Builder.Default
    private String boring = "edbb05";
}
