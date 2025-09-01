package com.buildtogether.controller;

import com.buildtogether.entity.Team;
import com.buildtogether.repository.TeamRepository;
import com.buildtogether.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class TeamController {

    private final TeamRepository teamRepository;

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        log.info("=== GET /teams - Fetching all teams ===");
        try {
            List<Team> teams = teamRepository.findAll();
            log.info("Successfully fetched {} teams", teams.size());
            return ResponseEntity.ok(teams);
        } catch (Exception e) {
            log.error("Error fetching all teams: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        log.info("Fetching team with id: {}", id);
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));
        return ResponseEntity.ok(team);
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@Valid @RequestBody Team team) {
        log.info("=== POST /teams - Creating new team ===");
        log.info("Team details: name={}, hackathonId={}, createdBy={}", 
                team.getTeamName(), team.getHackathon().getId(), team.getCreatedBy().getId());
        
        try {
            Team savedTeam = teamRepository.save(team);
            log.info("Successfully created team: {} (ID: {})", savedTeam.getTeamName(), savedTeam.getId());
            return ResponseEntity.ok(savedTeam);
        } catch (Exception e) {
            log.error("Error creating team {}: {}", team.getTeamName(), e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @Valid @RequestBody Team teamDetails) {
        log.info("Updating team with id: {}", id);
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));
        
        team.setTeamName(teamDetails.getTeamName());
        team.setHackathon(teamDetails.getHackathon());
        team.setCreatedBy(teamDetails.getCreatedBy());
        
        Team updatedTeam = teamRepository.save(team);
        return ResponseEntity.ok(updatedTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        log.info("Deleting team with id: {}", id);
        if (!teamRepository.existsById(id)) {
            throw new ResourceNotFoundException("Team", "id", id);
        }
        teamRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hackathon/{hackathonId}")
    public ResponseEntity<List<Team>> getTeamsByHackathon(@PathVariable Long hackathonId) {
        log.info("Fetching teams for hackathon: {}", hackathonId);
        List<Team> teams = teamRepository.findByHackathonId(hackathonId);
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Team>> getTeamsByUser(@PathVariable Long userId) {
        log.info("Fetching teams for user: {}", userId);
        List<Team> teams = teamRepository.findByCreatedById(userId);
        return ResponseEntity.ok(teams);
    }
}