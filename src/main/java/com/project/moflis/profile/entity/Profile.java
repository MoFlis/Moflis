package com.project.moflis.profile.entity;

import com.project.moflis.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 반드시 User 객체로 정의

    private String intro;

    @Column(name = "profile_image")
    private String profileImageName;

    @Column(insertable = false, name = "trust_score")
    private Float trustScore;

}