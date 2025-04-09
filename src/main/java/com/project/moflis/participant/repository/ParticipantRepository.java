package com.project.moflis.participant.repository;

import com.project.moflis.participant.entity.Participant;
import com.project.moflis.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    int countByPostId(int postId);

    boolean existsByPostIdAndUserId(int postId, int userId);

    Optional<Participant> findByPostIdAndUserId(int postId, int userId);

    List<Participant> findByPostId(int postId);

    int user(User user);
}
