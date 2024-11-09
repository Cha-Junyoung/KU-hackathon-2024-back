package com.example.KU_2024_hackathon.repository;

import com.example.KU_2024_hackathon.entity.Statistics;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    Optional<Statistics> findById(Long id);

    @Query(value = "SELECT * FROM statistics WHERE profile = :profile AND YEAR(created_at) = :year AND MONTH(created_at) = :month", nativeQuery = true)
    List<Statistics> findByProfileAndYearAndMonth(@Param("profile") Long id, @Param("year") int year,
                                                  @Param("month") int month);

    @Query(value = "SELECT * FROM statistics s WHERE s.profile = :id AND YEAR(s.created_at) = :year AND MONTH(s.created_at) = :month AND DAY(s.created_at) = :day", nativeQuery = true)
    Optional<Statistics> findStatisticsByUserIdAndYearAndMonthAndDay(@Param("id") Long id, @Param("year") int year,
                                                                     @Param("month") int month, @Param("day") int day);
}
