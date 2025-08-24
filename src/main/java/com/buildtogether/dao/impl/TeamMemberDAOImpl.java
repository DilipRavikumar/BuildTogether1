package com.buildtogether.dao.impl;

import com.buildtogether.dao.TeamMemberDAO;
import com.buildtogether.pojo.TeamMember;
import com.buildtogether.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of TeamMemberDAO for MySQL database
 */
public class TeamMemberDAOImpl implements TeamMemberDAO {
    
    private final DBConnection dbConnection;
    
    public TeamMemberDAOImpl() {
        this.dbConnection = DBConnection.getInstance();
    }
    
    @Override
    public void addTeamMember(TeamMember teamMember) {
        String sql = "INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, teamMember.getTeamId());
            pstmt.setInt(2, teamMember.getUserId());
            pstmt.setString(3, teamMember.getRoleInTeam());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("Team member added successfully");
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error adding team member: " + e.getMessage());
            throw new RuntimeException("Failed to add team member", e);
        }
    }
    
    @Override
    public void updateTeamMember(TeamMember teamMember) {
        String sql = "UPDATE team_member SET role_in_team = ? WHERE team_id = ? AND user_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, teamMember.getRoleInTeam());
            pstmt.setInt(2, teamMember.getTeamId());
            pstmt.setInt(3, teamMember.getUserId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("Team member updated successfully");
            } else {
                System.out.println("No team member found with team ID: " + teamMember.getTeamId() + 
                                 " and user ID: " + teamMember.getUserId());
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error updating team member: " + e.getMessage());
            throw new RuntimeException("Failed to update team member", e);
        }
    }
    
    @Override
    public void deleteTeamMember(int teamMemberId) {
        // Since team_member has composite primary key, we need team_id and user_id
        // This method signature might need to be updated in the interface
        String sql = "DELETE FROM team_member WHERE team_id = ? AND user_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // For now, we'll use teamMemberId as team_id and assume user_id is 0
            // This should be updated to take both parameters
            pstmt.setInt(1, teamMemberId);
            pstmt.setInt(2, 0); // This is a placeholder
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("Team member deleted successfully");
            } else {
                System.out.println("No team member found");
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error deleting team member: " + e.getMessage());
            throw new RuntimeException("Failed to delete team member", e);
        }
    }
    
    @Override
    public TeamMember getTeamMemberById(int teamMemberId) {
        // Since team_member has composite primary key, this method might need to be updated
        // For now, we'll return null as this doesn't make sense with composite key
        return null;
    }
    
    @Override
    public List<TeamMember> getTeamMembersByTeamId(int teamId) {
        String sql = "SELECT * FROM team_member WHERE team_id = ? ORDER BY user_id";
        List<TeamMember> teamMembers = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, teamId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    teamMembers.add(mapResultSetToTeamMember(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting team members by team ID: " + e.getMessage());
            throw new RuntimeException("Failed to get team members by team ID", e);
        }
        
        return teamMembers;
    }
    
    @Override
    public List<TeamMember> getTeamMembersByUserId(int userId) {
        String sql = "SELECT * FROM team_member WHERE user_id = ? ORDER BY team_id";
        List<TeamMember> teamMembers = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    teamMembers.add(mapResultSetToTeamMember(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting team members by user ID: " + e.getMessage());
            throw new RuntimeException("Failed to get team members by user ID", e);
        }
        
        return teamMembers;
    }
    
    @Override
    public List<TeamMember> getAllActiveTeamMembers() {
        String sql = "SELECT tm.* FROM team_member tm " +
                    "JOIN team t ON tm.team_id = t.team_id " +
                    "JOIN hackathon h ON t.hackathon_id = h.hackathon_id " +
                    "WHERE h.end_date >= SYSDATE " +
                    "ORDER BY tm.team_id, tm.user_id";
        List<TeamMember> teamMembers = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                teamMembers.add(mapResultSetToTeamMember(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all active team members: " + e.getMessage());
            throw new RuntimeException("Failed to get all active team members", e);
        }
        
        return teamMembers;
    }
    
    @Override
    public boolean userIsTeamMember(int userId, int teamId) {
        String sql = "SELECT COUNT(*) FROM team_member WHERE user_id = ? AND team_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            pstmt.setInt(2, teamId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking if user is team member: " + e.getMessage());
            throw new RuntimeException("Failed to check if user is team member", e);
        }
        
        return false;
    }
    
    @Override
    public List<TeamMember> getTeamMembersByRole(int teamId, String role) {
        String sql = "SELECT * FROM team_member WHERE team_id = ? AND role_in_team = ? ORDER BY user_id";
        List<TeamMember> teamMembers = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, teamId);
            pstmt.setString(2, role);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    teamMembers.add(mapResultSetToTeamMember(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting team members by role: " + e.getMessage());
            throw new RuntimeException("Failed to get team members by role", e);
        }
        
        return teamMembers;
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
    public void removeUserFromTeam(int userId, int teamId) {
        String sql = "DELETE FROM team_member WHERE user_id = ? AND team_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            pstmt.setInt(2, teamId);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("User removed from team successfully");
            } else {
                System.out.println("No team member found with user ID: " + userId + " and team ID: " + teamId);
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error removing user from team: " + e.getMessage());
            throw new RuntimeException("Failed to remove user from team", e);
        }
    }
    
    @Override
    public void deleteAllTeamMembers(int teamId) {
        String sql = "DELETE FROM team_member WHERE team_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, teamId);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("All team members deleted for team ID: " + teamId);
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error deleting all team members: " + e.getMessage());
            throw new RuntimeException("Failed to delete all team members", e);
        }
    }
    
    @Override
    public void deleteAllUserTeamMemberships(int userId) {
        String sql = "DELETE FROM team_member WHERE user_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("All team memberships deleted for user ID: " + userId);
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error deleting all user team memberships: " + e.getMessage());
            throw new RuntimeException("Failed to delete all user team memberships", e);
        }
    }
    
    /**
     * Map ResultSet to TeamMember object
     * @param rs ResultSet containing team member data
     * @return TeamMember object
     * @throws SQLException if mapping fails
     */
    private TeamMember mapResultSetToTeamMember(ResultSet rs) throws SQLException {
        TeamMember teamMember = new TeamMember();
        teamMember.setTeamId(rs.getInt("team_id"));
        teamMember.setUserId(rs.getInt("user_id"));
        teamMember.setRoleInTeam(rs.getString("role_in_team"));
        return teamMember;
    }
}
