package com.example.KU_2024_hackathon.service;

import com.example.KU_2024_hackathon.dto.ProfileDto;
import com.example.KU_2024_hackathon.entity.Profile;
import com.example.KU_2024_hackathon.exception.CustomErrorCode;
import com.example.KU_2024_hackathon.exception.CustomException;
import com.example.KU_2024_hackathon.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfileService
{
    private final ProfileRepository profileRepository;
    private final BCryptPasswordEncoder encoder;

    /* 회원가입 서비스 */
    @Transactional
    public void join(ProfileDto.Join dto) {
        // 해당 아이디로 생성된 계정이 이미 존재한다면 예외 처리
        profileRepository.findByEmail(dto.getEmail())
                .ifPresent(user -> {
                    throw new CustomException(CustomErrorCode.ALREADY_USED_EMAIL, dto.getEmail());
                });

        // Profile 엔티티 생성
        Profile profile = Profile.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
//                .created_at(LocalDateTime.now())
                .role("ROLE_USER")
                .build();

        // 데이터베이스에 계정 추가
        profileRepository.save(profile);
    }

    /* 감정 색상 조회 서비스 */
    @Transactional
    public ProfileDto.UpdateColor getColor(Principal principal)
    {
        // 로그인 여부 확인 요청을 보낸 사용자의 계정 이메일
        String email = principal.getName();

        // 해당 사용자의 계정이 존재하는지 확인
        Profile profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.EMAIL_NOT_FOUND, email));

        return ProfileDto.UpdateColor.builder()
                .joy(profile.getJoy())
                .angry(profile.getAngry())
                .sad(profile.getSad())
                .afraid(profile.getAfraid())
                .surprise(profile.getSurprise())
                .admiration(profile.getAdmiration())
                .interest(profile.getInterest())
                .boring(profile.getBoring())
                .build();
    }

    /* 감정 색상 수정 서비스 */
    @Transactional
    public void updateColor(Principal principal, ProfileDto.UpdateColor dto)
    {
        // 로그인 여부 확인 요청을 보낸 사용자의 계정 이메일
        String email = principal.getName();

        // 해당 사용자의 계정이 존재하는지 확인
        Profile profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.EMAIL_NOT_FOUND, email));

        // 색상 코드가 전부 6자리인지 확인
        if(
            dto.getJoy().length() != 6 ||
            dto.getAngry().length() != 6 ||
            dto.getSad().length() != 6 ||
            dto.getAfraid().length() != 6 ||
            dto.getAdmiration().length() != 6 ||
            dto.getSurprise().length() != 6 ||
            dto.getInterest().length() != 6 ||
            dto.getBoring().length() != 6
        ) {
            throw new CustomException(CustomErrorCode.INVALID_COLOR_CODE, null);
        }

        // 색상 정보 수정
        profile.setJoy(dto.getJoy());
        profile.setAngry(dto.getAngry());
        profile.setSad(dto.getSad());
        profile.setAfraid(dto.getAfraid());
        profile.setAdmiration(dto.getAdmiration());
        profile.setSurprise(dto.getSurprise());
        profile.setInterest(dto.getInterest());
        profile.setBoring(dto.getBoring());

        // 데이터베이스 반영
        profileRepository.save(profile);
    }

}
