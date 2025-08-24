package com.buildtogether.pojo;

/**
 * Team entity representing a team in the BuildTogether system
 */
public class Team {
    private int teamId;
    private int hackathonId;
    private int createdBy;
    private String teamName;

    // Default constructor
    public Team() {
    }

    // Parameterized constructor
    public Team(int hackathonId, int createdBy, String teamName) {
        this.hackathonId = hackathonId;
        this.createdBy = createdBy;
        this.teamName = teamName;
    }

    // Getters and Setters
    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getHackathonId() {
        return hackathonId;
    }

    public void setHackathonId(int hackathonId) {
        this.hackathonId = hackathonId;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", hackathonId=" + hackathonId +
                ", createdBy=" + createdBy +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
