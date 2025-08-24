package com.buildtogether.dao;

import com.buildtogether.pojo.UserSkill;
import java.util.List;

/**
 * Data Access Object interface for UserSkill entity
 */
public interface UserSkillDAO {
    
    /**
     * Add a new user skill to the database
     * @param userSkill The user skill to add
     */
    void addUserSkill(UserSkill userSkill);
    
    /**
     * Update an existing user skill in the database
     * @param userSkill The user skill to update
     */
    void updateUserSkill(UserSkill userSkill);
    
    /**
     * Delete a user skill from the database
     * @param userId The user ID
     * @param skillId The skill ID
     */
    void deleteUserSkill(int userId, int skillId);
    
    /**
     * Get all skills for a specific user
     * @param userId The ID of the user
     * @return List of user skills for the specified user
     */
    List<UserSkill> getUserSkillsByUserId(int userId);
    
    /**
     * Get all users who have a specific skill
     * @param skillId The ID of the skill
     * @return List of user skills for the specified skill
     */
    List<UserSkill> getUserSkillsBySkillId(int skillId);
    
    /**
     * Get all user skills from the database
     * @return List of all user skills
     */
    List<UserSkill> getAllUserSkills();
    
    /**
     * Check if a user already has a specific skill
     * @param userId The ID of the user
     * @param skillId The ID of the skill
     * @return true if user has the skill, false otherwise
     */
    boolean userHasSkill(int userId, int skillId);
    
    /**
     * Get user skills by proficiency level
     * @param userId The ID of the user
     * @param proficiencyLevel The proficiency level to filter by
     * @return List of user skills with the specified proficiency level
     */
    List<UserSkill> getUserSkillsByProficiencyLevel(int userId, String proficiencyLevel);
    
    /**
     * Delete all skills for a specific user
     * @param userId The ID of the user
     */
    void deleteAllUserSkills(int userId);
}
