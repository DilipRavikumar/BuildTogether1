package com.buildtogether.service;

import com.buildtogether.dao.UserSkillDAO;
import com.buildtogether.dao.impl.UserSkillDAOImpl;
import com.buildtogether.pojo.UserSkill;

import java.util.List;

/**
 * Service class for user skill-related business logic
 */
public class UserSkillService {
    
    private final UserSkillDAO userSkillDAO;
    
    public UserSkillService() {
        this.userSkillDAO = new UserSkillDAOImpl();
    }
    
    /**
     * Add a skill to a user's profile
     * @param userSkill The user skill to add
     * @return Success message or error message
     */
    public String addUserSkill(UserSkill userSkill) {
        try {
            // Validate user skill data
            String validationResult = validateUserSkillData(userSkill);
            if (!"OK".equals(validationResult)) {
                return validationResult;
            }
            
            // Check if user already has this skill
            if (userSkillDAO.userHasSkill(userSkill.getUserId(), userSkill.getSkillId())) {
                return "User already has this skill.";
            }
            
            // Add user skill to database
            userSkillDAO.addUserSkill(userSkill);
            return "Skill added to user profile successfully";
            
        } catch (Exception e) {
            System.err.println("Error adding user skill: " + e.getMessage());
            return "Error adding user skill: " + e.getMessage();
        }
    }
    
    /**
     * Update an existing user skill
     * @param userSkill The user skill to update
     * @return Success message or error message
     */
    public String updateUserSkill(UserSkill userSkill) {
        try {
            // Validate user skill data
            String validationResult = validateUserSkillData(userSkill);
            if (!"OK".equals(validationResult)) {
                return validationResult;
            }
            
            // Check if user has this skill
            if (!userSkillDAO.userHasSkill(userSkill.getUserId(), userSkill.getSkillId())) {
                return "User does not have this skill.";
            }
            
            // Update user skill
            userSkillDAO.updateUserSkill(userSkill);
            return "User skill updated successfully";
            
        } catch (Exception e) {
            System.err.println("Error updating user skill: " + e.getMessage());
            return "Error updating user skill: " + e.getMessage();
        }
    }
    
    /**
     * Remove a skill from a user's profile
     * @param userId The ID of the user
     * @param skillId The ID of the skill
     * @return Success message or error message
     */
    public String removeUserSkill(int userId, int skillId) {
        try {
            // Check if user has this skill
            if (!userSkillDAO.userHasSkill(userId, skillId)) {
                return "User does not have this skill.";
            }
            
            // Remove user skill
            userSkillDAO.deleteUserSkill(userId, skillId);
            return "Skill removed from user profile successfully";
            
        } catch (Exception e) {
            System.err.println("Error removing user skill: " + e.getMessage());
            return "Error removing user skill: " + e.getMessage();
        }
    }
    
    /**
     * Get all skills for a specific user
     * @param userId The ID of the user
     * @return List of user skills for the specified user
     */
    public List<UserSkill> getUserSkillsByUserId(int userId) {
        try {
            return userSkillDAO.getUserSkillsByUserId(userId);
        } catch (Exception e) {
            System.err.println("Error getting user skills by user ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get all users who have a specific skill
     * @param skillId The ID of the skill
     * @return List of user skills for the specified skill
     */
    public List<UserSkill> getUserSkillsBySkillId(int skillId) {
        try {
            return userSkillDAO.getUserSkillsBySkillId(skillId);
        } catch (Exception e) {
            System.err.println("Error getting user skills by skill ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get all user skills
     * @return List of all user skills
     */
    public List<UserSkill> getAllUserSkills() {
        try {
            return userSkillDAO.getAllUserSkills();
        } catch (Exception e) {
            System.err.println("Error getting all user skills: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Check if a user has a specific skill
     * @param userId The ID of the user
     * @param skillId The ID of the skill
     * @return true if user has the skill, false otherwise
     */
    public boolean userHasSkill(int userId, int skillId) {
        try {
            return userSkillDAO.userHasSkill(userId, skillId);
        } catch (Exception e) {
            System.err.println("Error checking if user has skill: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get user skills by proficiency level
     * @param userId The ID of the user
     * @param proficiencyLevel The proficiency level to filter by
     * @return List of user skills with the specified proficiency level
     */
    public List<UserSkill> getUserSkillsByProficiencyLevel(int userId, String proficiencyLevel) {
        try {
            if (proficiencyLevel == null || proficiencyLevel.trim().isEmpty()) {
                return null;
            }
            return userSkillDAO.getUserSkillsByProficiencyLevel(userId, proficiencyLevel.trim());
        } catch (Exception e) {
            System.err.println("Error getting user skills by proficiency level: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Delete all skills for a specific user
     * @param userId The ID of the user
     * @return Success message or error message
     */
    public String deleteAllUserSkills(int userId) {
        try {
            userSkillDAO.deleteAllUserSkills(userId);
            return "All skills deleted for user ID: " + userId;
            
        } catch (Exception e) {
            System.err.println("Error deleting all user skills: " + e.getMessage());
            return "Error deleting all user skills: " + e.getMessage();
        }
    }
    
    /**
     * Get user skill count
     * @param userId The ID of the user
     * @return Number of skills the user has
     */
    public int getUserSkillCount(int userId) {
        try {
            List<UserSkill> userSkills = userSkillDAO.getUserSkillsByUserId(userId);
            return userSkills != null ? userSkills.size() : 0;
        } catch (Exception e) {
            System.err.println("Error getting user skill count: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Validate user skill data
     * @param userSkill The user skill to validate
     * @return "OK" if valid, error message otherwise
     */
    private String validateUserSkillData(UserSkill userSkill) {
        if (userSkill == null) {
            return "User skill object cannot be null";
        }
        
        if (userSkill.getUserId() <= 0) {
            return "User ID must be valid";
        }
        
        if (userSkill.getSkillId() <= 0) {
            return "Skill ID must be valid";
        }
        
        if (userSkill.getProficiencyLevel() == null || userSkill.getProficiencyLevel().trim().isEmpty()) {
            return "Proficiency level cannot be empty";
        }
        
        if (!userSkill.getProficiencyLevel().matches("BEGINNER|INTERMEDIATE|ADVANCED|EXPERT")) {
            return "Proficiency level must be BEGINNER, INTERMEDIATE, ADVANCED, or EXPERT";
        }
        
        return "OK";
    }
}
