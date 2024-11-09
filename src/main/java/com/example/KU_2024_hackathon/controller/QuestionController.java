package com.example.KU_2024_hackathon.controller;

import com.example.KU_2024_hackathon.dto.QuestionDto;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
@Tag(name = "질문", description = "질문 관련 api입니다.")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping()
    @Operation(summary = "질문 답변 제출")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "질문 답변 제출 성공", content = @Content(schema = @Schema(implementation = String.class))),
    })
    public ResponseEntity<QuestionDto.Response> submitAnswer(@Valid @RequestBody QuestionDto.Request request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(questionService.submitAnswer(request));
    }

    /* 랜덤 질문 생성 컨트롤러
    @GetMapping("/get-random-questions")
    @Operation(summary = "랜덤 질문 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "랜덤 질문 생성 성공", content = @Content(schema = @Schema(implementation = String.class))),
    })
    public ResponseEntity<QuestionDto.Questions> getRandomQuestions()
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(questionService.getRandomQuestions());
    }*/
}
