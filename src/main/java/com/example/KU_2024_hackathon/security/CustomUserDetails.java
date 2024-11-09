package com.example.KU_2024_hackathon.security;

import com.example.KU_2024_hackathon.entity.Profile;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
    private final Profile profile;

    // 생성자
    public CustomUserDetails(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return profile.getRole();
            }
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return profile.getPassword();
    }

    @Override
    public String getUsername() {
        return profile.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CustomUserDetails) {
            return this.profile.getId().equals(((CustomUserDetails) obj).profile.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.profile.getId().hashCode();
    }
}
