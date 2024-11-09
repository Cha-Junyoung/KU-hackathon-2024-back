package com.example.KU_2024_hackathon.service;

import com.example.KU_2024_hackathon.dto.StatisticsDto;
import com.example.KU_2024_hackathon.entity.Profile;
import com.example.KU_2024_hackathon.repository.StatisticsRepository;
import com.example.KU_2024_hackathon.security.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;

    public StatisticsDto.GetColorsResponse getColorsPerMonth(CustomUserDetails customUserDetails,
                                                             StatisticsDto.GetColorsRequest request) {
        Profile profile = customUserDetails.getProfile();
        int year = Integer.parseInt(request.getYear());
        int month = Integer.parseInt(request.getMonth());

        List<String> emotionsByUserIdAndYearAndMonth = statisticsRepository.findEmotionsByUserIdAndYearAndMonth(
                profile.getId(), year,
                month);

        // List<String>을 String[] 배열로 변환
        String[] colorsArray = emotionsByUserIdAndYearAndMonth.toArray(new String[0]);

        // GetColorsResponse 객체 생성 및 colors 배열 설정
        return StatisticsDto.GetColorsResponse.builder()
                .colors(colorsArray)
                .build();
    }

    public StatisticsDto.GetStatisticsResponse getStatisticsPerDay(CustomUserDetails customUserDetails,
                                                                   StatisticsDto.GetStatisticsRequest request) {
        Profile profile = customUserDetails.getProfile();
        int year = Integer.parseInt(request.getYear());
        int month = Integer.parseInt(request.getMonth());
        int day = Integer.parseInt(request.getDay());

        return statisticsRepository.findStatisticsByUserIdAndYearAndMonth(profile.getId(), year, month, day);
    }
}
