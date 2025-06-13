package com.project.moflis.emailverification.service;

import com.project.moflis.emailverification.entity.VerificationToken;
import com.project.moflis.emailverification.enums.TokenStatus;
import com.project.moflis.emailverification.repository.VerificationTokenRepository;
import com.project.moflis.emailverification.util.TokenGenerator;
import com.project.moflis.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EmailVerificationService {

    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;
    private final UserService userService;
    private final TokenGenerator tokenGenerator;

    public EmailVerificationService(VerificationTokenRepository tokenRepository, EmailService emailService, UserService userService, TokenGenerator tokenGenerator) {
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.userService = userService;
        this.tokenGenerator = tokenGenerator;
    }

    public void sendVerificationEmail(String email) {

        String token = tokenGenerator.generateToken(); // uuid를 token으로 쓰는게 명확한가?
        // 숫자 생성하는 컴포넌트 만들고 동시요청이 들어왔을떄 같은 값이 생성이 안되게
        // 난수 기반으로 숫자를 만들어야함 비결정적 난수
        // 1. 이메일 발송 실패하면 생성한 토큰도 롤백하는 방향
        // 2. 토큰 발송 상태를 추가하고 이메일 발송실패했을때 발송상태를 보고 재시도를 처리하게 (스케줄러, 리트라이 주기) 고민해보야할듯

        VerificationToken verificationToken = new VerificationToken(
                email,
                token,
                LocalDateTime.now().plusMinutes(10)
        );

        try {

            emailService.sendEmail(
                    email,
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
            verificationToken.setStatus(TokenStatus.SENT);
        } catch (Exception e) {
            verificationToken.setStatus(TokenStatus.FAILED);
            e.printStackTrace();
        }
        tokenRepository.save(verificationToken);
    }

    @Transactional
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
