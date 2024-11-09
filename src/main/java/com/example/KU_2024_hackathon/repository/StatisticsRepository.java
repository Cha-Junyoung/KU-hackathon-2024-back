package com.example.KU_2024_hackathon.repository;

import com.example.KU_2024_hackathon.dto.StatisticsDto.GetStatisticsResponse;
import com.example.KU_2024_hackathon.entity.Profile;
import com.example.KU_2024_hackathon.entity.Statistics;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    @Query(value = "SELECT s.created_at as time, " +
            "CASE s.emotion " +
            "    WHEN 'joy' THEN p.joy " +
            "    WHEN 'angry' THEN p.angry " +
            "    WHEN 'sad' THEN p.sad " +
            "    WHEN 'afraid' THEN p.afraid " +
            "    WHEN 'admiration' THEN p.admiration " +
            "    WHEN 'surprise' THEN p.surprise " +
            "    WHEN 'interest' THEN p.interest " +
            "    WHEN 'boring' THEN p.boring " +
            "    ELSE 'unknown' " +
            "END as color, " +
            "s.emotion " +
            "FROM statistics s " +
            "JOIN profile p ON s.profile = p.id " +
            "WHERE s.profile = :id " +
            "AND YEAR(s.created_at) = :year " +
            "AND MONTH(s.created_at) = :month", nativeQuery = true)
    List<Object[]> findEmotionsByUserIdAndYearAndMonth(@Param("id") Long id, @Param("year") int year,
                                                       @Param("month") int month);

    @Query(value = "SELECT * FROM statistics WHERE profile = :profile AND YEAR(created_at) = :year AND MONTH(created_at) = :month", nativeQuery = true)
    List<Statistics> findByProfileAndYearAndMonth(@Param("profile") Long id, @Param("year") int year, @Param("month") int month);

    @Query(value = "SELECT s.image_url, s.text FROM Statistics s WHERE s.profile.id = :id AND YEAR(s.created_at) = :year AND MONTH(s.created_at) = :month AND DAY(s.created_at) = :day", nativeQuery = true)
    GetStatisticsResponse findStatisticsByUserIdAndYearAndMonthAndDay(@Param("id") Long id, @Param("year") int year,
                                                                      @Param("month") int month, @Param("day") int day);
}
