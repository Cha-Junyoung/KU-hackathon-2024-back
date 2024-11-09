package com.example.KU_2024_hackathon.entity;

import com.example.KU_2024_hackathon.dto.Emotions;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "Statistics")
public class Statistics extends Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Profile profile;

    //    @Column(nullable = false) private LocalDateTime created_at;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false)
    private int question_1;
    @Column(nullable = false)
    private int question_2;
    @Column(nullable = false)
    private int question_3;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String answer_1;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String answer_2;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String answer_3;
    @Column(nullable = false)
    private Emotions emotion;
}
