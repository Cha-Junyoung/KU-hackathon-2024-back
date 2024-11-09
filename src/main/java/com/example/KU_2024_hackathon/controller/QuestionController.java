package com.example.KU_2024_hackathon.controller;

import com.example.KU_2024_hackathon.dto.ErrorDto;
import com.example.KU_2024_hackathon.dto.QuestionDto;
import com.example.KU_2024_hackathon.dto.QuestionDto.Response;
import com.example.KU_2024_hackathon.security.CustomUserDetails;
import com.example.KU_2024_hackathon.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
@Tag(name = "질문", description = "질문 관련 api입니다.")
public class QuestionController {

    private final QuestionService questionService;

    /* 질문 답변 제출 컨트롤러 */
    @PostMapping()
    @Operation(summary = "질문 답변 제출")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "질문 답변 제출 성공", content = @Content(schema = @Schema(implementation = Response.class))),
    })
    public ResponseEntity<QuestionDto.Response> submitAnswer(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody QuestionDto.Request request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(questionService.submitAnswer(customUserDetails, request));
    }

    /* 랜덤 질문 생성 컨트롤러 */
    @GetMapping("/get-random-questions")
    @Operation(summary = "랜덤 질문 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "랜덤 질문 생성 성공", content = @Content(schema = @Schema(implementation = QuestionDto.Questions.class))),
            @ApiResponse(responseCode = "실패: 404 (QUESTION_NOT_FOUND)", description = "어떤 범주에 대한 질문이 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    public ResponseEntity<QuestionDto.Questions> getRandomQuestions() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(questionService.getRandomQuestions());
    }

}
