package com.buildtogether.dto;

import com.buildtogether.entity.JoinRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequestDTO {
    private Long id;
    private Long teamId;
    private String teamName;
    private Long hackathonId;
    private String hackathonTitle;
    private Long userId;
    private String userName;
    private String userEmail;
    private String status;
    private LocalDateTime requestedAt;
    private LocalDateTime updatedAt;

    // Static factory method to convert from JoinRequest entity
    public static JoinRequestDTO fromJoinRequest(JoinRequest joinRequest) {
        JoinRequestDTO dto = new JoinRequestDTO();
        dto.setId(joinRequest.getId());
        dto.setTeamId(joinRequest.getTeam().getId());
        dto.setTeamName(joinRequest.getTeam().getTeamName());
        dto.setHackathonId(joinRequest.getTeam().getHackathon().getId());
        dto.setHackathonTitle(joinRequest.getTeam().getHackathon().getTitle());
        dto.setUserId(joinRequest.getUser().getId());
        dto.setUserName(joinRequest.getUser().getName());
        dto.setUserEmail(joinRequest.getUser().getEmail());
        dto.setStatus(joinRequest.getStatus().name());
        dto.setRequestedAt(joinRequest.getRequestedAt());
        dto.setUpdatedAt(joinRequest.getUpdatedAt());
        return dto;
    }
}
