package com.buildtogether.service;

import com.buildtogether.dao.JoinRequestDAO;
import com.buildtogether.dao.impl.JoinRequestDAOImpl;
import com.buildtogether.pojo.JoinRequest;

import java.util.List;

/**
 * Service class for join request-related business logic
 */
public class JoinRequestService {
    
    private final JoinRequestDAO joinRequestDAO;
    
    public JoinRequestService() {
        this.joinRequestDAO = new JoinRequestDAOImpl();
    }
    
    /**
     * Create a new join request
     * @param joinRequest The join request to create
     * @return Success message or error message
     */
    public String createJoinRequest(JoinRequest joinRequest) {
        try {
            // Validate join request data
            String validationResult = validateJoinRequestData(joinRequest);
            if (!"OK".equals(validationResult)) {
                return validationResult;
            }
            
            // Check if user already has a pending request for this team
            if (joinRequestDAO.userHasPendingRequest(joinRequest.getUserId(), joinRequest.getTeamId())) {
                return "You already have a pending request for this team.";
            }
            
            // Set status to PENDING
            joinRequest.setStatus("PENDING");
            
            // Add join request to database
            joinRequestDAO.addJoinRequest(joinRequest);
            return "Join request created successfully with ID: " + joinRequest.getRequestId();
            
        } catch (Exception e) {
            System.err.println("Error creating join request: " + e.getMessage());
            return "Error creating join request: " + e.getMessage();
        }
    }
    
    /**
     * Update an existing join request
     * @param joinRequest The join request to update
     * @return Success message or error message
     */
    public String updateJoinRequest(JoinRequest joinRequest) {
        try {
            // Validate join request data
            String validationResult = validateJoinRequestData(joinRequest);
            if (!"OK".equals(validationResult)) {
                return validationResult;
            }
            
            // Check if join request exists
            JoinRequest existingRequest = joinRequestDAO.getJoinRequestById(joinRequest.getRequestId());
            if (existingRequest == null) {
                return "Join request not found with ID: " + joinRequest.getRequestId();
            }
            
            // Update join request
            joinRequestDAO.updateJoinRequest(joinRequest);
            return "Join request updated successfully";
            
        } catch (Exception e) {
            System.err.println("Error updating join request: " + e.getMessage());
            return "Error updating join request: " + e.getMessage();
        }
    }
    
    /**
     * Delete a join request
     * @param requestId The ID of the join request to delete
     * @return Success message or error message
     */
    public String deleteJoinRequest(int requestId) {
        try {
            // Check if join request exists
            JoinRequest joinRequest = joinRequestDAO.getJoinRequestById(requestId);
            if (joinRequest == null) {
                return "Join request not found with ID: " + requestId;
            }
            
            // Delete join request
            joinRequestDAO.deleteJoinRequest(requestId);
            return "Join request deleted successfully";
            
        } catch (Exception e) {
            System.err.println("Error deleting join request: " + e.getMessage());
            return "Error deleting join request: " + e.getMessage();
        }
    }
    
