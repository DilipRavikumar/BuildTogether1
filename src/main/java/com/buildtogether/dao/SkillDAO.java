package com.buildtogether.dao;

import com.buildtogether.pojo.Skill;
import java.util.List;

/**
 * Data Access Object interface for Skill entity
 */
public interface SkillDAO {
    
    /**
     * Add a new skill to the database
     * @param skill The skill to add
     */
    void addSkill(Skill skill);
    
    /**
     * Update an existing skill in the database
     * @param skill The skill to update
     */
    void updateSkill(Skill skill);
    
    /**
     * Delete a skill from the database
     * @param skillId The ID of the skill to delete
     */
    void deleteSkill(int skillId);
    
    /**
     * Get a skill by its ID
     * @param skillId The ID of the skill
     * @return The skill object, or null if not found
     */
    Skill getSkillById(int skillId);
    
    /**
     * Get a skill by its name
     * @param skillName The name of the skill
     * @return The skill object, or null if not found
     */
    Skill getSkillByName(String skillName);
    
    /**
     * Get all skills from the database
     * @return List of all skills
     */
    List<Skill> getAllSkills();
    
    /**
     * Search skills by name (partial match)
     * @param skillName The partial skill name to search for
     * @return List of matching skills
     */
    List<Skill> searchSkillsByName(String skillName);
    
    /**
     * Check if a skill name already exists
     * @param skillName The skill name to check
     * @return true if skill name exists, false otherwise
     */
    boolean skillNameExists(String skillName);
}
