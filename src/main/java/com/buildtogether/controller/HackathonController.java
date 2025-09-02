package com.buildtogether.controller;

import com.buildtogether.entity.Hackathon;
import com.buildtogether.repository.HackathonRepository;
import com.buildtogether.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/hackathons")
@RequiredArgsConstructor
@Slf4j
public class HackathonController {

    private final HackathonRepository hackathonRepository;

    @GetMapping
    public ResponseEntity<List<Hackathon>> getAllHackathons() {
        log.info("=== GET /hackathons - Fetching all hackathons ===");
        List<Hackathon> hackathons = hackathonRepository.findAll();
        log.info("Successfully fetched {} hackathons", hackathons.size());
        return ResponseEntity.ok(hackathons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hackathon> getHackathonById(@PathVariable Long id) {
        log.info("Fetching hackathon with id: {}", id);
        Hackathon hackathon = hackathonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon", "id", id));
        return ResponseEntity.ok(hackathon);
    }

    @PostMapping
    public ResponseEntity<Hackathon> createHackathon(@Valid @RequestBody Hackathon hackathon) {
        log.info("Creating new hackathon: {}", hackathon.getTitle());
        Hackathon savedHackathon = hackathonRepository.save(hackathon);
        return ResponseEntity.ok(savedHackathon);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hackathon> updateHackathon(@PathVariable Long id, @Valid @RequestBody Hackathon hackathonDetails) {
        log.info("Updating hackathon with id: {}", id);
        Hackathon hackathon = hackathonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon", "id", id));
        
        hackathon.setTitle(hackathonDetails.getTitle());
        hackathon.setDescription(hackathonDetails.getDescription());
        hackathon.setStartDate(hackathonDetails.getStartDate());
        hackathon.setEndDate(hackathonDetails.getEndDate());
        hackathon.setMaxTeamSize(hackathonDetails.getMaxTeamSize());
        
        Hackathon updatedHackathon = hackathonRepository.save(hackathon);
        return ResponseEntity.ok(updatedHackathon);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHackathon(@PathVariable Long id) {
        log.info("Deleting hackathon with id: {}", id);
        if (!hackathonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hackathon", "id", id);
        }
        hackathonRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    public ResponseEntity<List<Hackathon>> getActiveHackathons() {
        log.info("=== GET /hackathons/active - Fetching active hackathons ===");
        List<Hackathon> activeHackathons = hackathonRepository.findByActiveStatus();
        log.info("Successfully fetched {} active hackathons", activeHackathons.size());
        return ResponseEntity.ok(activeHackathons);
    }
}
