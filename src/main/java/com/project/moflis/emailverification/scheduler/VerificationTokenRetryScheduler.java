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
    public void retryFailedEmails() {
        List<VerificationToken> failedTokens = tokenRepository.findByStatus(TokenStatus.FAILED);

        for (VerificationToken token : failedTokens) {
            boolean success = sendVerificationEmail(token);

            updateTokenStatus(token, success);
        }
    }

    private boolean sendVerificationEmail(VerificationToken token) {
        try {
            emailService.sendEmail(
                    token.getEmail(),
                    "회원가입 이메일 인증 안내",
                    """
                            안녕하세요.
                            
                            MOFLIS 서비스 회원가입을 진행해주셔서 감사합니다.
                            
                            인증번호: [%s]
                            
                            만약 회원가입을 요청하지 않으셨다면 본 이메일은 무시하셔도 됩니다.
                            
                            감사합니다.
                            - MOFLIS 서비스팀 -
                            """.formatted(token)
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public void updateTokenStatus(VerificationToken token, boolean success) {
        token.setStatus(success ? TokenStatus.SENT : TokenStatus.FAILED);
        tokenRepository.save(token);
    }

}
