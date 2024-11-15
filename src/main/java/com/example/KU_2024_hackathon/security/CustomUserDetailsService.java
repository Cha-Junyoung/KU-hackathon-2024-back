package com.example.KU_2024_hackathon.security;

import com.example.KU_2024_hackathon.entity.Profile;
import com.example.KU_2024_hackathon.exception.CustomErrorCode;
import com.example.KU_2024_hackathon.exception.CustomException;
import com.example.KU_2024_hackathon.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        // 데이터베이스에서 해당 아이디의 계정 검색
        Optional<Profile> profile = profileRepository.findByEmail(email);

        // 해당 아이디의 계정이 없으면, 예외 처리
        if (profile.isEmpty())
            throw new UsernameNotFoundException(email);

        // 해당 아이디의 계정이 존재하면, 해당 계정으로 만들어진 CustomUserDetails 반환
        return new CustomUserDetails(profile.get());
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException
    {
        // profile 데이터베이스에서 해당 아이디의 계정 검색
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorCode.LOGIN_FAILURE, null));

        // 해당 이메일의 계정이 존재하면, Spring security에서 제공하는 User 클래스를 빌드
        return new CustomUserDetails(profile);
    }
}
