package com.example.KU_2024_hackathon.controller;

import com.example.KU_2024_hackathon.dto.QuestionDto;
import com.example.KU_2024_hackathon.dto.ShareDto;
import com.example.KU_2024_hackathon.security.CustomUserDetails;
import com.example.KU_2024_hackathon.service.ShareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/share")
@RequiredArgsConstructor
@Tag(name = "공유", description = "공유 관련 api입니다.")
public class ShareController
{
    private final ShareService shareService;

    /* 공유하기 버튼 컨트롤러 */
    @PostMapping()
    @Operation(summary = "공유하기 버튼")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공유하기 버튼 성공", content = @Content(schema = @Schema(implementation = ShareDto.Response.class))),
    })
    public ResponseEntity<ShareDto.Response> share(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody ShareDto.Request request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(shareService.share(customUserDetails, request));
    }

    /* 공유당하기 컨트롤러 */
    @GetMapping("/view")
    @Operation(summary = "공유당하기")
    @Parameters(value = {
            @Parameter(name = "id", description = "statistics 데이터의 PK"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공유당하기 성공", content = @Content(schema = @Schema(implementation = ShareDto.View.class))),
    })
    public ResponseEntity<ShareDto.View> view(@RequestParam("id") Long id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(shareService.view(id));
    }
}
