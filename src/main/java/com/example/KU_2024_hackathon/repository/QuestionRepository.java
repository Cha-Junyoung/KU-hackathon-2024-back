package com.example.KU_2024_hackathon.repository;

import com.example.KU_2024_hackathon.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long>
{
    Question findByQuestion(String question);
}
