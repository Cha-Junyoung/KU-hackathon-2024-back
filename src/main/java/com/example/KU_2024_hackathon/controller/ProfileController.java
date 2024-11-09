package com.example.KU_2024_hackathon.controller;

import com.example.KU_2024_hackathon.dto.ErrorDto;
import com.example.KU_2024_hackathon.dto.ProfileDto;
import com.example.KU_2024_hackathon.service.ProfileService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
@Tag(name = "프로필", description = "사용자 정보 관련 api입니다.")
public class ProfileController
{
    private final ProfileService profileService;

    /* 회원가입 컨트롤러 */
    @PostMapping("/join")
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공 (성공 메시지 반환)", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "실패: 400 (ALREADY_USED_EMAIL)", description = "해당 아이디로 생성된 계정이 이미 존재하는 경우 (입력받은 아이디를 반환)", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    public ResponseEntity<String> join(@Valid @RequestBody ProfileDto.Join dto)
    {
        profileService.join(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("회원가입을 성공하였습니다.");
    }

    /* 감정 색상 수정 컨트롤러
    @PostMapping("/update-color")
    @Operation(summary = "감정 색상 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "감정 색상 수정 성공 (성공 메시지 반환)", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "실패: 404 (EMAIL_NOT_FOUND)", description = "요청자의 쿠키에 대한 이메일로 만들어진 계정이 존재하지 않는 경우 (이메일을 반환)", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    public ResponseEntity<String> updateColor(Principal principal, @Valid @RequestBody ProfileDto.UpdateColor dto)
    {
        profileService.updateColor(principal, dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body("감정 색상 수정을 성공하였습니다.");
    }

     */
}
