package com.buildtogether.dto;

import com.buildtogether.entity.Hackathon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HackathonDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer maxTeamSize;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Static factory method to convert from Hackathon entity
    public static HackathonDTO fromHackathon(Hackathon hackathon) {
        HackathonDTO dto = new HackathonDTO();
        dto.setId(hackathon.getId());
        dto.setTitle(hackathon.getTitle());
        dto.setDescription(hackathon.getDescription());
        dto.setStartDate(hackathon.getStartDate());
        dto.setEndDate(hackathon.getEndDate());
        dto.setMaxTeamSize(hackathon.getMaxTeamSize());
        dto.setCreatedAt(hackathon.getCreatedAt());
        dto.setUpdatedAt(hackathon.getUpdatedAt());
        return dto;
    }
}
