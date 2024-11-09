package com.example.KU_2024_hackathon.repository;

import com.example.KU_2024_hackathon.dto.StatisticsDto.GetStatisticsResponse;
import com.example.KU_2024_hackathon.entity.Statistics;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    @Query(value = "SELECT s.emotion FROM Statistics s WHERE s.profile.id = :id AND YEAR(s.created_at) = :year AND MONTH(s.created_at) = :month", nativeQuery = true)
    List<String> findEmotionsByUserIdAndYearAndMonth(@Param("id") Long id, @Param("year") int year,
                                                     @Param("month") int month);

    @Query(value = "SELECT s.image_url, s.text FROM Statistics s WHERE s.profile.id = :id AND YEAR(s.created_at) = :year AND MONTH(s.created_at) = :month AND DAY(s.created_at) = :day", nativeQuery = true)
    GetStatisticsResponse findStatisticsByUserIdAndYearAndMonth(@Param("id") Long id, @Param("year") int year,
                                                                @Param("month") int month, @Param("day") int day);
}
