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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "https://web-test-front-m32mykqd6aabb332.sel4.cloudtype.app")
@Tag(name = "프로필", description = "사용자 정보 관련 api입니다.")
public class ProfileController
{
    @Autowired ProfileService profileService;

    /* 회원가입 컨트롤러 */
    @PostMapping("/join")
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공 (성공 메시지 반환)", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "실패: 400 (ALREADY_USED_ID)", description = "해당 아이디로 생성된 계정이 이미 존재하는 경우 (입력받은 아이디를 반환)", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    public ResponseEntity<String> join(@Valid @RequestBody ProfileDto.Join dto)
    {
        profileService.join(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("회원가입을 성공하였습니다.");
    }

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

    /* front-back connection test */
    @GetMapping("/test-connection")
    public String Test(){
        return "connection test";
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
