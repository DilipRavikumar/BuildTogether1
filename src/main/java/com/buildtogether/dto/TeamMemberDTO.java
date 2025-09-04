package com.buildtogether.dto;

import com.buildtogether.entity.TeamMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberDTO {
    private Long teamId;
    private String teamName;
    private Long hackathonId;
    private String hackathonTitle;
    private Long userId;
    private String userName;
    private String userEmail;
    private String roleInTeam;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Static factory method to convert from TeamMember entity
    public static TeamMemberDTO fromTeamMember(TeamMember teamMember) {
        TeamMemberDTO dto = new TeamMemberDTO();
        dto.setTeamId(teamMember.getId().getTeamId());
        dto.setTeamName(teamMember.getTeam().getTeamName());
        dto.setHackathonId(teamMember.getTeam().getHackathon().getId());
        dto.setHackathonTitle(teamMember.getTeam().getHackathon().getTitle());
        dto.setUserId(teamMember.getId().getUserId());
        dto.setUserName(teamMember.getUser().getName());
        dto.setUserEmail(teamMember.getUser().getEmail());
        dto.setRoleInTeam(teamMember.getRoleInTeam().name());
        dto.setCreatedAt(teamMember.getCreatedAt());
        dto.setUpdatedAt(teamMember.getUpdatedAt());
        return dto;
    }
}
