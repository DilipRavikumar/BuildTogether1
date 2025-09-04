package com.buildtogether.controller;

import com.buildtogether.dto.TeamDTO;
import com.buildtogether.entity.Team;
import com.buildtogether.repository.TeamRepository;
import com.buildtogether.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
@Slf4j
public class TeamController {

    private final TeamRepository teamRepository;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        log.info("=== GET /teams - Fetching all teams ===");
        List<Team> teams = teamRepository.findAll();
        List<TeamDTO> teamDTOs = teams.stream()
                .map(TeamDTO::fromTeam)
                .collect(Collectors.toList());
        log.info("Successfully fetched {} teams", teamDTOs.size());
        return ResponseEntity.ok(teamDTOs);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id) {
        log.info("Fetching team with id: {}", id);
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));
        TeamDTO teamDTO = TeamDTO.fromTeam(team);
        return ResponseEntity.ok(teamDTO);
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@Valid @RequestBody Team team) {
        log.info("=== POST /teams - Creating new team ===");
        log.info("Team details: name={}, hackathonId={}, createdBy={}", 
                team.getTeamName(), team.getHackathon().getId(), team.getCreatedBy().getId());
        
        Team savedTeam = teamRepository.save(team);
        TeamDTO teamDTO = TeamDTO.fromTeam(savedTeam);
        log.info("Successfully created team: {} (ID: {})", teamDTO.getTeamName(), teamDTO.getId());
        return ResponseEntity.ok(teamDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Long id, @Valid @RequestBody Team teamDetails) {
        log.info("Updating team with id: {}", id);
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));
        
        team.setTeamName(teamDetails.getTeamName());
        team.setHackathon(teamDetails.getHackathon());
        team.setCreatedBy(teamDetails.getCreatedBy());
        
        Team updatedTeam = teamRepository.save(team);
        TeamDTO teamDTO = TeamDTO.fromTeam(updatedTeam);
        return ResponseEntity.ok(teamDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        log.info("Deleting team with id: {}", id);
        if (!teamRepository.existsById(id)) {
            throw new ResourceNotFoundException("Team", "id", id);
        }
        teamRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hackathon/{hackathonId}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<TeamDTO>> getTeamsByHackathon(@PathVariable Long hackathonId) {
        log.info("Fetching teams for hackathon: {}", hackathonId);
        List<Team> teams = teamRepository.findByHackathonId(hackathonId);
        List<TeamDTO> teamDTOs = teams.stream()
                .map(TeamDTO::fromTeam)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOs);
    }

    @GetMapping("/user/{userId}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<TeamDTO>> getTeamsByUser(@PathVariable Long userId) {
        log.info("Fetching teams for user: {}", userId);
        List<Team> teams = teamRepository.findByCreatedById(userId);
        List<TeamDTO> teamDTOs = teams.stream()
                .map(TeamDTO::fromTeam)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOs);
    }
}