package com.example.KU_2024_hackathon.repository;

import com.example.KU_2024_hackathon.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long>
{
    Optional<Profile> findByEmail(String email);
}
