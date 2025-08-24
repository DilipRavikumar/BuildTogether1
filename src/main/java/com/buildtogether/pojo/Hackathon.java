package com.buildtogether.pojo;

import java.util.Date;

/**
 * Hackathon entity representing a hackathon event
 */
public class Hackathon {
    private int hackathonId;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int maxTeamSize;

    // Default constructor
    public Hackathon() {
        this.maxTeamSize = 5;
    }

    // Parameterized constructor
    public Hackathon(String title, String description, Date startDate, Date endDate, int maxTeamSize) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxTeamSize = maxTeamSize;
    }

    // Getters and Setters
    public int getHackathonId() {
        return hackathonId;
    }

    public void setHackathonId(int hackathonId) {
        this.hackathonId = hackathonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getMaxTeamSize() {
        return maxTeamSize;
    }

    public void setMaxTeamSize(int maxTeamSize) {
        this.maxTeamSize = maxTeamSize;
    }

    @Override
    public String toString() {
        return "Hackathon{" +
                "hackathonId=" + hackathonId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", maxTeamSize=" + maxTeamSize +
                '}';
    }
}
