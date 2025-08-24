package com.buildtogether.pojo;

import java.util.Date;

public class Submission {
    private int submissionId;
    private int teamId;
    private int hackathonId;
    private String projectTitle;
    private String projectDescription;
    private String githubLink;
    private String demoLink;
    private String presentationLink;
    private String technologies;
    private String features;
    private Date submittedAt;
    private String status;
    private double score;
    private String judgeComments;

    public Submission() {
    }

    public Submission(int teamId, int hackathonId, String projectTitle, String projectDescription, 
                     String githubLink, String demoLink, String presentationLink, String technologies, String features) {
        this.teamId = teamId;
        this.hackathonId = hackathonId;
        this.projectTitle = projectTitle;
        this.projectDescription = projectDescription;
        this.githubLink = githubLink;
        this.demoLink = demoLink;
        this.presentationLink = presentationLink;
        this.technologies = technologies;
        this.features = features;
        this.submittedAt = new Date();
        this.status = "SUBMITTED";
        this.score = 0.0;
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getHackathonId() {
        return hackathonId;
    }

    public void setHackathonId(int hackathonId) {
        this.hackathonId = hackathonId;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }

    public String getDemoLink() {
        return demoLink;
    }

    public void setDemoLink(String demoLink) {
        this.demoLink = demoLink;
    }

    public String getPresentationLink() {
        return presentationLink;
    }

    public void setPresentationLink(String presentationLink) {
        this.presentationLink = presentationLink;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public Date getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Date submittedAt) {
        this.submittedAt = submittedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getJudgeComments() {
        return judgeComments;
    }

    public void setJudgeComments(String judgeComments) {
        this.judgeComments = judgeComments;
    }

    @Override
    public String toString() {
        return "Submission{" +
                "submissionId=" + submissionId +
                ", teamId=" + teamId +
                ", hackathonId=" + hackathonId +
                ", projectTitle='" + projectTitle + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                ", githubLink='" + githubLink + '\'' +
                ", demoLink='" + demoLink + '\'' +
                ", presentationLink='" + presentationLink + '\'' +
                ", technologies='" + technologies + '\'' +
                ", features='" + features + '\'' +
                ", submittedAt=" + submittedAt +
                ", status='" + status + '\'' +
                ", score=" + score +
                ", judgeComments='" + judgeComments + '\'' +
                '}';
    }
}
