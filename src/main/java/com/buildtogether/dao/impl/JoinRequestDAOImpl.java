package com.buildtogether.dao.impl;

import com.buildtogether.dao.JoinRequestDAO;
import com.buildtogether.pojo.JoinRequest;
import com.buildtogether.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of JoinRequestDAO for MySQL database
 */
public class JoinRequestDAOImpl implements JoinRequestDAO {
    
    private final DBConnection dbConnection;
    
    public JoinRequestDAOImpl() {
        this.dbConnection = DBConnection.getInstance();
    }
    
    @Override
    public void addJoinRequest(JoinRequest joinRequest) {
        String sql = "INSERT INTO join_request (team_id, user_id, status) VALUES (?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, joinRequest.getTeamId());
            pstmt.setInt(2, joinRequest.getUserId());
            pstmt.setString(3, joinRequest.getStatus());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        joinRequest.setRequestId(rs.getInt(1));
                    }
                }
                dbConnection.commit();
                System.out.println("Join request added successfully with ID: " + joinRequest.getRequestId());
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error adding join request: " + e.getMessage());
            throw new RuntimeException("Failed to add join request", e);
        }
    }
    
    @Override
    public void updateJoinRequest(JoinRequest joinRequest) {
        String sql = "UPDATE join_request SET team_id = ?, user_id = ?, status = ? WHERE request_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, joinRequest.getTeamId());
            pstmt.setInt(2, joinRequest.getUserId());
            pstmt.setString(3, joinRequest.getStatus());
            pstmt.setInt(4, joinRequest.getRequestId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("Join request updated successfully");
            } else {
                System.out.println("No join request found with ID: " + joinRequest.getRequestId());
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error updating join request: " + e.getMessage());
            throw new RuntimeException("Failed to update join request", e);
        }
    }
    
    @Override
    public void deleteJoinRequest(int requestId) {
        String sql = "DELETE FROM join_request WHERE request_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, requestId);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("Join request deleted successfully");
            } else {
                System.out.println("No join request found with ID: " + requestId);
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error deleting join request: " + e.getMessage());
            throw new RuntimeException("Failed to delete join request", e);
        }
    }
    
    @Override
    public JoinRequest getJoinRequestById(int requestId) {
        String sql = "SELECT * FROM join_request WHERE request_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, requestId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToJoinRequest(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting join request by ID: " + e.getMessage());
            throw new RuntimeException("Failed to get join request by ID", e);
        }
        
        return null;
    }
    
    @Override
    public List<JoinRequest> getJoinRequestsByTeamId(int teamId) {
        String sql = "SELECT * FROM join_request WHERE team_id = ? ORDER BY requested_at DESC";
        List<JoinRequest> joinRequests = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, teamId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    joinRequests.add(mapResultSetToJoinRequest(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting join requests by team ID: " + e.getMessage());
            throw new RuntimeException("Failed to get join requests by team ID", e);
        }
        
        return joinRequests;
    }
    
    @Override
    public List<JoinRequest> getJoinRequestsByUserId(int userId) {
        String sql = "SELECT * FROM join_request WHERE user_id = ? ORDER BY requested_at DESC";
        List<JoinRequest> joinRequests = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    joinRequests.add(mapResultSetToJoinRequest(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting join requests by user ID: " + e.getMessage());
            throw new RuntimeException("Failed to get join requests by user ID", e);
        }
        
        return joinRequests;
    }
    
    @Override
    public List<JoinRequest> getPendingJoinRequestsByTeamId(int teamId) {
        String sql = "SELECT * FROM join_request WHERE team_id = ? AND status = 'PENDING' ORDER BY requested_at ASC";
        List<JoinRequest> joinRequests = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, teamId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    joinRequests.add(mapResultSetToJoinRequest(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting pending join requests by team ID: " + e.getMessage());
            throw new RuntimeException("Failed to get pending join requests by team ID", e);
        }
        
        return joinRequests;
    }
    
    @Override
    public List<JoinRequest> getJoinRequestsByStatus(String status) {
        String sql = "SELECT * FROM join_request WHERE status = ? ORDER BY requested_at DESC";
        List<JoinRequest> joinRequests = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    joinRequests.add(mapResultSetToJoinRequest(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting join requests by status: " + e.getMessage());
            throw new RuntimeException("Failed to get join requests by status", e);
        }
        
        return joinRequests;
    }
    
    @Override
    public List<JoinRequest> getAllJoinRequests() {
        String sql = "SELECT * FROM join_request ORDER BY requested_at DESC";
        List<JoinRequest> joinRequests = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                joinRequests.add(mapResultSetToJoinRequest(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all join requests: " + e.getMessage());
            throw new RuntimeException("Failed to get all join requests", e);
        }
        
        return joinRequests;
    }
    
    @Override
    public boolean userHasPendingRequest(int userId, int teamId) {
        String sql = "SELECT COUNT(*) FROM join_request WHERE user_id = ? AND team_id = ? AND status = 'PENDING'";
        
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
            System.err.println("Error checking if user has pending request: " + e.getMessage());
            throw new RuntimeException("Failed to check if user has pending request", e);
        }
        
        return false;
    }
    
    @Override
    public void approveJoinRequest(int requestId) {
        String sql = "UPDATE join_request SET status = 'APPROVED' WHERE request_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, requestId);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("Join request approved successfully");
            } else {
                System.out.println("No join request found with ID: " + requestId);
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error approving join request: " + e.getMessage());
            throw new RuntimeException("Failed to approve join request", e);
        }
    }
    
    @Override
    public void rejectJoinRequest(int requestId) {
        String sql = "UPDATE join_request SET status = 'REJECTED' WHERE request_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, requestId);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("Join request rejected successfully");
            } else {
                System.out.println("No join request found with ID: " + requestId);
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error rejecting join request: " + e.getMessage());
            throw new RuntimeException("Failed to reject join request", e);
        }
    }
    
    @Override
    public void deleteAllJoinRequestsForTeam(int teamId) {
        String sql = "DELETE FROM join_request WHERE team_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, teamId);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("All join requests deleted for team ID: " + teamId);
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error deleting all join requests for team: " + e.getMessage());
            throw new RuntimeException("Failed to delete all join requests for team", e);
        }
    }
    
    @Override
    public void deleteAllJoinRequestsByUser(int userId) {
        String sql = "DELETE FROM join_request WHERE user_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("All join requests deleted for user ID: " + userId);
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error deleting all join requests by user: " + e.getMessage());
            throw new RuntimeException("Failed to delete all join requests by user", e);
        }
    }
    
    /**
     * Map ResultSet to JoinRequest object
     * @param rs ResultSet containing join request data
     * @return JoinRequest object
     * @throws SQLException if mapping fails
     */
    private JoinRequest mapResultSetToJoinRequest(ResultSet rs) throws SQLException {
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setRequestId(rs.getInt("request_id"));
        joinRequest.setTeamId(rs.getInt("team_id"));
        joinRequest.setUserId(rs.getInt("user_id"));
        joinRequest.setStatus(rs.getString("status"));
        joinRequest.setRequestedAt(rs.getTimestamp("requested_at"));
        return joinRequest;
    }
}
