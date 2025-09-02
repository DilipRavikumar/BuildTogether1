package com.buildtogether.controller;

import com.buildtogether.entity.TeamMember;
import com.buildtogether.entity.TeamMemberId;
import com.buildtogether.repository.TeamMemberRepository;
import com.buildtogether.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/team-members")
@RequiredArgsConstructor
@Slf4j
public class TeamMemberController {

    private final TeamMemberRepository teamMemberRepository;

    @GetMapping
    public ResponseEntity<List<TeamMember>> getAllTeamMembers() {
        log.info("Fetching all team members");
        List<TeamMember> teamMembers = teamMemberRepository.findAll();
        return ResponseEntity.ok(teamMembers);
    }

    @GetMapping("/{teamId}/{userId}")
    public ResponseEntity<TeamMember> getTeamMemberById(@PathVariable Long teamId, @PathVariable Long userId) {
        log.info("Fetching team member with teamId: {} and userId: {}", teamId, userId);
        TeamMemberId id = new TeamMemberId(teamId, userId);
        TeamMember teamMember = teamMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("TeamMember not found with teamId: %d and userId: %d", teamId, userId)));
        return ResponseEntity.ok(teamMember);
    }

    @PostMapping
    public ResponseEntity<TeamMember> createTeamMember(@Valid @RequestBody TeamMember teamMember) {
        log.info("Creating new team member for team: {}", teamMember.getTeam().getId());
        TeamMember savedTeamMember = teamMemberRepository.save(teamMember);
        return ResponseEntity.ok(savedTeamMember);
    }

    @PutMapping("/{teamId}/{userId}")
    public ResponseEntity<TeamMember> updateTeamMember(@PathVariable Long teamId, @PathVariable Long userId, @Valid @RequestBody TeamMember teamMemberDetails) {
        log.info("Updating team member with teamId: {} and userId: {}", teamId, userId);
        TeamMemberId id = new TeamMemberId(teamId, userId);
        TeamMember teamMember = teamMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("TeamMember not found with teamId: %d and userId: %d", teamId, userId)));
        
        teamMember.setRoleInTeam(teamMemberDetails.getRoleInTeam());
        
        TeamMember updatedTeamMember = teamMemberRepository.save(teamMember);
        return ResponseEntity.ok(updatedTeamMember);
    }

    @DeleteMapping("/{teamId}/{userId}")
    public ResponseEntity<Void> deleteTeamMember(@PathVariable Long teamId, @PathVariable Long userId) {
        log.info("Deleting team member with teamId: {} and userId: {}", teamId, userId);
        TeamMemberId id = new TeamMemberId(teamId, userId);
        if (!teamMemberRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("TeamMember not found with teamId: %d and userId: %d", teamId, userId));
        }
        teamMemberRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<TeamMember>> getTeamMembersByTeam(@PathVariable Long teamId) {
        log.info("Fetching team members for team: {}", teamId);
        List<TeamMember> teamMembers = teamMemberRepository.findByTeamId(teamId);
        return ResponseEntity.ok(teamMembers);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TeamMember>> getTeamMembersByUser(@PathVariable Long userId) {
        log.info("Fetching team memberships for user: {}", userId);
        List<TeamMember> teamMembers = teamMemberRepository.findByUserId(userId);
        return ResponseEntity.ok(teamMembers);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkTeamMembership(@RequestParam Long teamId, @RequestParam Long userId) {
        log.info("Checking team membership for team: {} and user: {}", teamId, userId);
        boolean exists = teamMemberRepository.existsByTeamIdAndUserId(teamId, userId);
        return ResponseEntity.ok(exists);
    }
}