package com.example.KU_2024_hackathon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "Statistics")
public class Statistics
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Profile profile;

    @Column(nullable = false) private LocalDateTime created_at;
    @Column(nullable = false) private String text;
    @Column(nullable = false) private String image_link;
    @Column(columnDefinition = "TEXT", nullable = false) private String question_1;
    @Column(columnDefinition = "TEXT", nullable = false) private String question_2;
    @Column(columnDefinition = "TEXT", nullable = false) private String question_3;
    @Column(columnDefinition = "TEXT", nullable = false) private String answer_1;
    @Column(columnDefinition = "TEXT", nullable = false) private String answer_2;
    @Column(columnDefinition = "TEXT", nullable = false) private String answer_3;
}
