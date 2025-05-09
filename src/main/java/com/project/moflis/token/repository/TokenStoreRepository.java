package com.project.moflis.token.repository;

import com.project.moflis.token.entity.TokenStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenStoreRepository extends JpaRepository<TokenStore, Long> {

    Optional<TokenStore> deleteByUserId(Long userId);

    Optional<TokenStore> findByUserId(Long userId);
}
