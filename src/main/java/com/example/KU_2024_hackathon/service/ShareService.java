package com.example.KU_2024_hackathon.service;

import com.example.KU_2024_hackathon.dto.ProfileDto;
import com.example.KU_2024_hackathon.dto.ShareDto;
import com.example.KU_2024_hackathon.entity.Profile;
import com.example.KU_2024_hackathon.entity.Statistics;
import com.example.KU_2024_hackathon.exception.CustomErrorCode;
import com.example.KU_2024_hackathon.exception.CustomException;
import com.example.KU_2024_hackathon.repository.StatisticsRepository;
import com.example.KU_2024_hackathon.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShareService
{
    private final StatisticsRepository statisticsRepository;

    /* 공유하기 버튼 서비스 */
    public ShareDto.Response share(CustomUserDetails customUserDetails, ShareDto.Request request)
    {
        Profile profile = customUserDetails.getProfile();

        Optional<Statistics> statistics_optional = statisticsRepository.findStatisticsByUserIdAndYearAndMonthAndDay(profile.getId(), Integer.parseInt(request.getYear()), Integer.parseInt(request.getMonth()), Integer.parseInt(request.getDay()));

        if(statistics_optional.isEmpty())
            throw new CustomException(CustomErrorCode.STATISTICS_NOT_FOUND, null);

        Statistics statistics = statistics_optional.get();
        statistics.set_public(true);
        statisticsRepository.save(statistics);

        return ShareDto.Response.builder()
                .statistics_key(statistics.getId())
                .build();
    }
}
