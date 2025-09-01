package com.buildtogether.controller;

import com.buildtogether.entity.JoinRequest;
import com.buildtogether.repository.JoinRequestRepository;
import com.buildtogether.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/join-requests")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class JoinRequestController {

    private final JoinRequestRepository joinRequestRepository;

    @GetMapping
    public ResponseEntity<List<JoinRequest>> getAllJoinRequests() {
        log.info("Fetching all join requests");
        List<JoinRequest> joinRequests = joinRequestRepository.findAll();
        return ResponseEntity.ok(joinRequests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JoinRequest> getJoinRequestById(@PathVariable Long id) {
        log.info("Fetching join request with id: {}", id);
        JoinRequest joinRequest = joinRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JoinRequest", "id", id));
        return ResponseEntity.ok(joinRequest);
    }

    @PostMapping
    public ResponseEntity<JoinRequest> createJoinRequest(@Valid @RequestBody JoinRequest joinRequest) {
        log.info("Creating new join request for team: {} and user: {}", 
                joinRequest.getTeam().getId(), joinRequest.getUser().getId());
        JoinRequest savedJoinRequest = joinRequestRepository.save(joinRequest);
        return ResponseEntity.ok(savedJoinRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JoinRequest> updateJoinRequest(@PathVariable Long id, @Valid @RequestBody JoinRequest joinRequestDetails) {
        log.info("Updating join request with id: {}", id);
        JoinRequest joinRequest = joinRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JoinRequest", "id", id));
        
        joinRequest.setStatus(joinRequestDetails.getStatus());
        
        JoinRequest updatedJoinRequest = joinRequestRepository.save(joinRequest);
        return ResponseEntity.ok(updatedJoinRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJoinRequest(@PathVariable Long id) {
        log.info("Deleting join request with id: {}", id);
        if (!joinRequestRepository.existsById(id)) {
            throw new ResourceNotFoundException("JoinRequest", "id", id);
        }
        joinRequestRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<JoinRequest>> getJoinRequestsByTeam(@PathVariable Long teamId) {
        log.info("Fetching join requests for team: {}", teamId);
        List<JoinRequest> joinRequests = joinRequestRepository.findByTeamId(teamId);
        return ResponseEntity.ok(joinRequests);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<JoinRequest>> getJoinRequestsByUser(@PathVariable Long userId) {
        log.info("Fetching join requests for user: {}", userId);
        List<JoinRequest> joinRequests = joinRequestRepository.findByUserId(userId);
        return ResponseEntity.ok(joinRequests);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<JoinRequest>> getJoinRequestsByStatus(@PathVariable String status) {
        log.info("Fetching join requests with status: {}", status);
        try {
            JoinRequest.RequestStatus requestStatus = JoinRequest.RequestStatus.valueOf(status.toUpperCase());
            List<JoinRequest> joinRequests = joinRequestRepository.findByStatus(requestStatus);
            return ResponseEntity.ok(joinRequests);
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("Invalid status: " + status);
        }
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkJoinRequestExists(@RequestParam Long teamId, @RequestParam Long userId) {
        log.info("Checking if join request exists for team: {} and user: {}", teamId, userId);
        boolean exists = joinRequestRepository.existsByTeamIdAndUserId(teamId, userId);
        return ResponseEntity.ok(exists);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<JoinRequest> approveJoinRequest(@PathVariable Long id) {
        log.info("Approving join request with id: {}", id);
        JoinRequest joinRequest = joinRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JoinRequest", "id", id));
        
        joinRequest.setStatus(JoinRequest.RequestStatus.APPROVED);
        JoinRequest updatedJoinRequest = joinRequestRepository.save(joinRequest);
        return ResponseEntity.ok(updatedJoinRequest);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<JoinRequest> rejectJoinRequest(@PathVariable Long id) {
        log.info("Rejecting join request with id: {}", id);
        JoinRequest joinRequest = joinRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JoinRequest", "id", id));
        
        joinRequest.setStatus(JoinRequest.RequestStatus.REJECTED);
        JoinRequest updatedJoinRequest = joinRequestRepository.save(joinRequest);
        return ResponseEntity.ok(updatedJoinRequest);
    }
}