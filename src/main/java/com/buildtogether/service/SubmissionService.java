package com.buildtogether.service;

import com.buildtogether.dao.SubmissionDAO;
import com.buildtogether.dao.impl.SubmissionDAOImpl;
import com.buildtogether.pojo.Submission;

import java.util.List;

/**
 * Service layer for Submission operations
 */
public class SubmissionService {
    
    private SubmissionDAO submissionDAO;
    
    public SubmissionService() {
        this.submissionDAO = new SubmissionDAOImpl();
    }
    
    /**
     * Submit a new project
     * @param teamId the team ID
     * @param hackathonId the hackathon ID
     * @param projectTitle the project title
     * @param projectDescription the project description
     * @param githubLink the GitHub link
     * @param demoLink the demo link
     * @param presentationLink the presentation link
     * @param technologies the technologies used
     * @param features the features implemented
     * @return the created submission or null if failed
     */
    public Submission submitProject(int teamId, int hackathonId, String projectTitle, 
                                   String projectDescription, String githubLink, String demoLink, 
                                   String presentationLink, String technologies, String features) {
        
        // Validate inputs
        if (projectTitle == null || projectTitle.trim().isEmpty()) {
            System.out.println("Error: Project title cannot be empty");
            return null;
        }
        
        if (projectDescription == null || projectDescription.trim().isEmpty()) {
            System.out.println("Error: Project description cannot be empty");
            return null;
        }
        
        // Check if team has already submitted for this hackathon
        if (submissionDAO.teamHasSubmitted(teamId, hackathonId)) {
            System.out.println("Error: Team has already submitted a project for this hackathon");
            return null;
        }
        
        // Create submission
        Submission submission = new Submission(teamId, hackathonId, projectTitle, projectDescription,
                                              githubLink, demoLink, presentationLink, technologies, features);
        
        if (submissionDAO.addSubmission(submission)) {
            System.out.println("Project submitted successfully with ID: " + submission.getSubmissionId());
            return submission;
        } else {
            System.out.println("Error: Failed to submit project");
            return null;
        }
    }
    
    /**
     * Update an existing submission
     * @param submissionId the submission ID
     * @param projectTitle the new project title
     * @param projectDescription the new project description
     * @param githubLink the new GitHub link
     * @param demoLink the new demo link
     * @param presentationLink the new presentation link
     * @param technologies the new technologies
     * @param features the new features
     * @return true if successful, false otherwise
     */
    public boolean updateSubmission(int submissionId, String projectTitle, String projectDescription,
                                   String githubLink, String demoLink, String presentationLink,
                                   String technologies, String features) {
        
        Submission submission = submissionDAO.getSubmissionById(submissionId);
        if (submission == null) {
            System.out.println("Error: Submission not found");
            return false;
        }
        
        // Only allow updates if status is SUBMITTED
        if (!"SUBMITTED".equals(submission.getStatus())) {
            System.out.println("Error: Cannot update submission that is not in SUBMITTED status");
            return false;
        }
        
        // Update fields
        if (projectTitle != null && !projectTitle.trim().isEmpty()) {
            submission.setProjectTitle(projectTitle);
        }
        if (projectDescription != null && !projectDescription.trim().isEmpty()) {
            submission.setProjectDescription(projectDescription);
        }
        if (githubLink != null) {
            submission.setGithubLink(githubLink);
        }
        if (demoLink != null) {
            submission.setDemoLink(demoLink);
        }
        if (presentationLink != null) {
            submission.setPresentationLink(presentationLink);
        }
        if (technologies != null) {
            submission.setTechnologies(technologies);
        }
        if (features != null) {
            submission.setFeatures(features);
        }
        
        if (submissionDAO.updateSubmission(submission)) {
            System.out.println("Submission updated successfully");
            return true;
        } else {
            System.out.println("Error: Failed to update submission");
            return false;
        }
    }
    
    /**
     * Delete a submission
     * @param submissionId the submission ID
     * @return true if successful, false otherwise
     */
    public boolean deleteSubmission(int submissionId) {
        Submission submission = submissionDAO.getSubmissionById(submissionId);
        if (submission == null) {
            System.out.println("Error: Submission not found");
            return false;
        }
        
        // Only allow deletion if status is SUBMITTED
        if (!"SUBMITTED".equals(submission.getStatus())) {
            System.out.println("Error: Cannot delete submission that is not in SUBMITTED status");
            return false;
        }
        
        if (submissionDAO.deleteSubmission(submissionId)) {
            System.out.println("Submission deleted successfully");
            return true;
        } else {
            System.out.println("Error: Failed to delete submission");
            return false;
        }
    }
    
    /**
     * Get submission by ID
     * @param submissionId the submission ID
     * @return the submission or null if not found
     */
    public Submission getSubmissionById(int submissionId) {
        return submissionDAO.getSubmissionById(submissionId);
    }
    
    /**
     * Get all submissions
     * @return list of all submissions
     */
    public List<Submission> getAllSubmissions() {
        return submissionDAO.getAllSubmissions();
    }
    
    /**
     * Get submissions by team ID
     * @param teamId the team ID
     * @return list of submissions for the team
     */
    public List<Submission> getSubmissionsByTeamId(int teamId) {
        return submissionDAO.getSubmissionsByTeamId(teamId);
    }
    
