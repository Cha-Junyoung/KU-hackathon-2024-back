package com.example.KU_2024_hackathon.controller;

import com.example.KU_2024_hackathon.dto.StatisticsDto;
import com.example.KU_2024_hackathon.security.CustomUserDetails;
import com.example.KU_2024_hackathon.service.StatisticsService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@Tag(name = "통계", description = "통계 관련 api입니다.")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/day")
    @Operation(summary = "특정 요일 이미지와 텍스트 확인하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "특정 요일 통계 조회 성공", content = @Content(schema = @Schema(implementation = String.class))),
    })
    public ResponseEntity<StatisticsDto.GetStatisticsResponse> getStatisticsPerDay(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestParam StatisticsDto.GetStatisticsRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                statisticsService.getStatisticsPerDay(customUserDetails, request));
    }

    @GetMapping()
    @Operation(summary = "나의 특정 달 감정 통계 확인하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "감정 통계 조회 성공", content = @Content(schema = @Schema(implementation = String.class))),
    })
    public ResponseEntity<StatisticsDto.GetColorsResponse> getColorsPerMonth(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestParam StatisticsDto.GetColorsRequest request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(statisticsService.getColorsPerMonth(customUserDetails, request));
    }
}
