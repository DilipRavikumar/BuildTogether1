package com.buildtogether.dto;

import com.buildtogether.entity.UserSkill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillDTO {
    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private Long skillId;
    private String skillName;
    private String proficiencyLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Static factory method to convert from UserSkill entity
    public static UserSkillDTO fromUserSkill(UserSkill userSkill) {
        UserSkillDTO dto = new UserSkillDTO();
        dto.setId(userSkill.getId().getUserId()); // Use userId as the primary identifier
        dto.setUserId(userSkill.getId().getUserId());
        dto.setUserName(userSkill.getUser().getName());
        dto.setUserEmail(userSkill.getUser().getEmail());
        dto.setSkillId(userSkill.getId().getSkillId());
        dto.setSkillName(userSkill.getSkill().getSkillName());
        dto.setProficiencyLevel(userSkill.getProficiencyLevel().name());
        dto.setCreatedAt(userSkill.getCreatedAt());
        dto.setUpdatedAt(userSkill.getUpdatedAt());
        return dto;
    }
}
