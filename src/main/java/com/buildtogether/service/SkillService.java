package com.buildtogether.service;

import com.buildtogether.dao.SkillDAO;
import com.buildtogether.dao.impl.SkillDAOImpl;
import com.buildtogether.pojo.Skill;

import java.util.List;

/**
 * Service class for skill-related business logic
 */
public class SkillService {
    
    private final SkillDAO skillDAO;
    
    public SkillService() {
        this.skillDAO = new SkillDAOImpl();
    }
    
    /**
     * Create a new skill
     * @param skill The skill to create
     * @return Success message or error message
     */
    public String createSkill(Skill skill) {
        try {
            // Validate skill data
            String validationResult = validateSkillData(skill);
            if (!"OK".equals(validationResult)) {
                return validationResult;
            }
            
            // Check if skill name already exists
            if (skillDAO.skillNameExists(skill.getSkillName())) {
                return "Skill with this name already exists. Please use a different skill name.";
            }
            
            // Add skill to database
            skillDAO.addSkill(skill);
            return "Skill created successfully with ID: " + skill.getSkillId();
            
        } catch (Exception e) {
            System.err.println("Error creating skill: " + e.getMessage());
            return "Error creating skill: " + e.getMessage();
        }
    }
    
    /**
     * Update an existing skill
     * @param skill The skill to update
     * @return Success message or error message
     */
    public String updateSkill(Skill skill) {
        try {
            // Validate skill data
            String validationResult = validateSkillData(skill);
            if (!"OK".equals(validationResult)) {
                return validationResult;
            }
            
            // Check if skill exists
            Skill existingSkill = skillDAO.getSkillById(skill.getSkillId());
            if (existingSkill == null) {
                return "Skill not found with ID: " + skill.getSkillId();
            }
            
            // Check if skill name is being changed and if it already exists
            if (!existingSkill.getSkillName().equals(skill.getSkillName()) && 
                skillDAO.skillNameExists(skill.getSkillName())) {
                return "Skill with this name already exists. Please use a different skill name.";
            }
            
            // Update skill
            skillDAO.updateSkill(skill);
            return "Skill updated successfully";
            
        } catch (Exception e) {
            System.err.println("Error updating skill: " + e.getMessage());
            return "Error updating skill: " + e.getMessage();
        }
    }
    
    /**
     * Delete a skill
     * @param skillId The ID of the skill to delete
     * @return Success message or error message
     */
    public String deleteSkill(int skillId) {
        try {
            // Check if skill exists
            Skill skill = skillDAO.getSkillById(skillId);
            if (skill == null) {
                return "Skill not found with ID: " + skillId;
            }
            
            // Delete skill
            skillDAO.deleteSkill(skillId);
            return "Skill deleted successfully";
            
        } catch (Exception e) {
            System.err.println("Error deleting skill: " + e.getMessage());
            return "Error deleting skill: " + e.getMessage();
        }
    }
    
    /**
     * Get skill by ID
     * @param skillId The ID of the skill
     * @return Skill object or null if not found
     */
    public Skill getSkillById(int skillId) {
        try {
            return skillDAO.getSkillById(skillId);
        } catch (Exception e) {
            System.err.println("Error getting skill by ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get skill by name
     * @param skillName The name of the skill
     * @return Skill object or null if not found
     */
    public Skill getSkillByName(String skillName) {
        try {
            if (skillName == null || skillName.trim().isEmpty()) {
                return null;
            }
            return skillDAO.getSkillByName(skillName.trim());
        } catch (Exception e) {
            System.err.println("Error getting skill by name: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get all skills
     * @return List of all skills
     */
    public List<Skill> getAllSkills() {
        try {
            return skillDAO.getAllSkills();
        } catch (Exception e) {
            System.err.println("Error getting all skills: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Search skills by name
     * @param skillName The partial skill name to search for
     * @return List of matching skills
     */
    public List<Skill> searchSkillsByName(String skillName) {
        try {
            if (skillName == null || skillName.trim().isEmpty()) {
                return null;
            }
            return skillDAO.searchSkillsByName(skillName.trim());
        } catch (Exception e) {
            System.err.println("Error searching skills by name: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Check if skill name exists
     * @param skillName The skill name to check
     * @return true if skill name exists, false otherwise
     */
    public boolean skillNameExists(String skillName) {
        try {
            if (skillName == null || skillName.trim().isEmpty()) {
                return false;
            }
            return skillDAO.skillNameExists(skillName.trim());
        } catch (Exception e) {
            System.err.println("Error checking skill name existence: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get popular skills (skills with most users)
     * @param limit The maximum number of skills to return
     * @return List of popular skills
     */
    public List<Skill> getPopularSkills(int limit) {
        try {
            // This would need to be implemented in the DAO layer
            // For now, we'll return all skills
            List<Skill> allSkills = skillDAO.getAllSkills();
            if (allSkills != null && allSkills.size() > limit) {
                return allSkills.subList(0, limit);
            }
            return allSkills;
        } catch (Exception e) {
            System.err.println("Error getting popular skills: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get skills by category (placeholder for future implementation)
     * @param category The category to filter by
     * @return List of skills in the specified category
     */
    public List<Skill> getSkillsByCategory(String category) {
        try {
            // This would need to be implemented when we add category field to Skill
            // For now, we'll return all skills
            return skillDAO.getAllSkills();
        } catch (Exception e) {
            System.err.println("Error getting skills by category: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Validate skill data
     * @param skill The skill to validate
     * @return "OK" if valid, error message otherwise
     */
    private String validateSkillData(Skill skill) {
        if (skill == null) {
            return "Skill object cannot be null";
        }
        
        if (skill.getSkillName() == null || skill.getSkillName().trim().isEmpty()) {
            return "Skill name cannot be empty";
        }
        
        if (skill.getSkillName().length() < 2 || skill.getSkillName().length() > 100) {
            return "Skill name must be between 2 and 100 characters";
        }
        
        // Check for valid characters (alphanumeric, spaces, hyphens, underscores)
        if (!skill.getSkillName().matches("^[a-zA-Z0-9\\s\\-_]+$")) {
            return "Skill name can only contain letters, numbers, spaces, hyphens, and underscores";
        }
        
        return "OK";
    }
}