    /**
     * Get submissions by hackathon ID
     * @param hackathonId the hackathon ID
     * @return list of submissions for the hackathon
     */
    public List<Submission> getSubmissionsByHackathonId(int hackathonId) {
        return submissionDAO.getSubmissionsByHackathonId(hackathonId);
    }
    
    /**
     * Get submissions by status
     * @param status the status to filter by
     * @return list of submissions with the given status
     */
    public List<Submission> getSubmissionsByStatus(String status) {
        return submissionDAO.getSubmissionsByStatus(status);
    }
    
    /**
     * Get top submissions
     * @param limit number of top submissions to return
     * @return list of top submissions
     */
    public List<Submission> getTopSubmissions(int limit) {
        return submissionDAO.getTopSubmissions(limit);
    }
    
    /**
     * Update submission status (for judges/admins)
     * @param submissionId the submission ID
     * @param status the new status
     * @return true if successful, false otherwise
     */
    public boolean updateSubmissionStatus(int submissionId, String status) {
        if (!isValidStatus(status)) {
            System.out.println("Error: Invalid status. Valid statuses are: SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED");
            return false;
        }
        
        if (submissionDAO.updateSubmissionStatus(submissionId, status)) {
            System.out.println("Submission status updated to: " + status);
            return true;
        } else {
            System.out.println("Error: Failed to update submission status");
            return false;
        }
    }
    
    /**
     * Update submission score and comments (for judges)
     * @param submissionId the submission ID
     * @param score the score (0-100)
     * @param comments the judge comments
     * @return true if successful, false otherwise
     */
    public boolean updateSubmissionScore(int submissionId, double score, String comments) {
        if (score < 0 || score > 100) {
            System.out.println("Error: Score must be between 0 and 100");
            return false;
        }
        
        if (submissionDAO.updateSubmissionScore(submissionId, score, comments)) {
            System.out.println("Submission score updated to: " + score);
            return true;
        } else {
            System.out.println("Error: Failed to update submission score");
            return false;
        }
    }
    
    /**
     * Search submissions by title
     * @param title the title to search for
     * @return list of matching submissions
     */
    public List<Submission> searchSubmissionsByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Error: Search title cannot be empty");
            return null;
        }
        return submissionDAO.searchSubmissionsByTitle(title);
    }
    
    /**
     * Get submissions by technologies
     * @param technologies the technologies to search for
     * @return list of submissions using the specified technologies
     */
    public List<Submission> getSubmissionsByTechnologies(String technologies) {
        if (technologies == null || technologies.trim().isEmpty()) {
            System.out.println("Error: Technologies cannot be empty");
            return null;
        }
        return submissionDAO.getSubmissionsByTechnologies(technologies);
    }
    
    /**
     * Check if team has submitted for a hackathon
     * @param teamId the team ID
     * @param hackathonId the hackathon ID
     * @return true if team has submitted, false otherwise
     */
    public boolean teamHasSubmitted(int teamId, int hackathonId) {
        return submissionDAO.teamHasSubmitted(teamId, hackathonId);
    }
    
    /**
     * Get submissions by score range
     * @param minScore minimum score
     * @param maxScore maximum score
     * @return list of submissions within the score range
     */
    public List<Submission> getSubmissionsByScoreRange(double minScore, double maxScore) {
        if (minScore < 0 || maxScore > 100 || minScore > maxScore) {
            System.out.println("Error: Invalid score range. Min score must be >= 0, max score must be <= 100, and min <= max");
            return null;
        }
        return submissionDAO.getSubmissionsByScoreRange(minScore, maxScore);
    }
    
    /**
     * Validate status
     * @param status the status to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidStatus(String status) {
        return "SUBMITTED".equals(status) || "UNDER_REVIEW".equals(status) || 
               "APPROVED".equals(status) || "REJECTED".equals(status);
    }
    
    /**
     * Display submission information
     * @param submission the submission to display
     */
    public void displaySubmissionInfo(Submission submission) {
        if (submission == null) {
            System.out.println("Submission not found.");
            return;
        }
        
        System.out.println("Submission ID: " + submission.getSubmissionId());
        System.out.println("Team ID: " + submission.getTeamId());
        System.out.println("Hackathon ID: " + submission.getHackathonId());
        System.out.println("Project Title: " + submission.getProjectTitle());
        System.out.println("Project Description: " + submission.getProjectDescription());
        System.out.println("GitHub Link: " + (submission.getGithubLink() != null ? submission.getGithubLink() : "N/A"));
        System.out.println("Demo Link: " + (submission.getDemoLink() != null ? submission.getDemoLink() : "N/A"));
        System.out.println("Presentation Link: " + (submission.getPresentationLink() != null ? submission.getPresentationLink() : "N/A"));
        System.out.println("Technologies: " + (submission.getTechnologies() != null ? submission.getTechnologies() : "N/A"));
        System.out.println("Features: " + (submission.getFeatures() != null ? submission.getFeatures() : "N/A"));
        System.out.println("Submitted At: " + submission.getSubmittedAt());
        System.out.println("Status: " + submission.getStatus());
        System.out.println("Score: " + submission.getScore());
        if (submission.getJudgeComments() != null && !submission.getJudgeComments().isEmpty()) {
            System.out.println("Judge Comments: " + submission.getJudgeComments());
        }
        System.out.println("---");
    }
}
