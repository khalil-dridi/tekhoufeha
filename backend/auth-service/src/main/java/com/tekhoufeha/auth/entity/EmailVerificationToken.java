package com.tekhoufeha.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "email_verification_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailVerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(nullable = false, unique = true)
    private String token;


    @Column(nullable = false)
    private Instant expiryDate;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "auth_user_id",
            nullable = false
    )
    private AuthUser authUser;
}