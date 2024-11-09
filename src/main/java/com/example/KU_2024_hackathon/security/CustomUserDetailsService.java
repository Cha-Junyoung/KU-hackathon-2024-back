package com.example.KU_2024_hackathon.security;

import com.example.KU_2024_hackathon.entity.Profile;
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
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException
    {
        // 데이터베이스에서 해당 아이디의 계정 검색
        Optional<Profile> profile = profileRepository.findById(id);

        // 해당 아이디의 계정이 없으면, 예외 처리
        if (profile.isEmpty())
            throw new UsernameNotFoundException(id);

        // 해당 아이디의 계정이 존재하면, 해당 계정으로 만들어진 CustomUserDetails 반환
        return new CustomUserDetails(profile.get());
    }
}
