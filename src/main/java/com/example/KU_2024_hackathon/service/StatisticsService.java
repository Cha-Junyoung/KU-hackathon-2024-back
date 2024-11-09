package com.example.KU_2024_hackathon.service;

import com.example.KU_2024_hackathon.dto.Emotions;
import com.example.KU_2024_hackathon.dto.StatisticsDto;
import com.example.KU_2024_hackathon.dto.StatisticsDto.GetColorsResponse;
import com.example.KU_2024_hackathon.dto.StatisticsDto.GetStatisticsInfoResponse;
import com.example.KU_2024_hackathon.entity.Profile;
import com.example.KU_2024_hackathon.entity.Statistics;
import com.example.KU_2024_hackathon.exception.CustomErrorCode;
import com.example.KU_2024_hackathon.exception.CustomException;
import com.example.KU_2024_hackathon.repository.StatisticsRepository;
import com.example.KU_2024_hackathon.security.CustomUserDetails;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;

    public GetColorsResponse getColorsPerMonth(CustomUserDetails customUserDetails,
                                               String year, String month) {
        Profile profile = customUserDetails.getProfile();
        int specificYear = Integer.parseInt(year);
        int specificMonth = Integer.parseInt(month);
        GetStatisticsInfoResponse[] result = new GetStatisticsInfoResponse[31];

        // 통계 정보 조회
        List<Statistics> statistics = statisticsRepository.findByProfileAndYearAndMonth(profile.getId(), specificYear,
                specificMonth);

        // 통계 정보가 없으면 예외 처리
        if (statistics.isEmpty()) {
            return GetColorsResponse.builder()
                    .colors(result)
                    .build();
        }

        // 프로필에서 색상 정보를 미리 가져오기
        Map<Emotions, String> emotionColorMap = Map.of(
                Emotions.JOY, profile.getJoy(),
                Emotions.ANGRY, profile.getAngry(),
                Emotions.SAD, profile.getSad(),
                Emotions.AFRAID, profile.getAfraid(),
                Emotions.ADMIRATION, profile.getAdmiration(),
                Emotions.SURPRISE, profile.getSurprise(),
                Emotions.INTEREST, profile.getInterest(),
                Emotions.BORING, profile.getBoring()
        );

        // 날짜별 통계 데이터를 저장할 맵 생성
        Map<Integer, Statistics> statisticsByDay = new HashMap<>();
        for (Statistics statistic : statistics) {
            int dayOfMonth = statistic.getCreatedAt().getDayOfMonth();
            statisticsByDay.put(dayOfMonth, statistic);
        }

        // 1일부터 31일까지 순회
        for (int i = 1; i <= 31; i++) {
            Statistics statistic = statisticsByDay.get(i);
            if (statistic != null) {
                Emotions emotion = statistic.getEmotion();
                String emotionColor = emotionColorMap.get(emotion);

                // 예외 처리: 만약 emotionColor가 null이면 예상하지 못한 감정입니다.
                if (emotionColor == null) {
                    throw new CustomException(CustomErrorCode.EMOTION_NOT_FOUND, emotion);
                }

                result[i - 1] = GetStatisticsInfoResponse.builder()
                        .time(statistic.getCreatedAt())
                        .emotion(emotion.toString())
                        .color(emotionColor)
                        .build();
            } else {
                result[i - 1] = null; // 해당 날짜에 데이터가 없는 경우 null
            }
        }

        return GetColorsResponse.builder()
                .colors(result)
                .build();
    }


    public StatisticsDto.GetStatisticsResponse getStatisticsPerDay(CustomUserDetails customUserDetails,
                                                                   String year, String month, String day) {
        Profile profile = customUserDetails.getProfile();
        int specificYear = Integer.parseInt(year);
        int specificMonth = Integer.parseInt(month);
        int specificDay = Integer.parseInt(day);

        Optional<Statistics> statisticsByUserIdAndYearAndMonthAndDay = statisticsRepository.findStatisticsByUserIdAndYearAndMonthAndDay(
                profile.getId(), specificYear,
                specificMonth,
                specificDay);
        if (statisticsByUserIdAndYearAndMonthAndDay.isEmpty()) {
            throw new CustomException(CustomErrorCode.STATISTICS_NOT_FOUND, null);
        }
        return StatisticsDto.GetStatisticsResponse.builder().
                image(statisticsByUserIdAndYearAndMonthAndDay.get().getImage()).
                text(statisticsByUserIdAndYearAndMonthAndDay.get().getText()).
                build();
    }
}
