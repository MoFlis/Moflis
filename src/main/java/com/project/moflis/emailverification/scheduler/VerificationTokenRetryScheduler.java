package com.project.moflis.emailverification.scheduler;

import com.project.moflis.emailverification.entity.VerificationToken;
import com.project.moflis.emailverification.enums.TokenStatus;
import com.project.moflis.emailverification.repository.VerificationTokenRepository;
import com.project.moflis.emailverification.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VerificationTokenRetryScheduler {

    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;

    public VerificationTokenRetryScheduler(VerificationTokenRepository tokenRepository, EmailService emailService) {
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @Scheduled(fixedDelay = 5 * 60 * 1000)
    @Transactional
    public void retryFailedEmails() {
        List<VerificationToken> failedTokens = tokenRepository.findByStatus(TokenStatus.FAILED);

        for (VerificationToken token : failedTokens) {
            try {
                emailService.sendEmail(
                        token.getEmail(),
                        "회원가입 이메일 인증 재시도 안내",
                        """
                                안녕하세요.
                                
                                MOFLIS 서비스 회원가입 이메일 인증 재시도 메일입니다.
                                
                                인증번호: [%s]
                                
                                감사합니다.
                                - MOFLIS 서비스팀 -
                                """.formatted(token.getToken())
                );

                token.setStatus(TokenStatus.SENT);
            } catch (Exception e) {
                token.setStatus(TokenStatus.FAILED);
            }
            tokenRepository.save(token);
        }

    }

}
