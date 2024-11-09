package com.example.KU_2024_hackathon.controller;

import com.example.KU_2024_hackathon.dto.ErrorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Tag(name = "테스트", description = "테스트 관련 api입니다.")
public class TestController
{
    /* 로그인하지 않아도 되는 요청 테스트 */
    @GetMapping("/test-all")
    @Operation(summary = "로그인하지 않아도 되는 요청 테스트")
    @Parameters(value = {
            @Parameter(name = "message", description = "메시지"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "테스트 성공 (요청 메시지를 그대로 반환)", content = @Content(schema = @Schema(implementation = String.class))),
    })
    public ResponseEntity<String> testAll(@RequestParam("message") String message)
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(message);
    }

    /* 로그인해야 되는 요청 테스트 */
    @GetMapping("/test-user")
    @Operation(summary = "로그인해야 되는 요청 테스트")
    @Parameters(value = {
            @Parameter(name = "message", description = "메시지"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "테스트 성공 (요청 메시지를 그대로 반환)", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "실패: 401 (UNAUTHORIZED_REQUEST)", description = "로그인하지 않은 경우", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    public ResponseEntity<String> testUser(@RequestParam("message") String message)
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(message);
    }

    /* 관리자 계정으로 로그인해야 되는 요청 테스트 */
    @GetMapping("/test-admin")
    @Operation(summary = "관리자 계정으로 로그인해야 되는 요청 테스트")
    @Parameters(value = {
            @Parameter(name = "message", description = "메시지"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "테스트 성공 (요청 메시지를 그대로 반환)", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "실패: 401 (UNAUTHORIZED_REQUEST)", description = "로그인하지 않은 경우", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "실패: 401 (LOW_AUTHORITY_REQUEST)", description = "로그인했지만, 권한이 부족한 경우", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    public ResponseEntity<String> testAdmin(@RequestParam("message") String message)
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(message);
    }
}
