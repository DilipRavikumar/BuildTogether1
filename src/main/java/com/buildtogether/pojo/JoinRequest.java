package com.buildtogether.pojo;

import java.util.Date;

/**
 * JoinRequest entity representing requests to join teams
 */
public class JoinRequest {
    private int requestId;
    private int teamId;
    private int userId;
    private String status; // PENDING, APPROVED, REJECTED
    private Date requestedAt;

    // Default constructor
    public JoinRequest() {
        this.requestedAt = new Date();
        this.status = "PENDING";
    }

    // Parameterized constructor
    public JoinRequest(int teamId, int userId) {
        this();
        this.teamId = teamId;
        this.userId = userId;
    }

    // Getters and Setters
    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Date requestedAt) {
        this.requestedAt = requestedAt;
    }

    @Override
    public String toString() {
        return "JoinRequest{" +
                "requestId=" + requestId +
                ", teamId=" + teamId +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                ", requestedAt=" + requestedAt +
                '}';
    }
}
