package com.buildtogether.dao;

import com.buildtogether.pojo.Team;
import java.util.List;

/**
 * Data Access Object interface for Team entity
 */
public interface TeamDAO {
    
    /**
     * Add a new team to the database
     * @param team The team to add
     */
    void addTeam(Team team);
    
    /**
     * Update an existing team in the database
     * @param team The team to update
     */
    void updateTeam(Team team);
    
    /**
     * Delete a team from the database
     * @param teamId The ID of the team to delete
     */
    void deleteTeam(int teamId);
    
    /**
     * Get a team by its ID
     * @param teamId The ID of the team
     * @return The team object, or null if not found
     */
    Team getTeamById(int teamId);
    
    /**
     * Get a team by its name
     * @param teamName The name of the team
     * @return The team object, or null if not found
     */
    Team getTeamByName(String teamName);
    
    /**
     * Get all teams from the database
     * @return List of all teams
     */
    List<Team> getAllTeams();
    
    /**
     * Get all active teams from the database
     * @return List of active teams
     */
    List<Team> getActiveTeams();
    
    /**
     * Get teams led by a specific user
     * @param leaderId The ID of the team leader
     * @return List of teams led by the specified user
     */
    List<Team> getTeamsByLeaderId(int leaderId);
    
    /**
     * Search teams by name (partial match)
     * @param teamName The partial team name to search for
     * @return List of matching teams
     */
    List<Team> searchTeamsByName(String teamName);
    
    /**
     * Check if a team name already exists
     * @param teamName The team name to check
     * @return true if team name exists, false otherwise
     */
    boolean teamNameExists(String teamName);
    
    /**
     * Get teams with available spots (not full)
     * @return List of teams with available spots
     */
    List<Team> getTeamsWithAvailableSpots();
    
    /**
     * Get teams by hackathon ID
     * @param hackathonId The ID of the hackathon
     * @return List of teams for the specified hackathon
     */
    List<Team> getTeamsByHackathonId(int hackathonId);
    
    /**
     * Get team member count
     * @param teamId The ID of the team
     * @return Number of team members
     */
    int getTeamMemberCount(int teamId);
    
    /**
     * Check if team is full
     * @param teamId The ID of the team
     * @return true if team is full, false otherwise
     */
    boolean isTeamFull(int teamId);
}
