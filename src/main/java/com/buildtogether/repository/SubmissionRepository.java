package com.buildtogether.repository;

import com.buildtogether.entity.Submission;
import com.buildtogether.entity.Submission.SubmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByTeamId(Long teamId);
    List<Submission> findByHackathonId(Long hackathonId);
    List<Submission> findByStatus(SubmissionStatus status);
    List<Submission> findByTeamIdAndHackathonId(Long teamId, Long hackathonId);
}
