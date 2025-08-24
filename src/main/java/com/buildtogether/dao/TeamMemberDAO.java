package com.buildtogether.dao;

import com.buildtogether.pojo.TeamMember;
import java.util.List;

/**
 * Data Access Object interface for TeamMember entity
 */
public interface TeamMemberDAO {
    
    /**
     * Add a new team member to the database
     * @param teamMember The team member to add
     */
    void addTeamMember(TeamMember teamMember);
    
    /**
     * Update an existing team member in the database
     * @param teamMember The team member to update
     */
    void updateTeamMember(TeamMember teamMember);
    
    /**
     * Delete a team member from the database
     * @param teamMemberId The ID of the team member to delete
     */
    void deleteTeamMember(int teamMemberId);
    
    /**
     * Get a team member by its ID
     * @param teamMemberId The ID of the team member
     * @return The team member object, or null if not found
     */
    TeamMember getTeamMemberById(int teamMemberId);
    
    /**
     * Get all members of a specific team
     * @param teamId The ID of the team
     * @return List of team members for the specified team
     */
    List<TeamMember> getTeamMembersByTeamId(int teamId);
    
    /**
     * Get all teams that a specific user is a member of
     * @param userId The ID of the user
     * @return List of team memberships for the specified user
     */
    List<TeamMember> getTeamMembersByUserId(int userId);
    
    /**
     * Get all active team members from the database
     * @return List of all active team members
     */
    List<TeamMember> getAllActiveTeamMembers();
    
    /**
     * Check if a user is already a member of a specific team
     * @param userId The ID of the user
     * @param teamId The ID of the team
     * @return true if user is a member of the team, false otherwise
     */
    boolean userIsTeamMember(int userId, int teamId);
    
    /**
     * Get team members by role
     * @param teamId The ID of the team
     * @param role The role to filter by
     * @return List of team members with the specified role
     */
    List<TeamMember> getTeamMembersByRole(int teamId, String role);
    
    /**
     * Get the number of members in a team
     * @param teamId The ID of the team
     * @return The number of members in the team
     */
    int getTeamMemberCount(int teamId);
    
    /**
     * Remove a user from a team
     * @param userId The ID of the user
     * @param teamId The ID of the team
     */
    void removeUserFromTeam(int userId, int teamId);
    
    /**
     * Delete all team memberships for a specific team
     * @param teamId The ID of the team
     */
    void deleteAllTeamMembers(int teamId);
    
    /**
     * Delete all team memberships for a specific user
     * @param userId The ID of the user
     */
    void deleteAllUserTeamMemberships(int userId);
}
