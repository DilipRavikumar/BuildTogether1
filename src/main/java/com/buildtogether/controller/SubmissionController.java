package com.buildtogether.controller;

import com.buildtogether.entity.Submission;
import com.buildtogether.repository.SubmissionRepository;
import com.buildtogether.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/submissions")
@RequiredArgsConstructor
@Slf4j
public class SubmissionController {

    private final SubmissionRepository submissionRepository;

    @GetMapping
    public ResponseEntity<List<Submission>> getAllSubmissions() {
        log.info("Fetching all submissions");
        List<Submission> submissions = submissionRepository.findAll();
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(@PathVariable Long id) {
        log.info("Fetching submission with id: {}", id);
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Submission", "id", id));
        return ResponseEntity.ok(submission);
    }

    @PostMapping
    public ResponseEntity<Submission> createSubmission(@Valid @RequestBody Submission submission) {
        log.info("Creating new submission for team: {} and hackathon: {}", 
                submission.getTeam().getId(), submission.getHackathon().getId());
        Submission savedSubmission = submissionRepository.save(submission);
        return ResponseEntity.ok(savedSubmission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Submission> updateSubmission(@PathVariable Long id, @Valid @RequestBody Submission submissionDetails) {
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
        return ResponseEntity.ok(updatedSubmission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long id) {
        log.info("Deleting submission with id: {}", id);
        if (!submissionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Submission", "id", id);
        }
        submissionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Submission>> getSubmissionsByTeam(@PathVariable Long teamId) {
        log.info("Fetching submissions for team: {}", teamId);
        List<Submission> submissions = submissionRepository.findByTeamId(teamId);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/hackathon/{hackathonId}")
    public ResponseEntity<List<Submission>> getSubmissionsByHackathon(@PathVariable Long hackathonId) {
        log.info("Fetching submissions for hackathon: {}", hackathonId);
        List<Submission> submissions = submissionRepository.findByHackathonId(hackathonId);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Submission>> getSubmissionsByStatus(@PathVariable String status) {
        log.info("Fetching submissions with status: {}", status);
        Submission.SubmissionStatus submissionStatus = Submission.SubmissionStatus.valueOf(status.toUpperCase());
        List<Submission> submissions = submissionRepository.findByStatus(submissionStatus);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/team-hackathon")
    public ResponseEntity<List<Submission>> getSubmissionsByTeamAndHackathon(
            @RequestParam Long teamId, @RequestParam Long hackathonId) {
        log.info("Fetching submissions for team: {} and hackathon: {}", teamId, hackathonId);
        List<Submission> submissions = submissionRepository.findByTeamIdAndHackathonId(teamId, hackathonId);
        return ResponseEntity.ok(submissions);
    }

    @PutMapping("/{id}/score")
    public ResponseEntity<Submission> updateSubmissionScore(
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
        return ResponseEntity.ok(updatedSubmission);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Submission> updateSubmissionStatus(
            @PathVariable Long id, 
            @RequestParam String status) {
        log.info("Updating status for submission: {} to: {}", id, status);
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Submission", "id", id));
        
        Submission.SubmissionStatus submissionStatus = Submission.SubmissionStatus.valueOf(status.toUpperCase());
        submission.setStatus(submissionStatus);
        
        Submission updatedSubmission = submissionRepository.save(submission);
        return ResponseEntity.ok(updatedSubmission);
    }
}
