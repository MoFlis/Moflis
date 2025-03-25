package com.project.moflis.global.security.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Integer id;
    private final String username;

    // 필요한 경우: roles, email 등 추가 가능

    public CustomUserDetails(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 비밀번호가 필요 없는 경우 null 반환 (JWT 기반이기 때문에 사용 안 함)
    @Override
    public String getPassword() {
        return null;
    }

    // 권한 정보 (간단하게 비워도 무방함)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    // 계정 상태 기본값 (항상 true로 설정)
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
}
