package com.buildtogether.controller;

import com.buildtogether.dto.JoinRequestDTO;
import com.buildtogether.entity.JoinRequest;
import com.buildtogether.repository.JoinRequestRepository;
import com.buildtogether.exception.ResourceNotFoundException;
import com.buildtogether.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/join-requests")
@RequiredArgsConstructor
@Slf4j
public class JoinRequestController {

    private final JoinRequestRepository joinRequestRepository;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<JoinRequestDTO>> getAllJoinRequests() {
        log.info("Fetching all join requests");
        List<JoinRequest> joinRequests = joinRequestRepository.findAll();
        List<JoinRequestDTO> joinRequestDTOs = joinRequests.stream()
                .map(JoinRequestDTO::fromJoinRequest)
                .collect(Collectors.toList());
        return ResponseEntity.ok(joinRequestDTOs);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<JoinRequestDTO> getJoinRequestById(@PathVariable Long id) {
        log.info("Fetching join request with id: {}", id);
        JoinRequest joinRequest = joinRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JoinRequest", "id", id));
        JoinRequestDTO joinRequestDTO = JoinRequestDTO.fromJoinRequest(joinRequest);
        return ResponseEntity.ok(joinRequestDTO);
    }

    @PostMapping
    public ResponseEntity<JoinRequestDTO> createJoinRequest(@Valid @RequestBody JoinRequest joinRequest) {
        log.info("Creating new join request for team: {} and user: {}", 
                joinRequest.getTeam().getId(), joinRequest.getUser().getId());
        JoinRequest savedJoinRequest = joinRequestRepository.save(joinRequest);
        JoinRequestDTO joinRequestDTO = JoinRequestDTO.fromJoinRequest(savedJoinRequest);
        return ResponseEntity.ok(joinRequestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JoinRequestDTO> updateJoinRequest(@PathVariable Long id, @Valid @RequestBody JoinRequest joinRequestDetails) {
        log.info("Updating join request with id: {}", id);
        JoinRequest joinRequest = joinRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JoinRequest", "id", id));
        
        joinRequest.setStatus(joinRequestDetails.getStatus());
        
        JoinRequest updatedJoinRequest = joinRequestRepository.save(joinRequest);
        JoinRequestDTO joinRequestDTO = JoinRequestDTO.fromJoinRequest(updatedJoinRequest);
        return ResponseEntity.ok(joinRequestDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteJoinRequest(@PathVariable Long id) {
        log.info("Deleting join request with id: {}", id);
        if (!joinRequestRepository.existsById(id)) {
            throw new ResourceNotFoundException("JoinRequest", "id", id);
        }
        joinRequestRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/team/{teamId}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<JoinRequestDTO>> getJoinRequestsByTeam(@PathVariable Long teamId) {
        log.info("Fetching join requests for team: {}", teamId);
        List<JoinRequest> joinRequests = joinRequestRepository.findByTeamId(teamId);
        List<JoinRequestDTO> joinRequestDTOs = joinRequests.stream()
                .map(JoinRequestDTO::fromJoinRequest)
                .collect(Collectors.toList());
        return ResponseEntity.ok(joinRequestDTOs);
    }

    @GetMapping("/user/{userId}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<JoinRequestDTO>> getJoinRequestsByUser(@PathVariable Long userId) {
        log.info("Fetching join requests for user: {}", userId);
        List<JoinRequest> joinRequests = joinRequestRepository.findByUserId(userId);
        List<JoinRequestDTO> joinRequestDTOs = joinRequests.stream()
                .map(JoinRequestDTO::fromJoinRequest)
                .collect(Collectors.toList());
        return ResponseEntity.ok(joinRequestDTOs);
    }

    @GetMapping("/status/{status}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<JoinRequestDTO>> getJoinRequestsByStatus(@PathVariable String status) {
        log.info("Fetching join requests with status: {}", status);
        try {
            JoinRequest.RequestStatus requestStatus = JoinRequest.RequestStatus.valueOf(status.toUpperCase());
            List<JoinRequest> joinRequests = joinRequestRepository.findByStatus(requestStatus);
            List<JoinRequestDTO> joinRequestDTOs = joinRequests.stream()
                    .map(JoinRequestDTO::fromJoinRequest)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(joinRequestDTOs);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid status value provided: {}", status);
            throw new ValidationException("Invalid status value: " + status + ". Valid statuses are: PENDING, APPROVED, REJECTED");
        }
    }

    @GetMapping("/check")
    @Transactional(readOnly = true)
    public ResponseEntity<Boolean> checkJoinRequestExists(@RequestParam Long teamId, @RequestParam Long userId) {
        log.info("Checking if join request exists for team: {} and user: {}", teamId, userId);
        boolean exists = joinRequestRepository.existsByTeamIdAndUserId(teamId, userId);
        return ResponseEntity.ok(exists);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<JoinRequestDTO> approveJoinRequest(@PathVariable Long id) {
        log.info("Approving join request with id: {}", id);
        JoinRequest joinRequest = joinRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JoinRequest", "id", id));
        
        joinRequest.setStatus(JoinRequest.RequestStatus.APPROVED);
        JoinRequest updatedJoinRequest = joinRequestRepository.save(joinRequest);
        JoinRequestDTO joinRequestDTO = JoinRequestDTO.fromJoinRequest(updatedJoinRequest);
        return ResponseEntity.ok(joinRequestDTO);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<JoinRequestDTO> rejectJoinRequest(@PathVariable Long id) {
        log.info("Rejecting join request with id: {}", id);
        JoinRequest joinRequest = joinRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JoinRequest", "id", id));
        
        joinRequest.setStatus(JoinRequest.RequestStatus.REJECTED);
        JoinRequest updatedJoinRequest = joinRequestRepository.save(joinRequest);
        JoinRequestDTO joinRequestDTO = JoinRequestDTO.fromJoinRequest(updatedJoinRequest);
        return ResponseEntity.ok(joinRequestDTO);
    }
}