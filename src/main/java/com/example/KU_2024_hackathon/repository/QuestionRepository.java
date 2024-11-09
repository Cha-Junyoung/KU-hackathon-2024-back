package com.example.KU_2024_hackathon.repository;

import com.example.KU_2024_hackathon.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long>
{
    // 질문으로 엔티티 찾기
    Question findByQuestion(String question);

    // 해당 범주의 질문 리스트
    @Query(value = "SELECT question FROM question WHERE number = :number", nativeQuery = true)
    List<String> findByNumber(@Param("number") int number);
}
