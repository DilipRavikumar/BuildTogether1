package com.buildtogether.controller;

import com.buildtogether.dto.TeamMemberDTO;
import com.buildtogether.entity.TeamMember;
import com.buildtogether.entity.TeamMemberId;
import com.buildtogether.repository.TeamMemberRepository;
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
@RequestMapping("/api/v1/team-members")
@RequiredArgsConstructor
@Slf4j
public class TeamMemberController {

    private final TeamMemberRepository teamMemberRepository;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<TeamMemberDTO>> getAllTeamMembers() {
        log.info("Fetching all team members");
        List<TeamMember> teamMembers = teamMemberRepository.findAll();
        List<TeamMemberDTO> teamMemberDTOs = teamMembers.stream()
                .map(TeamMemberDTO::fromTeamMember)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teamMemberDTOs);
    }

    @GetMapping("/{teamId}/{userId}")
    @Transactional(readOnly = true)
    public ResponseEntity<TeamMemberDTO> getTeamMemberById(@PathVariable Long teamId, @PathVariable Long userId) {
        log.info("Fetching team member with teamId: {} and userId: {}", teamId, userId);
        TeamMemberId id = new TeamMemberId(teamId, userId);
        TeamMember teamMember = teamMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("TeamMember not found with teamId: %d and userId: %d", teamId, userId)));
        TeamMemberDTO teamMemberDTO = TeamMemberDTO.fromTeamMember(teamMember);
        return ResponseEntity.ok(teamMemberDTO);
    }

    @PostMapping
    public ResponseEntity<TeamMemberDTO> createTeamMember(@Valid @RequestBody TeamMember teamMember) {
        log.info("Creating new team member for team: {}", teamMember.getTeam().getId());
        TeamMember savedTeamMember = teamMemberRepository.save(teamMember);
        TeamMemberDTO teamMemberDTO = TeamMemberDTO.fromTeamMember(savedTeamMember);
        return ResponseEntity.ok(teamMemberDTO);
    }

    @PutMapping("/{teamId}/{userId}")
    public ResponseEntity<TeamMemberDTO> updateTeamMember(@PathVariable Long teamId, @PathVariable Long userId, @Valid @RequestBody TeamMember teamMemberDetails) {
        log.info("Updating team member with teamId: {} and userId: {}", teamId, userId);
        TeamMemberId id = new TeamMemberId(teamId, userId);
        TeamMember teamMember = teamMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("TeamMember not found with teamId: %d and userId: %d", teamId, userId)));
        
        teamMember.setRoleInTeam(teamMemberDetails.getRoleInTeam());
        
        TeamMember updatedTeamMember = teamMemberRepository.save(teamMember);
        TeamMemberDTO teamMemberDTO = TeamMemberDTO.fromTeamMember(updatedTeamMember);
        return ResponseEntity.ok(teamMemberDTO);
    }

    @DeleteMapping("/{teamId}/{userId}")
    @Transactional
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
    @Transactional(readOnly = true)
    public ResponseEntity<List<TeamMemberDTO>> getTeamMembersByTeam(@PathVariable Long teamId) {
        log.info("Fetching team members for team: {}", teamId);
        List<TeamMember> teamMembers = teamMemberRepository.findByTeamId(teamId);
        List<TeamMemberDTO> teamMemberDTOs = teamMembers.stream()
                .map(TeamMemberDTO::fromTeamMember)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teamMemberDTOs);
    }

    @GetMapping("/user/{userId}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<TeamMemberDTO>> getTeamMembersByUser(@PathVariable Long userId) {
        log.info("Fetching team memberships for user: {}", userId);
        List<TeamMember> teamMembers = teamMemberRepository.findByUserId(userId);
        List<TeamMemberDTO> teamMemberDTOs = teamMembers.stream()
                .map(TeamMemberDTO::fromTeamMember)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teamMemberDTOs);
    }

    @GetMapping("/check")
    @Transactional(readOnly = true)
    public ResponseEntity<Boolean> checkTeamMembership(@RequestParam Long teamId, @RequestParam Long userId) {
        log.info("Checking team membership for team: {} and user: {}", teamId, userId);
        boolean exists = teamMemberRepository.existsByTeamIdAndUserId(teamId, userId);
        return ResponseEntity.ok(exists);
    }
}