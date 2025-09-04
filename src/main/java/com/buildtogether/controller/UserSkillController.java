package com.buildtogether.controller;

import com.buildtogether.dto.UserSkillDTO;
import com.buildtogether.entity.UserSkill;
import com.buildtogether.repository.UserSkillRepository;
import com.buildtogether.exception.ResourceNotFoundException;
import com.buildtogether.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user-skills")
@RequiredArgsConstructor
@Slf4j
public class UserSkillController {

    private final UserSkillRepository userSkillRepository;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserSkillDTO>> getAllUserSkills() {
        log.info("Fetching all user skills");
        List<UserSkill> userSkills = userSkillRepository.findAll();
        List<UserSkillDTO> userSkillDTOs = userSkills.stream()
                .map(UserSkillDTO::fromUserSkill)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userSkillDTOs);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<UserSkillDTO> getUserSkillById(@PathVariable Long id) {
        log.info("Fetching user skill with id: {}", id);
        // Note: This endpoint might need adjustment since UserSkill uses composite key
        // For now, we'll search by userId
        List<UserSkill> userSkills = userSkillRepository.findByUserId(id);
        if (userSkills.isEmpty()) {
            throw new ResourceNotFoundException("UserSkill", "userId", id);
        }
        UserSkillDTO userSkillDTO = UserSkillDTO.fromUserSkill(userSkills.get(0));
        return ResponseEntity.ok(userSkillDTO);
    }

    @PostMapping
    public ResponseEntity<UserSkillDTO> createUserSkill(@Valid @RequestBody UserSkill userSkill) {
        log.info("Creating new user skill for user: {} and skill: {}", 
                userSkill.getUser().getId(), userSkill.getSkill().getId());
        UserSkill savedUserSkill = userSkillRepository.save(userSkill);
        UserSkillDTO userSkillDTO = UserSkillDTO.fromUserSkill(savedUserSkill);
        return ResponseEntity.ok(userSkillDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSkillDTO> updateUserSkill(@PathVariable Long id, @Valid @RequestBody UserSkill userSkillDetails) {
        log.info("Updating user skill with id: {}", id);
        // Note: This endpoint might need adjustment since UserSkill uses composite key
        List<UserSkill> userSkills = userSkillRepository.findByUserId(id);
        if (userSkills.isEmpty()) {
            throw new ResourceNotFoundException("UserSkill", "userId", id);
        }
        UserSkill userSkill = userSkills.get(0);
        
        userSkill.setProficiencyLevel(userSkillDetails.getProficiencyLevel());
        
        UserSkill updatedUserSkill = userSkillRepository.save(userSkill);
        UserSkillDTO userSkillDTO = UserSkillDTO.fromUserSkill(updatedUserSkill);
        return ResponseEntity.ok(userSkillDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUserSkill(@PathVariable Long id) {
        log.info("Deleting user skill with id: {}", id);
        // Note: This endpoint might need adjustment since UserSkill uses composite key
        List<UserSkill> userSkills = userSkillRepository.findByUserId(id);
        if (userSkills.isEmpty()) {
            throw new ResourceNotFoundException("UserSkill", "userId", id);
        }
        userSkillRepository.delete(userSkills.get(0));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserSkillDTO>> getUserSkillsByUser(@PathVariable Long userId) {
        log.info("Fetching skills for user: {}", userId);
        List<UserSkill> userSkills = userSkillRepository.findByUserId(userId);
        List<UserSkillDTO> userSkillDTOs = userSkills.stream()
                .map(UserSkillDTO::fromUserSkill)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userSkillDTOs);
    }

    @GetMapping("/skill/{skillId}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserSkillDTO>> getUserSkillsBySkill(@PathVariable Long skillId) {
        log.info("Fetching users with skill: {}", skillId);
        List<UserSkill> userSkills = userSkillRepository.findBySkillId(skillId);
        List<UserSkillDTO> userSkillDTOs = userSkills.stream()
                .map(UserSkillDTO::fromUserSkill)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userSkillDTOs);
    }

    @GetMapping("/proficiency/{level}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserSkillDTO>> getUserSkillsByProficiencyLevel(@PathVariable String level) {
        log.info("Fetching user skills with proficiency level: {}", level);
        try {
            UserSkill.ProficiencyLevel proficiencyLevel = UserSkill.ProficiencyLevel.valueOf(level.toUpperCase());
            List<UserSkill> userSkills = userSkillRepository.findByProficiencyLevel(proficiencyLevel);
            List<UserSkillDTO> userSkillDTOs = userSkills.stream()
                    .map(UserSkillDTO::fromUserSkill)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userSkillDTOs);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid proficiency level: " + level);
        }
    }

    @GetMapping("/check")
    @Transactional(readOnly = true)
    public ResponseEntity<Boolean> checkUserSkillExists(@RequestParam Long userId, @RequestParam Long skillId) {
        log.info("Checking if user skill exists for user: {} and skill: {}", userId, skillId);
        boolean exists = userSkillRepository.existsByUserIdAndSkillId(userId, skillId);
        return ResponseEntity.ok(exists);
    }
}