package com.project.moflis.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "Profiles")
@ToString
public class Profiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 반드시 User 객체로 정의

    private String intro;

    @Column(name = "profile_image")
    private String profileImageName;

    @Column(name = "trust_score")
    private Float trustScore = 50f;

    @Column(name = "location_verified")
    private boolean locationVerified;
}