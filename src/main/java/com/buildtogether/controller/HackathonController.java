package com.buildtogether.controller;

import com.buildtogether.dto.HackathonDTO;
import com.buildtogether.entity.Hackathon;
import com.buildtogether.repository.HackathonRepository;
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
@RequestMapping("/api/v1/hackathons")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Hackathon Management", description = "APIs for managing hackathons and events")
public class HackathonController {

    private final HackathonRepository hackathonRepository;

    @GetMapping
    @Operation(summary = "Get all hackathons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all hackathons",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HackathonDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Transactional(readOnly = true)
    public ResponseEntity<List<HackathonDTO>> getAllHackathons() {
        log.info("=== GET /hackathons - Fetching all hackathons ===");
        List<Hackathon> hackathons = hackathonRepository.findAll();
        List<HackathonDTO> hackathonDTOs = hackathons.stream()
                .map(HackathonDTO::fromHackathon)
                .collect(Collectors.toList());
        log.info("Successfully fetched {} hackathons", hackathonDTOs.size());
        return ResponseEntity.ok(hackathonDTOs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get hackathon by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched hackathon",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HackathonDTO.class))),
            @ApiResponse(responseCode = "404", description = "Hackathon not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Transactional(readOnly = true)
    public ResponseEntity<HackathonDTO> getHackathonById(@PathVariable Long id) {
        log.info("Fetching hackathon with id: {}", id);
        Hackathon hackathon = hackathonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon", "id", id));
        HackathonDTO hackathonDTO = HackathonDTO.fromHackathon(hackathon);
        return ResponseEntity.ok(hackathonDTO);
    }

    @PostMapping
    @Operation(summary = "Create new hackathon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created hackathon",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HackathonDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HackathonDTO> createHackathon(@Valid @RequestBody Hackathon hackathon) {
        log.info("Creating new hackathon: {}", hackathon.getTitle());
        Hackathon savedHackathon = hackathonRepository.save(hackathon);
        HackathonDTO hackathonDTO = HackathonDTO.fromHackathon(savedHackathon);
        return ResponseEntity.ok(hackathonDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update hackathon by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated hackathon",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HackathonDTO.class))),
            @ApiResponse(responseCode = "404", description = "Hackathon not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HackathonDTO> updateHackathon(@PathVariable Long id, @Valid @RequestBody Hackathon hackathonDetails) {
        log.info("Updating hackathon with id: {}", id);
        Hackathon hackathon = hackathonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon", "id", id));
        
        hackathon.setTitle(hackathonDetails.getTitle());
        hackathon.setDescription(hackathonDetails.getDescription());
        hackathon.setStartDate(hackathonDetails.getStartDate());
        hackathon.setEndDate(hackathonDetails.getEndDate());
        hackathon.setMaxTeamSize(hackathonDetails.getMaxTeamSize());
        
        Hackathon updatedHackathon = hackathonRepository.save(hackathon);
        HackathonDTO hackathonDTO = HackathonDTO.fromHackathon(updatedHackathon);
        return ResponseEntity.ok(hackathonDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete hackathon by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted hackathon"),
            @ApiResponse(responseCode = "404", description = "Hackathon not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Transactional
    public ResponseEntity<Void> deleteHackathon(@PathVariable Long id) {
        log.info("Deleting hackathon with id: {}", id);
        if (!hackathonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hackathon", "id", id);
        }
        hackathonRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    @Operation(summary = "Get active hackathons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched active hackathons",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HackathonDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Transactional(readOnly = true)
    public ResponseEntity<List<HackathonDTO>> getActiveHackathons() {
        log.info("=== GET /hackathons - Fetching active hackathons ===");
        List<Hackathon> activeHackathons = hackathonRepository.findByActiveStatus();
        List<HackathonDTO> activeHackathonDTOs = activeHackathons.stream()
                .map(HackathonDTO::fromHackathon)
                .collect(Collectors.toList());
        log.info("Successfully fetched {} active hackathons", activeHackathonDTOs.size());
        return ResponseEntity.ok(activeHackathonDTOs);
    }
}
