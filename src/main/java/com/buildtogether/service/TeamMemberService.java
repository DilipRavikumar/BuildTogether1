package com.buildtogether.service;

import com.buildtogether.dao.TeamMemberDAO;
import com.buildtogether.dao.impl.TeamMemberDAOImpl;
import com.buildtogether.pojo.TeamMember;

import java.util.List;

/**
 * Service class for team member-related business logic
 */
public class TeamMemberService {
    
    private final TeamMemberDAO teamMemberDAO;
    
    public TeamMemberService() {
        this.teamMemberDAO = new TeamMemberDAOImpl();
    }
    
    /**
     * Add a new team member
     * @param teamMember The team member to add
     * @return Success message or error message
     */
    public String addTeamMember(TeamMember teamMember) {
        try {
            // Validate team member data
            String validationResult = validateTeamMemberData(teamMember);
            if (!"OK".equals(validationResult)) {
                return validationResult;
            }
            
            // Check if user is already a member of this team
            if (teamMemberDAO.userIsTeamMember(teamMember.getUserId(), teamMember.getTeamId())) {
                return "User is already a member of this team.";
            }
            
            // Add team member to database
            teamMemberDAO.addTeamMember(teamMember);
            return "Team member added successfully";
            
        } catch (Exception e) {
            System.err.println("Error adding team member: " + e.getMessage());
            return "Error adding team member: " + e.getMessage();
        }
    }
    
    /**
     * Update an existing team member
     * @param teamMember The team member to update
     * @return Success message or error message
     */
    public String updateTeamMember(TeamMember teamMember) {
        try {
            // Validate team member data
            String validationResult = validateTeamMemberData(teamMember);
            if (!"OK".equals(validationResult)) {
                return validationResult;
            }
            
            // Check if team member exists
            if (!teamMemberDAO.userIsTeamMember(teamMember.getUserId(), teamMember.getTeamId())) {
                return "Team member not found with user ID: " + teamMember.getUserId() + 
                       " and team ID: " + teamMember.getTeamId();
            }
            
            // Update team member
            teamMemberDAO.updateTeamMember(teamMember);
            return "Team member updated successfully";
            
        } catch (Exception e) {
            System.err.println("Error updating team member: " + e.getMessage());
            return "Error updating team member: " + e.getMessage();
        }
    }
    
    /**
     * Remove a user from a team
     * @param userId The ID of the user
     * @param teamId The ID of the team
     * @return Success message or error message
     */
    public String removeUserFromTeam(int userId, int teamId) {
        try {
            // Check if user is a member of this team
            if (!teamMemberDAO.userIsTeamMember(userId, teamId)) {
                return "User is not a member of this team.";
            }
            
            // Remove user from team
            teamMemberDAO.removeUserFromTeam(userId, teamId);
            return "User removed from team successfully";
            
        } catch (Exception e) {
            System.err.println("Error removing user from team: " + e.getMessage());
            return "Error removing user from team: " + e.getMessage();
        }
    }
    
    /**
     * Get all members of a specific team
     * @param teamId The ID of the team
     * @return List of team members for the specified team
     */
    public List<TeamMember> getTeamMembersByTeamId(int teamId) {
        try {
            return teamMemberDAO.getTeamMembersByTeamId(teamId);
        } catch (Exception e) {
            System.err.println("Error getting team members by team ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get all teams that a specific user is a member of
     * @param userId The ID of the user
     * @return List of team memberships for the specified user
     */
    public List<TeamMember> getTeamMembersByUserId(int userId) {
        try {
            return teamMemberDAO.getTeamMembersByUserId(userId);
        } catch (Exception e) {
            System.err.println("Error getting team members by user ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get all active team members
     * @return List of all active team members
     */
    public List<TeamMember> getAllActiveTeamMembers() {
        try {
            return teamMemberDAO.getAllActiveTeamMembers();
        } catch (Exception e) {
            System.err.println("Error getting all active team members: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Check if a user is a member of a specific team
     * @param userId The ID of the user
     * @param teamId The ID of the team
     * @return true if user is a member of the team, false otherwise
     */
    public boolean userIsTeamMember(int userId, int teamId) {
        try {
            return teamMemberDAO.userIsTeamMember(userId, teamId);
        } catch (Exception e) {
            System.err.println("Error checking if user is team member: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get team members by role
     * @param teamId The ID of the team
     * @param role The role to filter by
     * @return List of team members with the specified role
     */
    public List<TeamMember> getTeamMembersByRole(int teamId, String role) {
        try {
            if (role == null || role.trim().isEmpty()) {
                return null;
            }
            return teamMemberDAO.getTeamMembersByRole(teamId, role.trim());
        } catch (Exception e) {
            System.err.println("Error getting team members by role: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Delete all team members for a specific team
     * @param teamId The ID of the team
     * @return Success message or error message
     */
    public String deleteAllTeamMembers(int teamId) {
        try {
            teamMemberDAO.deleteAllTeamMembers(teamId);
            return "All team members deleted for team ID: " + teamId;
            
        } catch (Exception e) {
            System.err.println("Error deleting all team members: " + e.getMessage());
            return "Error deleting all team members: " + e.getMessage();
        }
    }
    
    /**
     * Delete all team memberships for a specific user
     * @param userId The ID of the user
     * @return Success message or error message
     */
    public String deleteAllUserTeamMemberships(int userId) {
        try {
            teamMemberDAO.deleteAllUserTeamMemberships(userId);
            return "All team memberships deleted for user ID: " + userId;
            
        } catch (Exception e) {
            System.err.println("Error deleting all user team memberships: " + e.getMessage());
            return "Error deleting all user team memberships: " + e.getMessage();
        }
    }
    
    /**
     * Get team member count for a team
     * @param teamId The ID of the team
     * @return Number of team members
     */
    public int getTeamMemberCount(int teamId) {
        try {
            List<TeamMember> teamMembers = teamMemberDAO.getTeamMembersByTeamId(teamId);
            return teamMembers != null ? teamMembers.size() : 0;
        } catch (Exception e) {
            System.err.println("Error getting team member count: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Get user's team count
     * @param userId The ID of the user
     * @return Number of teams the user is a member of
     */
    public int getUserTeamCount(int userId) {
        try {
            List<TeamMember> teamMemberships = teamMemberDAO.getTeamMembersByUserId(userId);
            return teamMemberships != null ? teamMemberships.size() : 0;
        } catch (Exception e) {
            System.err.println("Error getting user team count: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Validate team member data
     * @param teamMember The team member to validate
     * @return "OK" if valid, error message otherwise
     */
    private String validateTeamMemberData(TeamMember teamMember) {
        if (teamMember == null) {
            return "Team member object cannot be null";
        }
        
        if (teamMember.getTeamId() <= 0) {
            return "Team ID must be valid";
        }
        
        if (teamMember.getUserId() <= 0) {
            return "User ID must be valid";
        }
        
        if (teamMember.getRoleInTeam() == null || teamMember.getRoleInTeam().trim().isEmpty()) {
            return "Role in team cannot be empty";
        }
        
        if (teamMember.getRoleInTeam().length() > 50) {
            return "Role in team must be 50 characters or less";
        }
        
        return "OK";
    }
}
