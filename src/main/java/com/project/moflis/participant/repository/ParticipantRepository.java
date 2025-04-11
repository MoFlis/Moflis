package com.project.moflis.participant.repository;

import com.project.moflis.participant.entity.Participant;
import com.project.moflis.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    int countByPostId(long postId);

    boolean existsByPostIdAndUserId(long postId, long userId);

    Optional<Participant> findByPostIdAndUserId(long postId, long userId);

    List<Participant> findByPostId(long postId);

    long user(User user);
}
