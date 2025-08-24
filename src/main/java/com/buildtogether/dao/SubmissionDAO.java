package com.buildtogether.dao;

import com.buildtogether.pojo.Submission;
import java.util.List;

/**
 * Data Access Object interface for Submission entity
 */
public interface SubmissionDAO {
    
    /**
     * Add a new submission
     * @param submission the submission to add
     * @return true if successful, false otherwise
     */
    boolean addSubmission(Submission submission);
    
    /**
     * Update an existing submission
     * @param submission the submission to update
     * @return true if successful, false otherwise
     */
    boolean updateSubmission(Submission submission);
    
    /**
     * Delete a submission by ID
     * @param submissionId the ID of the submission to delete
     * @return true if successful, false otherwise
     */
    boolean deleteSubmission(int submissionId);
    
    /**
     * Get a submission by ID
     * @param submissionId the ID of the submission
     * @return the submission or null if not found
     */
    Submission getSubmissionById(int submissionId);
    
    /**
     * Get all submissions
     * @return list of all submissions
     */
    List<Submission> getAllSubmissions();
    
    /**
     * Get submissions by team ID
     * @param teamId the team ID
     * @return list of submissions for the team
     */
    List<Submission> getSubmissionsByTeamId(int teamId);
    
    /**
     * Get submissions by hackathon ID
     * @param hackathonId the hackathon ID
     * @return list of submissions for the hackathon
     */
    List<Submission> getSubmissionsByHackathonId(int hackathonId);
    
    /**
     * Get submissions by status
     * @param status the status to filter by
     * @return list of submissions with the given status
     */
    List<Submission> getSubmissionsByStatus(String status);
    
    /**
     * Get submissions by score range
     * @param minScore minimum score
     * @param maxScore maximum score
     * @return list of submissions within the score range
     */
    List<Submission> getSubmissionsByScoreRange(double minScore, double maxScore);
    
    /**
     * Get top submissions by score
     * @param limit number of top submissions to return
     * @return list of top submissions
     */
    List<Submission> getTopSubmissions(int limit);
    
    /**
     * Check if a team has already submitted for a hackathon
     * @param teamId the team ID
     * @param hackathonId the hackathon ID
     * @return true if team has submitted, false otherwise
     */
    boolean teamHasSubmitted(int teamId, int hackathonId);
    
    /**
     * Update submission status
     * @param submissionId the submission ID
     * @param status the new status
     * @return true if successful, false otherwise
     */
    boolean updateSubmissionStatus(int submissionId, String status);
    
    /**
     * Update submission score and comments
     * @param submissionId the submission ID
     * @param score the score
     * @param comments the judge comments
     * @return true if successful, false otherwise
     */
    boolean updateSubmissionScore(int submissionId, double score, String comments);
    
    /**
     * Search submissions by project title
     * @param title the title to search for
     * @return list of matching submissions
     */
    List<Submission> searchSubmissionsByTitle(String title);
    
    /**
     * Get submissions by technologies used
     * @param technologies the technologies to search for
     * @return list of submissions using the specified technologies
     */
    List<Submission> getSubmissionsByTechnologies(String technologies);
    
    /**
     * Delete all submissions for a team
     * @param teamId the team ID
     * @return true if successful, false otherwise
     */
    boolean deleteAllSubmissionsForTeam(int teamId);
    
    /**
     * Delete all submissions for a hackathon
     * @param hackathonId the hackathon ID
     * @return true if successful, false otherwise
     */
    boolean deleteAllSubmissionsForHackathon(int hackathonId);
}
