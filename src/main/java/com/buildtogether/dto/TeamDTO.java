package com.buildtogether.dto;

import com.buildtogether.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
    private Long id;
    private Long hackathonId;
    private Long createdById;
    private String teamName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Static factory method to convert from Team entity
    public static TeamDTO fromTeam(Team team) {
        TeamDTO dto = new TeamDTO();
        dto.setId(team.getId());
        dto.setHackathonId(team.getHackathon().getId());
        dto.setCreatedById(team.getCreatedBy().getId());
        dto.setTeamName(team.getTeamName());
        dto.setCreatedAt(team.getCreatedAt());
        dto.setUpdatedAt(team.getUpdatedAt());
        return dto;
    }
}
