package com.example.KU_2024_hackathon.service;

import com.example.KU_2024_hackathon.dto.StatisticsDto;
import com.example.KU_2024_hackathon.dto.StatisticsDto.GetColorsResponse;
import com.example.KU_2024_hackathon.dto.StatisticsDto.GetStatisticsInfoResponse;
import com.example.KU_2024_hackathon.entity.Profile;
import com.example.KU_2024_hackathon.entity.Statistics;
import com.example.KU_2024_hackathon.exception.CustomErrorCode;
import com.example.KU_2024_hackathon.exception.CustomException;
import com.example.KU_2024_hackathon.repository.StatisticsRepository;
import com.example.KU_2024_hackathon.security.CustomUserDetails;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;

    public List<GetColorsResponse> getColorsPerMonth(CustomUserDetails customUserDetails,
                                                     String year, String month) {
        Profile profile = customUserDetails.getProfile();
        int specificYear = Integer.parseInt(year);
        int specificMonth = Integer.parseInt(month);

        List<Statistics> results = statisticsRepository.findByProfileAndYearAndMonth(profile.getId(), specificYear, specificMonth);

        // 해당 요청자의 해당 달의 통계 정보가 없다면 예외 처리
        if(results.isEmpty())
            throw new CustomException(CustomErrorCode.STATISTICS_NOT_FOUND, null);

        String joyColor = results.getFirst().getProfile().getJoy();
        String angryColor = results.getFirst().getProfile().getAngry();
        String sadColor = results.getFirst().getProfile().getSad();
        String afraidColor = results.getFirst().getProfile().getAfraid();
        String admirationColor = results.getFirst().getProfile().getAdmiration();
        String surpriseColor = results.getFirst().getProfile().getSurprise();
        String interestColor = results.getFirst().getProfile().getInterest();
        String boringColor = results.getFirst().getProfile().getBoring();
        LocalDateTime createdAt = results.getFirst().getCreatedAt();



        return null;
    }

    public StatisticsDto.GetStatisticsResponse getStatisticsPerDay(CustomUserDetails customUserDetails,
                                                                   String year, String month, String day) {
        Profile profile = customUserDetails.getProfile();
        int specificYear = Integer.parseInt(year);
        int specificMonth = Integer.parseInt(month);
        int specificDay = Integer.parseInt(day);

        return statisticsRepository.findStatisticsByUserIdAndYearAndMonthAndDay(profile.getId(), specificYear,
                specificMonth,
                specificDay);
    }
}
