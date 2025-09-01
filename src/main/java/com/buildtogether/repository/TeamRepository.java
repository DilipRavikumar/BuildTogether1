package com.buildtogether.repository;

import com.buildtogether.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByHackathonId(Long hackathonId);
    List<Team> findByCreatedById(Long userId);
    List<Team> findByTeamNameContainingIgnoreCase(String teamName);
}
