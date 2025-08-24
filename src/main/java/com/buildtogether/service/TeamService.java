package com.buildtogether.service;

import com.buildtogether.dao.TeamDAO;
import com.buildtogether.dao.impl.TeamDAOImpl;
import com.buildtogether.pojo.Team;

import java.util.List;

/**
 * Service class for team-related business logic
 */
public class TeamService {
    
    private final TeamDAO teamDAO;
    
    public TeamService() {
        this.teamDAO = new TeamDAOImpl();
    }
    
    /**
     * Create a new team
     * @param team The team to create
     * @return Success message or error message
     */
    public String createTeam(Team team) {
        try {
            // Validate team data
            String validationResult = validateTeamData(team);
            if (!"OK".equals(validationResult)) {
                return validationResult;
            }
            
            // Check if team name already exists for this hackathon
            if (teamDAO.teamNameExists(team.getTeamName())) {
                return "Team with this name already exists. Please use a different team name.";
            }
            
            // Add team to database
            teamDAO.addTeam(team);
            return "Team created successfully with ID: " + team.getTeamId();
            
        } catch (Exception e) {
            System.err.println("Error creating team: " + e.getMessage());
            return "Error creating team: " + e.getMessage();
        }
    }
    
    /**
     * Update an existing team
     * @param team The team to update
     * @return Success message or error message
     */
    public String updateTeam(Team team) {
        try {
            // Validate team data
            String validationResult = validateTeamData(team);
            if (!"OK".equals(validationResult)) {
                return validationResult;
            }
            
            // Check if team exists
            Team existingTeam = teamDAO.getTeamById(team.getTeamId());
            if (existingTeam == null) {
                return "Team not found with ID: " + team.getTeamId();
            }
            
            // Check if team name is being changed and if it already exists
            if (!existingTeam.getTeamName().equals(team.getTeamName()) && 
                teamDAO.teamNameExists(team.getTeamName())) {
                return "Team with this name already exists. Please use a different team name.";
            }
            
            // Update team
            teamDAO.updateTeam(team);
            return "Team updated successfully";
            
        } catch (Exception e) {
            System.err.println("Error updating team: " + e.getMessage());
            return "Error updating team: " + e.getMessage();
        }
    }
    
    /**
     * Delete a team
     * @param teamId The ID of the team to delete
     * @return Success message or error message
     */
    public String deleteTeam(int teamId) {
        try {
            // Check if team exists
            Team team = teamDAO.getTeamById(teamId);
            if (team == null) {
                return "Team not found with ID: " + teamId;
            }
            
            // Delete team
            teamDAO.deleteTeam(teamId);
            return "Team deleted successfully";
            
        } catch (Exception e) {
            System.err.println("Error deleting team: " + e.getMessage());
            return "Error deleting team: " + e.getMessage();
        }
    }
    
    /**
     * Get team by ID
     * @param teamId The ID of the team
     * @return Team object or null if not found
     */
    public Team getTeamById(int teamId) {
        try {
            return teamDAO.getTeamById(teamId);
        } catch (Exception e) {
            System.err.println("Error getting team by ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get team by name
     * @param teamName The name of the team
     * @return Team object or null if not found
     */
    public Team getTeamByName(String teamName) {
        try {
            if (teamName == null || teamName.trim().isEmpty()) {
                return null;
            }
            return teamDAO.getTeamByName(teamName.trim());
        } catch (Exception e) {
            System.err.println("Error getting team by name: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get all teams
     * @return List of all teams
     */
    public List<Team> getAllTeams() {
        try {
            return teamDAO.getAllTeams();
        } catch (Exception e) {
            System.err.println("Error getting all teams: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get active teams
     * @return List of active teams
     */
    public List<Team> getActiveTeams() {
        try {
            return teamDAO.getActiveTeams();
        } catch (Exception e) {
            System.err.println("Error getting active teams: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get teams led by a specific user
     * @param leaderId The ID of the team leader
     * @return List of teams led by the specified user
     */
    public List<Team> getTeamsByLeaderId(int leaderId) {
        try {
            return teamDAO.getTeamsByLeaderId(leaderId);
        } catch (Exception e) {
            System.err.println("Error getting teams by leader ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Search teams by name
     * @param teamName The partial team name to search for
     * @return List of matching teams
     */
    public List<Team> searchTeamsByName(String teamName) {
        try {
            if (teamName == null || teamName.trim().isEmpty()) {
                return null;
            }
            return teamDAO.searchTeamsByName(teamName.trim());
        } catch (Exception e) {
            System.err.println("Error searching teams by name: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get teams with available spots
     * @return List of teams with available spots
     */
    public List<Team> getTeamsWithAvailableSpots() {
        try {
            return teamDAO.getTeamsWithAvailableSpots();
        } catch (Exception e) {
            System.err.println("Error getting teams with available spots: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get teams by hackathon ID
     * @param hackathonId The ID of the hackathon
     * @return List of teams for the specified hackathon
     */
    public List<Team> getTeamsByHackathonId(int hackathonId) {
        try {
            return teamDAO.getTeamsByHackathonId(hackathonId);
        } catch (Exception e) {
            System.err.println("Error getting teams by hackathon ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get team member count
     * @param teamId The ID of the team
     * @return Number of team members
     */
    public int getTeamMemberCount(int teamId) {
        try {
            return teamDAO.getTeamMemberCount(teamId);
        } catch (Exception e) {
            System.err.println("Error getting team member count: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Check if team is full
     * @param teamId The ID of the team
     * @return true if team is full, false otherwise
     */
    public boolean isTeamFull(int teamId) {
        try {
            return teamDAO.isTeamFull(teamId);
        } catch (Exception e) {
            System.err.println("Error checking if team is full: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get available spots in a team
     * @param teamId The ID of the team
     * @return Number of available spots
     */
    public int getAvailableSpots(int teamId) {
        try {
            Team team = teamDAO.getTeamById(teamId);
            if (team == null) {
                return 0;
            }
            
            // This would need to be implemented with hackathon info
            // For now, we'll return a default value
            int currentMembers = teamDAO.getTeamMemberCount(teamId);
            int maxTeamSize = 5; // Default max team size
            
            return Math.max(0, maxTeamSize - currentMembers);
        } catch (Exception e) {
            System.err.println("Error getting available spots: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Validate team data
     * @param team The team to validate
     * @return "OK" if valid, error message otherwise
     */
    private String validateTeamData(Team team) {
        if (team == null) {
            return "Team object cannot be null";
        }
        
        if (team.getTeamName() == null || team.getTeamName().trim().isEmpty()) {
            return "Team name cannot be empty";
        }
        
        if (team.getTeamName().length() < 2 || team.getTeamName().length() > 100) {
            return "Team name must be between 2 and 100 characters";
        }
        
        if (team.getHackathonId() <= 0) {
            return "Hackathon ID must be valid";
        }
        
        if (team.getCreatedBy() <= 0) {
            return "Created by user ID must be valid";
        }
        
        return "OK";
    }
}
