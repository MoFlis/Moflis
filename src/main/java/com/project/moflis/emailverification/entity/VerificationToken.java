package com.project.moflis.emailverification.entity;

import com.project.moflis.emailverification.enums.TokenStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "verification_token")
@Getter
@Setter
@NoArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String token;

    private LocalDateTime expiresAt;

    @Column
    @Enumerated(EnumType.STRING)
    private TokenStatus status;

    public VerificationToken(String email, String token, LocalDateTime expiresAt) {
        this.email = email;
        this.token = token;
        this.expiresAt = expiresAt;
        this.status = TokenStatus.PENDING;
    }
}
