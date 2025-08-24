package com.buildtogether.dao.impl;

import com.buildtogether.dao.UserSkillDAO;
import com.buildtogether.pojo.UserSkill;
import com.buildtogether.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of UserSkillDAO for Oracle database
 */
public class UserSkillDAOImpl implements UserSkillDAO {
    
    private final DBConnection dbConnection;
    
    public UserSkillDAOImpl() {
        this.dbConnection = DBConnection.getInstance();
    }
    
    @Override
    public void addUserSkill(UserSkill userSkill) {
        String sql = "INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userSkill.getUserId());
            pstmt.setInt(2, userSkill.getSkillId());
            pstmt.setString(3, userSkill.getProficiencyLevel());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("User skill added successfully");
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error adding user skill: " + e.getMessage());
            throw new RuntimeException("Failed to add user skill", e);
        }
    }
    
    @Override
    public void updateUserSkill(UserSkill userSkill) {
        String sql = "UPDATE user_skill SET proficiency_level = ? WHERE user_id = ? AND skill_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userSkill.getProficiencyLevel());
            pstmt.setInt(2, userSkill.getUserId());
            pstmt.setInt(3, userSkill.getSkillId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("User skill updated successfully");
            } else {
                System.out.println("No user skill found for user ID: " + userSkill.getUserId() + " and skill ID: " + userSkill.getSkillId());
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error updating user skill: " + e.getMessage());
            throw new RuntimeException("Failed to update user skill", e);
        }
    }
    
    @Override
    public void deleteUserSkill(int userId, int skillId) {
        String sql = "DELETE FROM user_skill WHERE user_id = ? AND skill_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            pstmt.setInt(2, skillId);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("User skill deleted successfully");
            } else {
                System.out.println("No user skill found for user ID: " + userId + " and skill ID: " + skillId);
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error deleting user skill: " + e.getMessage());
            throw new RuntimeException("Failed to delete user skill", e);
        }
    }
    
    @Override
    public List<UserSkill> getUserSkillsByUserId(int userId) {
        String sql = "SELECT * FROM user_skill WHERE user_id = ? ORDER BY skill_id";
        List<UserSkill> userSkills = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    userSkills.add(mapResultSetToUserSkill(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting user skills by user ID: " + e.getMessage());
            throw new RuntimeException("Failed to get user skills by user ID", e);
        }
        
        return userSkills;
    }
    
    @Override
    public List<UserSkill> getUserSkillsBySkillId(int skillId) {
        String sql = "SELECT * FROM user_skill WHERE skill_id = ? ORDER BY user_id";
        List<UserSkill> userSkills = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, skillId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    userSkills.add(mapResultSetToUserSkill(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting user skills by skill ID: " + e.getMessage());
            throw new RuntimeException("Failed to get user skills by skill ID", e);
        }
        
        return userSkills;
    }
    
    @Override
    public List<UserSkill> getAllUserSkills() {
        String sql = "SELECT * FROM user_skill ORDER BY user_id, skill_id";
        List<UserSkill> userSkills = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                userSkills.add(mapResultSetToUserSkill(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all user skills: " + e.getMessage());
            throw new RuntimeException("Failed to get all user skills", e);
        }
        
        return userSkills;
    }
    
    @Override
    public boolean userHasSkill(int userId, int skillId) {
        String sql = "SELECT COUNT(*) FROM user_skill WHERE user_id = ? AND skill_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            pstmt.setInt(2, skillId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking if user has skill: " + e.getMessage());
            throw new RuntimeException("Failed to check if user has skill", e);
        }
        
        return false;
    }
    
    @Override
    public List<UserSkill> getUserSkillsByProficiencyLevel(int userId, String proficiencyLevel) {
        String sql = "SELECT * FROM user_skill WHERE user_id = ? AND proficiency_level = ? ORDER BY skill_id";
        List<UserSkill> userSkills = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            pstmt.setString(2, proficiencyLevel);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    userSkills.add(mapResultSetToUserSkill(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting user skills by proficiency level: " + e.getMessage());
            throw new RuntimeException("Failed to get user skills by proficiency level", e);
        }
        
        return userSkills;
    }
    
    @Override
    public void deleteAllUserSkills(int userId) {
        String sql = "DELETE FROM user_skill WHERE user_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("All user skills deleted successfully for user ID: " + userId);
            } else {
                System.out.println("No user skills found for user ID: " + userId);
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error deleting all user skills: " + e.getMessage());
            throw new RuntimeException("Failed to delete all user skills", e);
        }
    }
    
    /**
     * Map ResultSet to UserSkill object
     * @param rs ResultSet containing user skill data
     * @return UserSkill object
     * @throws SQLException if mapping fails
     */
    private UserSkill mapResultSetToUserSkill(ResultSet rs) throws SQLException {
        UserSkill userSkill = new UserSkill();
        userSkill.setUserId(rs.getInt("user_id"));
        userSkill.setSkillId(rs.getInt("skill_id"));
        userSkill.setProficiencyLevel(rs.getString("proficiency_level"));
        return userSkill;
    }
}

