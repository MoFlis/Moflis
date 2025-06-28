package com.project.moflis.emailverification.repository;

import com.project.moflis.emailverification.entity.VerificationToken;
import com.project.moflis.emailverification.enums.TokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    List<VerificationToken> findByStatus(TokenStatus tokenStatus);
}
