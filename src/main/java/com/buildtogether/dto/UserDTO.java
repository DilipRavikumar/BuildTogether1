package com.buildtogether.dto;

import com.buildtogether.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private User.UserRole role;
    private String githubLink;
    private String linkedinLink;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Static factory method to convert from User entity
    public static UserDTO fromUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setGithubLink(user.getGithubLink());
        dto.setLinkedinLink(user.getLinkedinLink());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}
