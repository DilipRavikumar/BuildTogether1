package com.buildtogether.pojo;

/**
 * TeamMember entity representing the relationship between users and teams
 */
public class TeamMember {
    private int teamId;
    private int userId;
    private String roleInTeam;

    // Default constructor
    public TeamMember() {
    }

    // Parameterized constructor
    public TeamMember(int teamId, int userId, String roleInTeam) {
        this.teamId = teamId;
        this.userId = userId;
        this.roleInTeam = roleInTeam;
    }

    // Getters and Setters
    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRoleInTeam() {
        return roleInTeam;
    }

    public void setRoleInTeam(String roleInTeam) {
        this.roleInTeam = roleInTeam;
    }

    @Override
    public String toString() {
        return "TeamMember{" +
                "teamId=" + teamId +
                ", userId=" + userId +
                ", roleInTeam='" + roleInTeam + '\'' +
                '}';
    }
}
