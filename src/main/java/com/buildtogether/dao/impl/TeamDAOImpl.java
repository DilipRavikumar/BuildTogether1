package com.buildtogether.dao.impl;

import com.buildtogether.dao.TeamDAO;
import com.buildtogether.pojo.Team;
import com.buildtogether.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of TeamDAO for MySQL database
 */
public class TeamDAOImpl implements TeamDAO {
    
    private final DBConnection dbConnection;
    
    public TeamDAOImpl() {
        this.dbConnection = DBConnection.getInstance();
    }
    
    @Override
    public void addTeam(Team team) {
        String sql = "INSERT INTO team (hackathon_id, created_by, team_name) VALUES (?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, team.getHackathonId());
            pstmt.setInt(2, team.getCreatedBy());
            pstmt.setString(3, team.getTeamName());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        team.setTeamId(rs.getInt(1));
                    }
                }
                dbConnection.commit();
                System.out.println("Team added successfully with ID: " + team.getTeamId());
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error adding team: " + e.getMessage());
            throw new RuntimeException("Failed to add team", e);
        }
    }
    
    @Override
    public void updateTeam(Team team) {
        String sql = "UPDATE team SET hackathon_id = ?, created_by = ?, team_name = ? WHERE team_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, team.getHackathonId());
            pstmt.setInt(2, team.getCreatedBy());
            pstmt.setString(3, team.getTeamName());
            pstmt.setInt(4, team.getTeamId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("Team updated successfully");
            } else {
                System.out.println("No team found with ID: " + team.getTeamId());
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error updating team: " + e.getMessage());
            throw new RuntimeException("Failed to update team", e);
        }
    }
    
    @Override
    public void deleteTeam(int teamId) {
        String sql = "DELETE FROM team WHERE team_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, teamId);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("Team deleted successfully");
            } else {
                System.out.println("No team found with ID: " + teamId);
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error deleting team: " + e.getMessage());
            throw new RuntimeException("Failed to delete team", e);
        }
    }
    
    @Override
    public Team getTeamById(int teamId) {
        String sql = "SELECT * FROM team WHERE team_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, teamId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTeam(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting team by ID: " + e.getMessage());
            throw new RuntimeException("Failed to get team by ID", e);
        }
        
        return null;
    }
    
    @Override
    public Team getTeamByName(String teamName) {
        String sql = "SELECT * FROM team WHERE team_name = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, teamName);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTeam(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting team by name: " + e.getMessage());
            throw new RuntimeException("Failed to get team by name", e);
        }
        
        return null;
    }
    
    @Override
    public List<Team> getAllTeams() {
        String sql = "SELECT * FROM team ORDER BY team_id";
        List<Team> teams = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                teams.add(mapResultSetToTeam(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all teams: " + e.getMessage());
            throw new RuntimeException("Failed to get all teams", e);
        }
        
        return teams;
    }
    
    @Override
    public List<Team> getActiveTeams() {
        String sql = "SELECT t.* FROM team t " +
                    "JOIN hackathon h ON t.hackathon_id = h.hackathon_id " +
                    "WHERE h.end_date >= SYSDATE " +
                    "ORDER BY t.team_id";
        List<Team> teams = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                teams.add(mapResultSetToTeam(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting active teams: " + e.getMessage());
            throw new RuntimeException("Failed to get active teams", e);
        }
        
        return teams;
    }
    
    @Override
    public List<Team> getTeamsByLeaderId(int leaderId) {
        String sql = "SELECT * FROM team WHERE created_by = ? ORDER BY team_id";
        List<Team> teams = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, leaderId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    teams.add(mapResultSetToTeam(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting teams by leader ID: " + e.getMessage());
            throw new RuntimeException("Failed to get teams by leader ID", e);
        }
        
        return teams;
    }
    
    @Override
    public List<Team> searchTeamsByName(String teamName) {
        String sql = "SELECT * FROM team WHERE LOWER(team_name) LIKE ? ORDER BY team_id";
        List<Team> teams = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + teamName.toLowerCase() + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    teams.add(mapResultSetToTeam(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching teams by name: " + e.getMessage());
            throw new RuntimeException("Failed to search teams by name", e);
        }
        
        return teams;
    }
    
    @Override
    public boolean teamNameExists(String teamName) {
        String sql = "SELECT COUNT(*) FROM team WHERE team_name = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, teamName);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking team name existence: " + e.getMessage());
            throw new RuntimeException("Failed to check team name existence", e);
        }
        
        return false;
    }
    
    @Override
    public List<Team> getTeamsWithAvailableSpots() {
        String sql = "SELECT t.*, h.max_team_size, COUNT(tm.user_id) as current_members " +
                    "FROM team t " +
                    "JOIN hackathon h ON t.hackathon_id = h.hackathon_id " +
                    "LEFT JOIN team_member tm ON t.team_id = tm.team_id " +
                    "WHERE h.end_date >= SYSDATE " +
                    "GROUP BY t.team_id " +
                    "HAVING current_members < h.max_team_size " +
                    "ORDER BY t.team_id";
        List<Team> teams = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Team team = mapResultSetToTeam(rs);
                // You could add additional info about available spots if needed
                teams.add(team);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting teams with available spots: " + e.getMessage());
            throw new RuntimeException("Failed to get teams with available spots", e);
        }
        
        return teams;
    }
    
    @Override
    public List<Team> getTeamsByHackathonId(int hackathonId) {
        String sql = "SELECT * FROM team WHERE hackathon_id = ? ORDER BY team_id";
        List<Team> teams = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, hackathonId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    teams.add(mapResultSetToTeam(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting teams by hackathon ID: " + e.getMessage());
            throw new RuntimeException("Failed to get teams by hackathon ID", e);
        }
        
        return teams;
    }
    
    @Override
    public int getTeamMemberCount(int teamId) {
        String sql = "SELECT COUNT(*) FROM team_member WHERE team_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, teamId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting team member count: " + e.getMessage());
            throw new RuntimeException("Failed to get team member count", e);
        }
        
        return 0;
    }
    
    @Override
    public boolean isTeamFull(int teamId) {
        String sql = "SELECT h.max_team_size, COUNT(tm.user_id) as current_members " +
                    "FROM team t " +
                    "JOIN hackathon h ON t.hackathon_id = h.hackathon_id " +
                    "LEFT JOIN team_member tm ON t.team_id = tm.team_id " +
                    "WHERE t.team_id = ? " +
                    "GROUP BY t.team_id, h.max_team_size";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, teamId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int maxTeamSize = rs.getInt("max_team_size");
                    int currentMembers = rs.getInt("current_members");
                    return currentMembers >= maxTeamSize;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking if team is full: " + e.getMessage());
            throw new RuntimeException("Failed to check if team is full", e);
        }
        
        return false;
    }
    
    /**
     * Map ResultSet to Team object
     * @param rs ResultSet containing team data
     * @return Team object
     * @throws SQLException if mapping fails
     */
    private Team mapResultSetToTeam(ResultSet rs) throws SQLException {
        Team team = new Team();
        team.setTeamId(rs.getInt("team_id"));
        team.setHackathonId(rs.getInt("hackathon_id"));
        team.setCreatedBy(rs.getInt("created_by"));
        team.setTeamName(rs.getString("team_name"));
        return team;
    }
}
