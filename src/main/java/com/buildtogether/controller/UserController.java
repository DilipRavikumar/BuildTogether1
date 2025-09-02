package com.buildtogether.controller;

import com.buildtogether.entity.User;
import com.buildtogether.repository.UserRepository;
import com.buildtogether.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("=== GET /users - Fetching all users ===");
        List<User> users = userRepository.findAll();
        log.info("Successfully fetched {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("=== GET /users/{} - Fetching user by ID ===", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        log.info("Successfully fetched user: {} (ID: {})", user.getName(), user.getId());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        log.info("Fetching user with email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        log.info("Fetching users with role: {}", role);
        User.UserRole userRole = User.UserRole.valueOf(role.toUpperCase());
        List<User> users = userRepository.findByRole(userRole);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        log.info("Searching users with query: {}", query);
        List<User> users = userRepository.findByNameContainingOrEmailContaining(query);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        log.info("=== POST /users - Creating new user ===");
        log.info("User details: email={}, name={}, role={}", user.getEmail(), user.getName(), user.getRole());
        
        if (userRepository.existsByEmail(user.getEmail())) {
            log.warn("User creation failed: Email already exists - {}", user.getEmail());
            throw new ResourceNotFoundException("User already exists with email: " + user.getEmail());
        }
        
        User savedUser = userRepository.save(user);
        log.info("Successfully created user: {} (ID: {})", savedUser.getName(), savedUser.getId());
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
        log.info("Updating user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPhone(userDetails.getPhone());
        user.setRole(userDetails.getRole());
        user.setGithubLink(userDetails.getGithubLink());
        user.setLinkedinLink(userDetails.getLinkedinLink());
        
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Deleting user with id: {}", id);
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<User>> getUsersByTeam(@PathVariable Long teamId) {
        log.info("Fetching users for team: {}", teamId);
        List<User> users = userRepository.findByTeamId(teamId);
        return ResponseEntity.ok(users);
    }
}
