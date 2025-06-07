package com.project.moflis.emailverification.controller;

import com.project.moflis.emailverification.dto.EmailVerificationRequest;
import com.project.moflis.emailverification.service.EmailVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailVerificationController {

    private final EmailVerificationService verificationService;

    public EmailVerificationController(EmailVerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @PostMapping("/verify-request")
    public ResponseEntity<String> requestVerification(EmailVerificationRequest request) {
        verificationService.sendVerificationEmail(request.getEmail());
        return ResponseEntity.ok("인증 이메일이 발송되었습니다.");
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(String token) {
        boolean isVerify = verificationService.verifyToken(token);
        if (isVerify) {
            return ResponseEntity.ok("이메일 인증이 완료되었습니다.");
        }
        return ResponseEntity.badRequest().build();
    }
}
