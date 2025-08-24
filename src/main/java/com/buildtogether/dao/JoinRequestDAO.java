package com.buildtogether.dao;

import com.buildtogether.pojo.JoinRequest;
import java.util.List;

/**
 * Data Access Object interface for JoinRequest entity
 */
public interface JoinRequestDAO {
    
    /**
     * Add a new join request to the database
     * @param joinRequest The join request to add
     */
    void addJoinRequest(JoinRequest joinRequest);
    
    /**
     * Update an existing join request in the database
     * @param joinRequest The join request to update
     */
    void updateJoinRequest(JoinRequest joinRequest);
    
    /**
     * Delete a join request from the database
     * @param requestId The ID of the join request to delete
     */
    void deleteJoinRequest(int requestId);
    
    /**
     * Get a join request by its ID
     * @param requestId The ID of the join request
     * @return The join request object, or null if not found
     */
    JoinRequest getJoinRequestById(int requestId);
    
    /**
     * Get all join requests for a specific team
     * @param teamId The ID of the team
     * @return List of join requests for the specified team
     */
    List<JoinRequest> getJoinRequestsByTeamId(int teamId);
    
    /**
     * Get all join requests made by a specific user
     * @param userId The ID of the user
     * @return List of join requests made by the specified user
     */
    List<JoinRequest> getJoinRequestsByUserId(int teamId);
    
    /**
     * Get all pending join requests for a specific team
     * @param teamId The ID of the team
     * @return List of pending join requests for the specified team
     */
    List<JoinRequest> getPendingJoinRequestsByTeamId(int teamId);
    
    /**
     * Get all join requests by status
     * @param status The status to filter by (PENDING, APPROVED, REJECTED)
     * @return List of join requests with the specified status
     */
    List<JoinRequest> getJoinRequestsByStatus(String status);
    
    /**
     * Get all join requests from the database
     * @return List of all join requests
     */
    List<JoinRequest> getAllJoinRequests();
    
    /**
     * Check if a user has a pending join request for a specific team
     * @param userId The ID of the user
     * @param teamId The ID of the team
     * @return true if user has a pending request for the team, false otherwise
     */
    boolean userHasPendingRequest(int userId, int teamId);
    
    /**
     * Approve a join request
     * @param requestId The ID of the join request to approve
     */
    void approveJoinRequest(int requestId);
    
    /**
     * Reject a join request
     * @param requestId The ID of the join request to reject
     */
    void rejectJoinRequest(int requestId);
    
    /**
     * Delete all join requests for a specific team
     * @param teamId The ID of the team
     */
    void deleteAllJoinRequestsForTeam(int teamId);
    
    /**
     * Delete all join requests made by a specific user
     * @param userId The ID of the user
     */
    void deleteAllJoinRequestsByUser(int userId);
}
