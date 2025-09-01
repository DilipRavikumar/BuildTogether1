package com.buildtogether.repository;

import com.buildtogether.entity.JoinRequest;
import com.buildtogether.entity.JoinRequest.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    List<JoinRequest> findByTeamId(Long teamId);
    List<JoinRequest> findByUserId(Long userId);
    List<JoinRequest> findByStatus(RequestStatus status);
    Optional<JoinRequest> findByTeamIdAndUserId(Long teamId, Long userId);
    boolean existsByTeamIdAndUserId(Long teamId, Long userId);
}
