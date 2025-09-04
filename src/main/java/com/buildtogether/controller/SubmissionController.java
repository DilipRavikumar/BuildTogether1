package com.buildtogether.controller;

import com.buildtogether.dto.SubmissionDTO;
import com.buildtogether.entity.Submission;
import com.buildtogether.repository.SubmissionRepository;
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
@RequestMapping("/api/v1/submissions")
@RequiredArgsConstructor
@Slf4j
public class SubmissionController {

    private final SubmissionRepository submissionRepository;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<SubmissionDTO>> getAllSubmissions() {
        log.info("Fetching all submissions");
        List<Submission> submissions = submissionRepository.findAll();
        List<SubmissionDTO> submissionDTOs = submissions.stream()
                .map(SubmissionDTO::fromSubmission)
                .collect(Collectors.toList());
        return ResponseEntity.ok(submissionDTOs);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<SubmissionDTO> getSubmissionById(@PathVariable Long id) {
        log.info("Fetching submission with id: {}", id);
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Submission", "id", id));
        SubmissionDTO submissionDTO = SubmissionDTO.fromSubmission(submission);
        return ResponseEntity.ok(submissionDTO);
    }

    @PostMapping
    public ResponseEntity<SubmissionDTO> createSubmission(@Valid @RequestBody Submission submission) {
        log.info("Creating new submission for team: {} and hackathon: {}", 
                submission.getTeam().getId(), submission.getHackathon().getId());
        Submission savedSubmission = submissionRepository.save(submission);
        SubmissionDTO submissionDTO = SubmissionDTO.fromSubmission(savedSubmission);
        return ResponseEntity.ok(submissionDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubmissionDTO> updateSubmission(@PathVariable Long id, @Valid @RequestBody Submission submissionDetails) {
        log.info("Updating submission with id: {}", id);
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Submission", "id", id));
        
        submission.setProjectTitle(submissionDetails.getProjectTitle());
        submission.setProjectDescription(submissionDetails.getProjectDescription());
        submission.setGithubLink(submissionDetails.getGithubLink());
        submission.setDemoLink(submissionDetails.getDemoLink());
        submission.setPresentationLink(submissionDetails.getPresentationLink());
        submission.setTechnologies(submissionDetails.getTechnologies());
        submission.setFeatures(submissionDetails.getFeatures());
        submission.setStatus(submissionDetails.getStatus());
        submission.setScore(submissionDetails.getScore());
        submission.setJudgeComments(submissionDetails.getJudgeComments());
        
        Submission updatedSubmission = submissionRepository.save(submission);
        SubmissionDTO submissionDTO = SubmissionDTO.fromSubmission(updatedSubmission);
        return ResponseEntity.ok(submissionDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long id) {
        log.info("Deleting submission with id: {}", id);
        if (!submissionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Submission", "id", id);
        }
        submissionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/team/{teamId}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByTeam(@PathVariable Long teamId) {
        log.info("Fetching submissions for team: {}", teamId);
        List<Submission> submissions = submissionRepository.findByTeamId(teamId);
        List<SubmissionDTO> submissionDTOs = submissions.stream()
                .map(SubmissionDTO::fromSubmission)
                .collect(Collectors.toList());
        return ResponseEntity.ok(submissionDTOs);
    }

    @GetMapping("/hackathon/{hackathonId}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByHackathon(@PathVariable Long hackathonId) {
        log.info("Fetching submissions for hackathon: {}", hackathonId);
        List<Submission> submissions = submissionRepository.findByHackathonId(hackathonId);
        List<SubmissionDTO> submissionDTOs = submissions.stream()
                .map(SubmissionDTO::fromSubmission)
                .collect(Collectors.toList());
        return ResponseEntity.ok(submissionDTOs);
    }

    @GetMapping("/status/{status}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByStatus(@PathVariable String status) {
        log.info("Fetching submissions with status: {}", status);
        try {
            Submission.SubmissionStatus submissionStatus = Submission.SubmissionStatus.valueOf(status.toUpperCase());
            List<Submission> submissions = submissionRepository.findByStatus(submissionStatus);
            List<SubmissionDTO> submissionDTOs = submissions.stream()
                    .map(SubmissionDTO::fromSubmission)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(submissionDTOs);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid status value: " + status + ". Valid statuses are: SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED");
        }
    }

    @GetMapping("/team-hackathon")
    @Transactional(readOnly = true)
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByTeamAndHackathon(
            @RequestParam Long teamId, @RequestParam Long hackathonId) {
        log.info("Fetching submissions for team: {} and hackathon: {}", teamId, hackathonId);
        List<Submission> submissions = submissionRepository.findByTeamIdAndHackathonId(teamId, hackathonId);
        List<SubmissionDTO> submissionDTOs = submissions.stream()
                .map(SubmissionDTO::fromSubmission)
                .collect(Collectors.toList());
        return ResponseEntity.ok(submissionDTOs);
    }

    @PutMapping("/{id}/score")
    public ResponseEntity<SubmissionDTO> updateSubmissionScore(
            @PathVariable Long id, 
            @RequestParam Double score, 
            @RequestParam(required = false) String judgeComments) {
        log.info("Updating score for submission: {} to: {}", id, score);
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Submission", "id", id));
        
        submission.setScore(java.math.BigDecimal.valueOf(score));
        if (judgeComments != null) {
            submission.setJudgeComments(judgeComments);
        }
        
        Submission updatedSubmission = submissionRepository.save(submission);
        SubmissionDTO submissionDTO = SubmissionDTO.fromSubmission(updatedSubmission);
        return ResponseEntity.ok(submissionDTO);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<SubmissionDTO> updateSubmissionStatus(
            @PathVariable Long id, 
            @RequestParam String status) {
        log.info("Updating status for submission: {} to: {}", id, status);
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Submission", "id", id));
        
        Submission.SubmissionStatus submissionStatus = Submission.SubmissionStatus.valueOf(status.toUpperCase());
        submission.setStatus(submissionStatus);
        
        Submission updatedSubmission = submissionRepository.save(submission);
        SubmissionDTO submissionDTO = SubmissionDTO.fromSubmission(updatedSubmission);
        return ResponseEntity.ok(submissionDTO);
    }
}
