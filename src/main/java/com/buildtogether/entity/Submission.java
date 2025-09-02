package com.buildtogether.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "dr_submission")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "submission_seq")
    @SequenceGenerator(name = "submission_seq", sequenceName = "submission_seq", allocationSize = 1)
    private Long id;

    @NotNull(message = "Team is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "submissions"})
    private Team team;

    @NotNull(message = "Hackathon is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hackathon_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "submissions"})
    private Hackathon hackathon;

    @NotBlank(message = "Project title is required")
    @Column(name = "project_title", nullable = false)
    private String projectTitle;

    @NotBlank(message = "Project description is required")
    @Column(name = "project_description", columnDefinition = "CLOB", nullable = false)
    private String projectDescription;

    @Column(name = "github_link")
    private String githubLink;

    @Column(name = "demo_link")
    private String demoLink;

    @Column(name = "presentation_link")
    private String presentationLink;

    @Column(columnDefinition = "CLOB")
    private String technologies;

    @Column(columnDefinition = "CLOB")
    private String features;

    @CreatedDate
    @Column(name = "submitted_at", nullable = false, updatable = false)
    private LocalDateTime submittedAt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR2(20) DEFAULT 'SUBMITTED'")
    private SubmissionStatus status = SubmissionStatus.SUBMITTED;

    @Column(precision = 5, scale = 2, columnDefinition = "DECIMAL(5,2) DEFAULT 0.00")
    private BigDecimal score = BigDecimal.ZERO;

    @Column(name = "judge_comments", columnDefinition = "CLOB")
    private String judgeComments;

    @NotNull(message = "Submitted by is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "submitted_by", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User submittedBy;

    public enum SubmissionStatus {
        SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED
    }

    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
    }
}
