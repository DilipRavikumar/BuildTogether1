package com.buildtogether.controller;

import com.buildtogether.entity.UserSkill;
import com.buildtogether.repository.UserSkillRepository;
import com.buildtogether.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user-skills")
@RequiredArgsConstructor
@Slf4j
public class UserSkillController {

    private final UserSkillRepository userSkillRepository;

    @GetMapping
    public ResponseEntity<List<UserSkill>> getAllUserSkills() {
        log.info("Fetching all user skills");
        List<UserSkill> userSkills = userSkillRepository.findAll();
        return ResponseEntity.ok(userSkills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSkill> getUserSkillById(@PathVariable Long id) {
        log.info("Fetching user skill with id: {}", id);
        UserSkill userSkill = userSkillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserSkill", "id", id));
        return ResponseEntity.ok(userSkill);
    }

    @PostMapping
    public ResponseEntity<UserSkill> createUserSkill(@Valid @RequestBody UserSkill userSkill) {
        log.info("Creating new user skill for user: {} and skill: {}", 
                userSkill.getUser().getId(), userSkill.getSkill().getId());
        UserSkill savedUserSkill = userSkillRepository.save(userSkill);
        return ResponseEntity.ok(savedUserSkill);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSkill> updateUserSkill(@PathVariable Long id, @Valid @RequestBody UserSkill userSkillDetails) {
        log.info("Updating user skill with id: {}", id);
        UserSkill userSkill = userSkillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserSkill", "id", id));
        
        userSkill.setProficiencyLevel(userSkillDetails.getProficiencyLevel());
        
        UserSkill updatedUserSkill = userSkillRepository.save(userSkill);
        return ResponseEntity.ok(updatedUserSkill);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserSkill(@PathVariable Long id) {
        log.info("Deleting user skill with id: {}", id);
        if (!userSkillRepository.existsById(id)) {
            throw new ResourceNotFoundException("UserSkill", "id", id);
        }
        userSkillRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserSkill>> getUserSkillsByUser(@PathVariable Long userId) {
        log.info("Fetching skills for user: {}", userId);
        List<UserSkill> userSkills = userSkillRepository.findByUserId(userId);
        return ResponseEntity.ok(userSkills);
    }

    @GetMapping("/skill/{skillId}")
    public ResponseEntity<List<UserSkill>> getUserSkillsBySkill(@PathVariable Long skillId) {
        log.info("Fetching users with skill: {}", skillId);
        List<UserSkill> userSkills = userSkillRepository.findBySkillId(skillId);
        return ResponseEntity.ok(userSkills);
    }

    @GetMapping("/proficiency/{level}")
    public ResponseEntity<List<UserSkill>> getUserSkillsByProficiencyLevel(@PathVariable String level) {
        log.info("Fetching user skills with proficiency level: {}", level);
        UserSkill.ProficiencyLevel proficiencyLevel = UserSkill.ProficiencyLevel.valueOf(level.toUpperCase());
        List<UserSkill> userSkills = userSkillRepository.findByProficiencyLevel(proficiencyLevel);
        return ResponseEntity.ok(userSkills);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkUserSkillExists(@RequestParam Long userId, @RequestParam Long skillId) {
        log.info("Checking if user skill exists for user: {} and skill: {}", userId, skillId);
        boolean exists = userSkillRepository.existsByUserIdAndSkillId(userId, skillId);
        return ResponseEntity.ok(exists);
    }
}