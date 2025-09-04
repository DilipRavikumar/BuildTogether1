package com.buildtogether.dto;

import com.buildtogether.entity.Submission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO {
    private Long id;
    private Long teamId;
    private String teamName;
    private Long hackathonId;
    private String hackathonTitle;
    private String projectTitle;
    private String projectDescription;
    private String githubLink;
    private String demoLink;
    private String presentationLink;
    private String technologies;
    private String features;
    private String status;
    private BigDecimal score;
    private String judgeComments;
    private LocalDateTime submittedAt;

    // Static factory method to convert from Submission entity
    public static SubmissionDTO fromSubmission(Submission submission) {
        SubmissionDTO dto = new SubmissionDTO();
        dto.setId(submission.getId());
        dto.setTeamId(submission.getTeam().getId());
        dto.setTeamName(submission.getTeam().getTeamName());
        dto.setHackathonId(submission.getHackathon().getId());
        dto.setHackathonTitle(submission.getHackathon().getTitle());
        dto.setProjectTitle(submission.getProjectTitle());
        dto.setProjectDescription(submission.getProjectDescription());
        dto.setGithubLink(submission.getGithubLink());
        dto.setDemoLink(submission.getDemoLink());
        dto.setPresentationLink(submission.getPresentationLink());
        dto.setTechnologies(submission.getTechnologies());
        dto.setFeatures(submission.getFeatures());
        dto.setStatus(submission.getStatus().name());
        dto.setScore(submission.getScore());
        dto.setJudgeComments(submission.getJudgeComments());
        dto.setSubmittedAt(submission.getSubmittedAt());
        return dto;
    }
}
