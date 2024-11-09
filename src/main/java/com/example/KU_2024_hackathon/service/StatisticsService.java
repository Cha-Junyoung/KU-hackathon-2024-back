package com.example.KU_2024_hackathon.service;

import com.example.KU_2024_hackathon.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;

//    public StatisticsDto.GetColorsResponse getColorsPerMonth(StatisticsDto.GetColorsRequest request) {
//        String year = request.getYear();
//        String month = request.getMonth();
//
//
//    }
}
