package com.project.moflis.emailverification.service;

import com.project.moflis.emailverification.entity.VerificationToken;
import com.project.moflis.emailverification.repository.VerificationTokenRepository;
import com.project.moflis.user.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailVerificationService {

    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;
    private final UserService userService;

    public EmailVerificationService(VerificationTokenRepository tokenRepository, EmailService emailService, UserService userService) {
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.userService = userService;
    }

    public void sendVerificationEmail(String email) {

        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken(
                email,
                token,
                LocalDateTime.now().plusHours(24)
        );
        tokenRepository.save(verificationToken);

        String verificationLink = "http://localhost:8080/email/verify?token=" + token;

        emailService.sendEmail(
                email,
                "회원가입 이메일 인증 안내",
                """
                        안녕하세요.
                        
                        MOFLIS 서비스 회원가입을 진행해주셔서 감사합니다.
                        
                        아래 링크를 클릭하시면 이메일 인증이 완료됩니다.
                        
                        %s
                        
                        만약 회원가입을 요청하지 않으셨다면 본 이메일은 무시하셔도 됩니다.
                        
                        감사합니다.
                        - MOFLIS 서비스팀 -
                        """.formatted(verificationLink)
        );
    }

    public boolean verifyToken(String token) {

        VerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("유효하지 않은 토큰입니다."));

        if (verificationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("토큰이 만료되었습니다.");
        }

        userService.updateEmailVerified(verificationToken.getEmail());
        tokenRepository.delete(verificationToken);

        return true;
    }
}
