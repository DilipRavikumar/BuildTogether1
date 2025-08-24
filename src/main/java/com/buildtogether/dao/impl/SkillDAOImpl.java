package com.buildtogether.dao.impl;

import com.buildtogether.dao.SkillDAO;
import com.buildtogether.pojo.Skill;
import com.buildtogether.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of SkillDAO for MySQL database
 */
public class SkillDAOImpl implements SkillDAO {
    
    private final DBConnection dbConnection;
    
    public SkillDAOImpl() {
        this.dbConnection = DBConnection.getInstance();
    }
    
    @Override
    public void addSkill(Skill skill) {
        String sql = "INSERT INTO skill (skill_name) VALUES (?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, skill.getSkillName());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        skill.setSkillId(rs.getInt(1));
                    }
                }
                dbConnection.commit();
                System.out.println("Skill added successfully with ID: " + skill.getSkillId());
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error adding skill: " + e.getMessage());
            throw new RuntimeException("Failed to add skill", e);
        }
    }
    
    @Override
    public void updateSkill(Skill skill) {
        String sql = "UPDATE skill SET skill_name = ? WHERE skill_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, skill.getSkillName());
            pstmt.setInt(2, skill.getSkillId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("Skill updated successfully");
            } else {
                System.out.println("No skill found with ID: " + skill.getSkillId());
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error updating skill: " + e.getMessage());
            throw new RuntimeException("Failed to update skill", e);
        }
    }
    
    @Override
    public void deleteSkill(int skillId) {
        String sql = "DELETE FROM skill WHERE skill_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, skillId);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                dbConnection.commit();
                System.out.println("Skill deleted successfully");
            } else {
                System.out.println("No skill found with ID: " + skillId);
            }
            
        } catch (SQLException e) {
            dbConnection.rollback();
            System.err.println("Error deleting skill: " + e.getMessage());
            throw new RuntimeException("Failed to delete skill", e);
        }
    }
    
    @Override
    public Skill getSkillById(int skillId) {
        String sql = "SELECT * FROM skill WHERE skill_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, skillId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSkill(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting skill by ID: " + e.getMessage());
            throw new RuntimeException("Failed to get skill by ID", e);
        }
        
        return null;
    }
    
    @Override
    public Skill getSkillByName(String skillName) {
        String sql = "SELECT * FROM skill WHERE skill_name = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, skillName);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSkill(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting skill by name: " + e.getMessage());
            throw new RuntimeException("Failed to get skill by name", e);
        }
        
        return null;
    }
    
    @Override
    public List<Skill> getAllSkills() {
        String sql = "SELECT * FROM skill ORDER BY skill_name";
        List<Skill> skills = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                skills.add(mapResultSetToSkill(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all skills: " + e.getMessage());
            throw new RuntimeException("Failed to get all skills", e);
        }
        
        return skills;
    }
    
    @Override
    public List<Skill> searchSkillsByName(String skillName) {
        String sql = "SELECT * FROM skill WHERE LOWER(skill_name) LIKE ? ORDER BY skill_name";
        List<Skill> skills = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + skillName.toLowerCase() + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    skills.add(mapResultSetToSkill(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching skills by name: " + e.getMessage());
            throw new RuntimeException("Failed to search skills by name", e);
        }
        
        return skills;
    }
    
    @Override
    public boolean skillNameExists(String skillName) {
        String sql = "SELECT COUNT(*) FROM skill WHERE skill_name = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, skillName);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking skill name existence: " + e.getMessage());
            throw new RuntimeException("Failed to check skill name existence", e);
        }
        
        return false;
    }
    
    /**
     * Map ResultSet to Skill object
     * @param rs ResultSet containing skill data
     * @return Skill object
     * @throws SQLException if mapping fails
     */
    private Skill mapResultSetToSkill(ResultSet rs) throws SQLException {
        Skill skill = new Skill();
        skill.setSkillId(rs.getInt("skill_id"));
        skill.setSkillName(rs.getString("skill_name"));
        return skill;
    }
}