    /**
     * Get join request by ID
     * @param requestId The ID of the join request
     * @return JoinRequest object or null if not found
     */
    public JoinRequest getJoinRequestById(int requestId) {
        try {
            return joinRequestDAO.getJoinRequestById(requestId);
        } catch (Exception e) {
            System.err.println("Error getting join request by ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get all join requests for a specific team
     * @param teamId The ID of the team
     * @return List of join requests for the specified team
     */
    public List<JoinRequest> getJoinRequestsByTeamId(int teamId) {
        try {
            return joinRequestDAO.getJoinRequestsByTeamId(teamId);
        } catch (Exception e) {
            System.err.println("Error getting join requests by team ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get all join requests made by a specific user
     * @param userId The ID of the user
     * @return List of join requests made by the specified user
     */
    public List<JoinRequest> getJoinRequestsByUserId(int userId) {
        try {
            return joinRequestDAO.getJoinRequestsByUserId(userId);
        } catch (Exception e) {
            System.err.println("Error getting join requests by user ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get all pending join requests for a specific team
     * @param teamId The ID of the team
     * @return List of pending join requests for the specified team
     */
    public List<JoinRequest> getPendingJoinRequestsByTeamId(int teamId) {
        try {
            return joinRequestDAO.getPendingJoinRequestsByTeamId(teamId);
        } catch (Exception e) {
            System.err.println("Error getting pending join requests by team ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get all join requests by status
     * @param status The status to filter by (PENDING, APPROVED, REJECTED)
     * @return List of join requests with the specified status
     */
    public List<JoinRequest> getJoinRequestsByStatus(String status) {
        try {
            if (status == null || status.trim().isEmpty()) {
                return null;
            }
            return joinRequestDAO.getJoinRequestsByStatus(status.trim());
        } catch (Exception e) {
            System.err.println("Error getting join requests by status: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get all join requests
     * @return List of all join requests
     */
    public List<JoinRequest> getAllJoinRequests() {
        try {
            return joinRequestDAO.getAllJoinRequests();
        } catch (Exception e) {
            System.err.println("Error getting all join requests: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Check if user has a pending request for a specific team
     * @param userId The ID of the user
     * @param teamId The ID of the team
     * @return true if user has a pending request for the team, false otherwise
     */
    public boolean userHasPendingRequest(int userId, int teamId) {
        try {
            return joinRequestDAO.userHasPendingRequest(userId, teamId);
        } catch (Exception e) {
            System.err.println("Error checking if user has pending request: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Approve a join request
     * @param requestId The ID of the join request to approve
     * @return Success message or error message
     */
    public String approveJoinRequest(int requestId) {
        try {
            // Check if join request exists
            JoinRequest joinRequest = joinRequestDAO.getJoinRequestById(requestId);
            if (joinRequest == null) {
                return "Join request not found with ID: " + requestId;
            }
            
            // Check if request is already processed
            if (!"PENDING".equals(joinRequest.getStatus())) {
                return "Join request is already " + joinRequest.getStatus().toLowerCase();
            }
            
            // Approve join request
            joinRequestDAO.approveJoinRequest(requestId);
            return "Join request approved successfully";
            
        } catch (Exception e) {
            System.err.println("Error approving join request: " + e.getMessage());
            return "Error approving join request: " + e.getMessage();
        }
    }
    
    /**
     * Reject a join request
     * @param requestId The ID of the join request to reject
     * @return Success message or error message
     */
    public String rejectJoinRequest(int requestId) {
        try {
            // Check if join request exists
            JoinRequest joinRequest = joinRequestDAO.getJoinRequestById(requestId);
            if (joinRequest == null) {
                return "Join request not found with ID: " + requestId;
            }
            
            // Check if request is already processed
            if (!"PENDING".equals(joinRequest.getStatus())) {
                return "Join request is already " + joinRequest.getStatus().toLowerCase();
            }
            
            // Reject join request
            joinRequestDAO.rejectJoinRequest(requestId);
            return "Join request rejected successfully";
            
        } catch (Exception e) {
            System.err.println("Error rejecting join request: " + e.getMessage());
            return "Error rejecting join request: " + e.getMessage();
        }
    }
    
    /**
     * Delete all join requests for a specific team
     * @param teamId The ID of the team
     * @return Success message or error message
     */
    public String deleteAllJoinRequestsForTeam(int teamId) {
        try {
            joinRequestDAO.deleteAllJoinRequestsForTeam(teamId);
            return "All join requests deleted for team ID: " + teamId;
            
        } catch (Exception e) {
            System.err.println("Error deleting all join requests for team: " + e.getMessage());
            return "Error deleting all join requests for team: " + e.getMessage();
        }
    }
    
    /**
     * Delete all join requests made by a specific user
     * @param userId The ID of the user
     * @return Success message or error message
     */
    public String deleteAllJoinRequestsByUser(int userId) {
        try {
            joinRequestDAO.deleteAllJoinRequestsByUser(userId);
            return "All join requests deleted for user ID: " + userId;
            
        } catch (Exception e) {
            System.err.println("Error deleting all join requests by user: " + e.getMessage());
            return "Error deleting all join requests by user: " + e.getMessage();
        }
    }
    
    /**
     * Get pending join requests count for a team
     * @param teamId The ID of the team
     * @return Number of pending join requests
     */
    public int getPendingJoinRequestsCount(int teamId) {
        try {
            List<JoinRequest> pendingRequests = joinRequestDAO.getPendingJoinRequestsByTeamId(teamId);
            return pendingRequests != null ? pendingRequests.size() : 0;
        } catch (Exception e) {
            System.err.println("Error getting pending join requests count: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Validate join request data
     * @param joinRequest The join request to validate
     * @return "OK" if valid, error message otherwise
     */
    private String validateJoinRequestData(JoinRequest joinRequest) {
        if (joinRequest == null) {
            return "Join request object cannot be null";
        }
        
        if (joinRequest.getTeamId() <= 0) {
            return "Team ID must be valid";
        }
        
        if (joinRequest.getUserId() <= 0) {
            return "User ID must be valid";
        }
        
        if (joinRequest.getStatus() != null && 
            !joinRequest.getStatus().matches("PENDING|APPROVED|REJECTED")) {
            return "Status must be PENDING, APPROVED, or REJECTED";
        }
        
        return "OK";
    }
}
