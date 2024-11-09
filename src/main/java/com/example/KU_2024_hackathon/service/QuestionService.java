package com.example.KU_2024_hackathon.service;

import com.example.KU_2024_hackathon.dto.Emotions;
import com.example.KU_2024_hackathon.dto.QuestionDto;
import com.example.KU_2024_hackathon.entity.Question;
import com.example.KU_2024_hackathon.entity.Statistics;
import com.example.KU_2024_hackathon.exception.CustomErrorCode;
import com.example.KU_2024_hackathon.exception.CustomException;
import com.example.KU_2024_hackathon.repository.QuestionRepository;
import com.example.KU_2024_hackathon.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final StatisticsRepository statisticsRepository;
    private final QuestionRepository questionRepository;
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
                .question_1(questionRepository.findByQuestion(dto.getQuestion1()).getNumber())
                .question_2(questionRepository.findByQuestion(dto.getQuestion2()).getNumber())
                .question_3(questionRepository.findByQuestion(dto.getQuestion3()).getNumber())
                .answer_1(dto.getAnswer1())
                .answer_2(dto.getAnswer2())
                .answer_3(dto.getAnswer3())
                .text(response.getText())
                .image_link(response.getImageUrl())
                .emotion(Emotions.valueOf(response.getEmotion()))
                .build();

        statisticsRepository.save(statistics);

        return response;
    }

    /* 랜덤 질문 생성 서비스 */
    public QuestionDto.Questions getRandomQuestions()
    {
        // 범주마다의 질문 리스트
        List<String> questionList1 = questionRepository.findByNumber(1);
        List<String> questionList2 = questionRepository.findByNumber(2);
        List<String> questionList3 = questionRepository.findByNumber(3);

        // 범주에 대한 질문이 존재하지 않는다면 예외 처리
        if (questionList1.isEmpty() || questionList2.isEmpty() || questionList3.isEmpty()) {
            throw new CustomException(CustomErrorCode.QUESTION_NOT_FOUND, null);
        }

        // 랜덤한 질문 선택
        Random random = new Random();
        int randomIndex1 = random.nextInt(questionList1.size());
        int randomIndex2 = random.nextInt(questionList2.size());
        int randomIndex3 = random.nextInt(questionList3.size());

        return QuestionDto.Questions.builder()
                .question1(questionList1.get(randomIndex1))
                .question2(questionList2.get(randomIndex2))
                .question3(questionList3.get(randomIndex3))
                .build();
    }
}
