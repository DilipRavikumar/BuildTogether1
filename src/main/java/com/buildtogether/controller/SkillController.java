package com.buildtogether.controller;

import com.buildtogether.dto.SkillDTO;
import com.buildtogether.entity.Skill;
import com.buildtogether.repository.SkillRepository;
import com.buildtogether.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Skill Management", description = "APIs for managing skills and competencies")
public class SkillController {

    private final SkillRepository skillRepository;

    @GetMapping
    @Operation(summary = "Get all skills")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all skills",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SkillDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Skills not found", content = @Content)
    })
    @Transactional(readOnly = true)
    public ResponseEntity<List<SkillDTO>> getAllSkills() {
        log.info("Fetching all skills");
        List<Skill> skills = skillRepository.findAll();
        List<SkillDTO> skillDTOs = skills.stream()
                .map(SkillDTO::fromSkill)
                .collect(Collectors.toList());
        return ResponseEntity.ok(skillDTOs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get skill by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the skill",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SkillDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Skill not found", content = @Content)
    })
    @Transactional(readOnly = true)
    public ResponseEntity<SkillDTO> getSkillById(@PathVariable Long id) {
        log.info("Fetching skill with id: {}", id);
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", id));
        SkillDTO skillDTO = SkillDTO.fromSkill(skill);
        return ResponseEntity.ok(skillDTO);
    }

    @PostMapping
    @Operation(summary = "Create new skill")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skill created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SkillDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<SkillDTO> createSkill(@Valid @RequestBody Skill skill) {
        log.info("Creating new skill: {}", skill.getSkillName());
        Skill savedSkill = skillRepository.save(skill);
        SkillDTO skillDTO = SkillDTO.fromSkill(savedSkill);
        return ResponseEntity.ok(skillDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update skill by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skill updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SkillDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Skill not found", content = @Content)
    })
    public ResponseEntity<SkillDTO> updateSkill(@PathVariable Long id, @Valid @RequestBody Skill skillDetails) {
        log.info("Updating skill with id: {}", id);
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", id));
        
        skill.setSkillName(skillDetails.getSkillName());
        
        Skill updatedSkill = skillRepository.save(skill);
        SkillDTO skillDTO = SkillDTO.fromSkill(updatedSkill);
        return ResponseEntity.ok(skillDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete skill by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Skill deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Skill not found", content = @Content)
    })
    @Transactional
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        log.info("Deleting skill with id: {}", id);
        if (!skillRepository.existsById(id)) {
            throw new ResourceNotFoundException("Skill", "id", id);
        }
        skillRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search skills by query")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found skills matching query",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SkillDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Skills not found", content = @Content)
    })
    public ResponseEntity<List<SkillDTO>> searchSkills(@RequestParam String query) {
        log.info("Searching skills with query: {}", query);
        List<Skill> skills = skillRepository.findBySkillNameContainingIgnoreCase(query);
        List<SkillDTO> skillDTOs = skills.stream()
                .map(SkillDTO::fromSkill)
                .collect(Collectors.toList());
        return ResponseEntity.ok(skillDTOs);
    }
}