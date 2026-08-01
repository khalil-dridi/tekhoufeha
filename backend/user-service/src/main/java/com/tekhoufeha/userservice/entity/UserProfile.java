package com.tekhoufeha.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;


    /**
     * Référence vers auth-service.auth_users.id
     * Pas de relation JPA car les bases sont séparées.
     */
    @Column(nullable = false)
    private UUID authUserId;


    private String firstName;

    private String lastName;


    private String phone;

    private String city;

    private String governorate;


    @Column(nullable = false)
    private String avatarUrl;


    private String avatarPublicId;


    private String bio;


    @Column(nullable = false)
    private boolean profileCompleted;


    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;



    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

    }



    @PreUpdate
    public void preUpdate() {

        updatedAt = LocalDateTime.now();

    }

}