package com.buildtogether.pojo;

/**
 * Skill entity representing a skill that users can have
 */
public class Skill {
    private int skillId;
    private String skillName;

    // Default constructor
    public Skill() {
    }

    // Parameterized constructor
    public Skill(String skillName) {
        this.skillName = skillName;
    }

    // Getters and Setters
    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "skillId=" + skillId +
                ", skillName='" + skillName + '\'' +
                '}';
    }
}
