package com.buildtogether.dto;

import com.buildtogether.entity.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillDTO {
    private Long id;
    private String skillName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Static factory method to convert from Skill entity
    public static SkillDTO fromSkill(Skill skill) {
        SkillDTO dto = new SkillDTO();
        dto.setId(skill.getId());
        dto.setSkillName(skill.getSkillName());
        dto.setCreatedAt(skill.getCreatedAt());
        dto.setUpdatedAt(skill.getUpdatedAt());
        return dto;
    }
}
