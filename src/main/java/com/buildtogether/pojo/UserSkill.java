package com.buildtogether.pojo;

/**
 * UserSkill entity representing the relationship between users and skills with proficiency levels
 */
public class UserSkill {
    private int userId;
    private int skillId;
    private String proficiencyLevel; // BEGINNER, INTERMEDIATE, ADVANCED, EXPERT

    // Default constructor
    public UserSkill() {
    }

    // Parameterized constructor
    public UserSkill(int userId, int skillId, String proficiencyLevel) {
        this.userId = userId;
        this.skillId = skillId;
        this.proficiencyLevel = proficiencyLevel;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(String proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

    @Override
    public String toString() {
        return "UserSkill{" +
                "userId=" + userId +
                ", skillId=" + skillId +
                ", proficiencyLevel='" + proficiencyLevel + '\'' +
                '}';
    }
}
