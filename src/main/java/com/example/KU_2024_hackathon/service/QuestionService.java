package com.example.KU_2024_hackathon.service;

import com.example.KU_2024_hackathon.dto.QuestionDto;
import com.example.KU_2024_hackathon.entity.Statistics;
import com.example.KU_2024_hackathon.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final StatisticsRepository statisticsRepository;
    WebClient webClient = WebClient.builder().build();

    public QuestionDto.Response submitAnswer(QuestionDto.Request dto) {
        QuestionDto.Response response = webClient.post()
                .uri("https://www.naver.com")
                .header("Content-Type", "application/json; charset=UTF-8")
//                .header("Referer", "https://sugang.korea.ac.kr/graduate/core?attribute=coreMain&flagx=X&fake=1712483556643")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(QuestionDto.Response.class)
                .block();
        Statistics statistics = Statistics.builder()
                .question_1(dto.getQuestion1())
                .question_2(dto.getQuestion2())
                .question_3(dto.getQuestion3())
                .answer_1(dto.getAnswer1())
                .answer_2(dto.getAnswer2())
                .answer_3(dto.getAnswer3())
                .text(response.getText())
                .image_link(response.getImageUrl())
                .build();

        statisticsRepository.save(statistics);

        return response;
    }
}
