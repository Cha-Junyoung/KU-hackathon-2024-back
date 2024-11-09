package com.example.KU_2024_hackathon.service;

import com.example.KU_2024_hackathon.dto.StatisticsDto;
import com.example.KU_2024_hackathon.dto.StatisticsDto.GetColorsResponse;
import com.example.KU_2024_hackathon.dto.StatisticsDto.GetStatisticsInfoResponse;
import com.example.KU_2024_hackathon.entity.Profile;
import com.example.KU_2024_hackathon.repository.StatisticsRepository;
import com.example.KU_2024_hackathon.security.CustomUserDetails;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;

    public List<GetColorsResponse> getColorsPerMonth(CustomUserDetails customUserDetails,
                                                     StatisticsDto.GetColorsRequest request) {
        Profile profile = customUserDetails.getProfile();
        int year = Integer.parseInt(request.getYear());
        int month = Integer.parseInt(request.getMonth());

        List<Object[]> results = statisticsRepository.findEmotionsByUserIdAndYearAndMonth(
                profile.getId(), year,
                month);

        return results.stream()
                .map(result -> GetStatisticsInfoResponse.builder()
                        .time((LocalDateTime) result[0])
                        .color((String) result[1])
                        .emotion((String) result[2])
                        .build())
                .collect(Collectors.groupingBy(GetStatisticsInfoResponse::getTime))
                .values().stream()
                .map(statistics -> GetColorsResponse.builder()
                        .colors(statistics.toArray(new GetStatisticsInfoResponse[0]))
                        .build())
                .toList();
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
