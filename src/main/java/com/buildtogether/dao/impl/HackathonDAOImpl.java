package com.buildtogether.dao.impl;

import com.buildtogether.dao.HackathonDAO;
import com.buildtogether.pojo.Hackathon;
import com.buildtogether.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HackathonDAOImpl implements HackathonDAO {
    
    private final DBConnection dbConnection;
    
    public HackathonDAOImpl() {
        this.dbConnection = DBConnection.getInstance();
    }
    
    @Override
    public void addHackathon(Hackathon hackathon) {
        String sql = "INSERT INTO hackathon (title, description, start_date, end_date, max_team_size) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"hackathon_id"})) {
            
            pstmt.setString(1, hackathon.getTitle());
            pstmt.setString(2, hackathon.getDescription());
            pstmt.setDate(3, new java.sql.Date(hackathon.getStartDate().getTime()));
            pstmt.setDate(4, new java.sql.Date(hackathon.getEndDate().getTime()));
            pstmt.setInt(5, hackathon.getMaxTeamSize());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        hackathon.setHackathonId(rs.getInt(1));
                    }
                }
                dbConnection.commit();
                System.out.println("Hackathon added successfully with ID: " + hackathon.getHackathonId());
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error adding hackathon: " + e.getMessage());
            throw new RuntimeException("Failed to add hackathon", e);
        }
    }
    
    @Override
    public void updateHackathon(Hackathon hackathon) {
        String sql = "UPDATE hackathon SET title = ?, description = ?, start_date = ?, end_date = ?, " +
                    "max_team_size = ? WHERE hackathon_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, hackathon.getTitle());
            pstmt.setString(2, hackathon.getDescription());
            pstmt.setDate(3, new java.sql.Date(hackathon.getStartDate().getTime()));
            pstmt.setDate(4, new java.sql.Date(hackathon.getEndDate().getTime()));
            pstmt.setInt(5, hackathon.getMaxTeamSize());
            pstmt.setInt(6, hackathon.getHackathonId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("Hackathon updated successfully");
            } else {
                System.out.println("No hackathon found with ID: " + hackathon.getHackathonId());
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error updating hackathon: " + e.getMessage());
            throw new RuntimeException("Failed to update hackathon", e);
        }
    }
    
    @Override
    public void deleteHackathon(int hackathonId) {
        String sql = "DELETE FROM hackathon WHERE hackathon_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, hackathonId);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("Hackathon deleted successfully");
            } else {
                System.out.println("No hackathon found with ID: " + hackathonId);
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error deleting hackathon: " + e.getMessage());
            throw new RuntimeException("Failed to delete hackathon", e);
        }
    }
    
    @Override
    public Hackathon getHackathonById(int hackathonId) {
        String sql = "SELECT * FROM hackathon WHERE hackathon_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, hackathonId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToHackathon(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting hackathon by ID: " + e.getMessage());
            throw new RuntimeException("Failed to get hackathon by ID", e);
        }
        
        return null;
    }
    
    @Override
    public Hackathon getHackathonByName(String name) {
        String sql = "SELECT * FROM hackathon WHERE title = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToHackathon(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting hackathon by name: " + e.getMessage());
            throw new RuntimeException("Failed to get hackathon by name", e);
        }
        
        return null;
    }
    
    @Override
    public List<Hackathon> getAllHackathons() {
        String sql = "SELECT * FROM hackathon ORDER BY start_date DESC";
        List<Hackathon> hackathons = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                hackathons.add(mapResultSetToHackathon(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all hackathons: " + e.getMessage());
            throw new RuntimeException("Failed to get all hackathons", e);
        }
        
        return hackathons;
    }
    
    @Override
    public List<Hackathon> getHackathonsByStatus(String status) {
        String sql = "SELECT * FROM hackathon WHERE status = ? ORDER BY start_date DESC";
        List<Hackathon> hackathons = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hackathons.add(mapResultSetToHackathon(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting hackathons by status: " + e.getMessage());
            throw new RuntimeException("Failed to get hackathons by status", e);
        }
        
        return hackathons;
    }
    
    @Override
    public List<Hackathon> getUpcomingHackathons() {
        String sql = "SELECT * FROM hackathon WHERE start_date > SYSDATE ORDER BY start_date ASC";
        List<Hackathon> hackathons = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                hackathons.add(mapResultSetToHackathon(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting upcoming hackathons: " + e.getMessage());
            throw new RuntimeException("Failed to get upcoming hackathons", e);
        }
        
        return hackathons;
    }
    
    @Override
    public List<Hackathon> getActiveHackathons() {
        String sql = "SELECT * FROM hackathon WHERE start_date <= SYSDATE AND end_date >= SYSDATE ORDER BY start_date DESC";
        List<Hackathon> hackathons = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                hackathons.add(mapResultSetToHackathon(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting active hackathons: " + e.getMessage());
            throw new RuntimeException("Failed to get active hackathons", e);
        }
        
        return hackathons;
    }
    
    @Override
    public List<Hackathon> getCompletedHackathons() {
        String sql = "SELECT * FROM hackathon WHERE end_date < SYSDATE ORDER BY end_date DESC";
        List<Hackathon> hackathons = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                hackathons.add(mapResultSetToHackathon(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting completed hackathons: " + e.getMessage());
            throw new RuntimeException("Failed to get completed hackathons", e);
        }
        
        return hackathons;
    }
    
    @Override
    public List<Hackathon> searchHackathonsByName(String name) {
        String sql = "SELECT * FROM hackathon WHERE LOWER(title) LIKE ? ORDER BY start_date DESC";
        List<Hackathon> hackathons = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + name.toLowerCase() + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hackathons.add(mapResultSetToHackathon(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching hackathons by name: " + e.getMessage());
            throw new RuntimeException("Failed to search hackathons by name", e);
        }
        
        return hackathons;
    }
    
    @Override
    public List<Hackathon> getHackathonsByTheme(String theme) {
        String sql = "SELECT * FROM hackathon WHERE LOWER(description) LIKE ? ORDER BY start_date DESC";
        List<Hackathon> hackathons = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + theme.toLowerCase() + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hackathons.add(mapResultSetToHackathon(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting hackathons by theme: " + e.getMessage());
            throw new RuntimeException("Failed to get hackathons by theme", e);
        }
        
        return hackathons;
    }
    
    @Override
    public List<Hackathon> getHackathonsByDateRange(Date startDate, Date endDate) {
        String sql = "SELECT * FROM hackathon WHERE start_date >= ? AND end_date <= ? ORDER BY start_date ASC";
        List<Hackathon> hackathons = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
            pstmt.setDate(2, new java.sql.Date(endDate.getTime()));
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hackathons.add(mapResultSetToHackathon(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting hackathons by date range: " + e.getMessage());
            throw new RuntimeException("Failed to get hackathons by date range", e);
        }
        
        return hackathons;
    }
    
    @Override
    public boolean hackathonNameExists(String name) {
        String sql = "SELECT COUNT(*) FROM hackathon WHERE title = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking hackathon name existence: " + e.getMessage());
            throw new RuntimeException("Failed to check hackathon name existence", e);
        }
        
        return false;
    }
    
    @Override
    public void updateHackathonStatus(int hackathonId) {
        // This method would update hackathon status based on current date
        // For now, we'll implement a simple status update
        String sql = "UPDATE hackathon SET status = CASE " +
                                    "WHEN start_date > SYSDATE THEN 'UPCOMING' " +
                "WHEN end_date < SYSDATE THEN 'COMPLETED' " +
                    "ELSE 'ACTIVE' END " +
                    "WHERE hackathon_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, hackathonId);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("Hackathon status updated successfully");
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error updating hackathon status: " + e.getMessage());
            throw new RuntimeException("Failed to update hackathon status", e);
        }
    }
    
    /**
     * Map ResultSet to Hackathon object
     * @param rs ResultSet containing hackathon data
     * @return Hackathon object
     * @throws SQLException if mapping fails
     */
    private Hackathon mapResultSetToHackathon(ResultSet rs) throws SQLException {
        Hackathon hackathon = new Hackathon();
        hackathon.setHackathonId(rs.getInt("hackathon_id"));
        hackathon.setTitle(rs.getString("title"));
        hackathon.setDescription(rs.getString("description"));
        hackathon.setStartDate(rs.getDate("start_date"));
        hackathon.setEndDate(rs.getDate("end_date"));
        hackathon.setMaxTeamSize(rs.getInt("max_team_size"));
        return hackathon;
    }
}
