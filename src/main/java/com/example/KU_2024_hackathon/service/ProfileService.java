package com.example.KU_2024_hackathon.service;

import com.example.KU_2024_hackathon.dto.ProfileDto;
import com.example.KU_2024_hackathon.entity.Profile;
import com.example.KU_2024_hackathon.exception.CustomErrorCode;
import com.example.KU_2024_hackathon.exception.CustomException;
import com.example.KU_2024_hackathon.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProfileService
{
    @Autowired ProfileRepository profileRepository;
    @Autowired BCryptPasswordEncoder encoder;

    /* 회원가입 서비스 */
    @Transactional
    public void join(ProfileDto.Join dto)
    {
        // 해당 아이디로 생성된 계정이 이미 존재한다면 예외 처리
        profileRepository.findByEmail(dto.getEmail())
                .ifPresent(user -> {
                    throw new CustomException(CustomErrorCode.ALREADY_USED_ID, dto.getEmail());
                });

        // Profile 엔티티 생성
        Profile profile = Profile.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .created_at(LocalDateTime.now())
                .role("ROLE_USER")
                .build();

        // 데이터베이스에 계정 추가
        profileRepository.save(profile);
    }
}
