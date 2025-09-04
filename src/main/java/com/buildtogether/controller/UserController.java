package com.buildtogether.controller;

import com.buildtogether.dto.UserDTO;
import com.buildtogether.entity.User;
import com.buildtogether.repository.UserRepository;
import com.buildtogether.exception.ResourceNotFoundException;
import com.buildtogether.exception.ValidationException;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Management", description = "APIs for managing users, skills, and profiles")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all users",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.info("=== GET /users - Fetching all users ===");
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream()
                .map(UserDTO::fromUser)
                .collect(Collectors.toList());
        log.info("Successfully fetched {} users", userDTOs.size());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched user by ID",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Transactional(readOnly = true)
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        log.info("=== GET /users/{} - Fetching user by ID ===", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        UserDTO userDTO = UserDTO.fromUser(user);
        log.info("Successfully fetched user: {} (ID: {})", userDTO.getName(), userDTO.getId());
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched user by email",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Transactional(readOnly = true)
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        log.info("Fetching user with email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        UserDTO userDTO = UserDTO.fromUser(user);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Get users by role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched users by role",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid role value"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserDTO>> getUsersByRole(@PathVariable String role) {
        log.info("Fetching users with role: {}", role);
        try {
            User.UserRole userRole = User.UserRole.valueOf(role.toUpperCase());
            List<User> users = userRepository.findByRole(userRole);
            List<UserDTO> userDTOs = users.stream()
                    .map(UserDTO::fromUser)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userDTOs);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid role value provided: {}", role);
            throw new ValidationException("Invalid role value: " + role + ". Valid role values are: DEVELOPER, DESIGNER, PRODUCT_MANAGER, DEVOPS, DATA_SCIENTIST, UI_UX_DESIGNER");
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search users by name or email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully searched users",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String query) {
        log.info("Searching users with query: {}", query);
        List<User> users = userRepository.findByNameContainingOrEmailContaining(query);
        List<UserDTO> userDTOs = users.stream()
                .map(UserDTO::fromUser)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping
    @Operation(summary = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created user",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "User already exists with email"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody User user) {
        log.info("=== POST /users - Creating new user ===");
        log.info("User details: email={}, name={}, role={}", user.getEmail(), user.getName(), user.getRole());
        
        if (userRepository.existsByEmail(user.getEmail())) {
            log.warn("User creation failed: Email already exists - {}", user.getEmail());
            throw new ResourceNotFoundException("User already exists with email: " + user.getEmail());
        }
        
        User savedUser = userRepository.save(user);
        UserDTO userDTO = UserDTO.fromUser(savedUser);
        log.info("Successfully created user: {} (ID: {})", userDTO.getName(), userDTO.getId());
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated user",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
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
        UserDTO userDTO = UserDTO.fromUser(updatedUser);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("=== DELETE /users/{} - Starting user deletion ===", id);
        
        // Find the user first
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        log.info("Found user: {} (ID: {}), proceeding with deletion", user.getName(), id);
        
        try {
            // Delete the user entity directly (this ensures proper cascade handling)
            userRepository.delete(user);
            
            // Force flush to ensure changes are persisted
            userRepository.flush();
            
            log.info("Successfully deleted user with ID: {}", id);
            
            // Verify deletion
            boolean stillExists = userRepository.existsById(id);
            if (stillExists) {
                log.error("User deletion verification failed: User with ID {} still exists after deletion", id);
                return ResponseEntity.status(500).build();
            } else {
                log.info("User deletion verified: User with ID {} no longer exists", id);
            }
            
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error during user deletion for ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/team/{teamId}")
    @Operation(summary = "Get users by team ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched users by team ID",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<UserDTO>> getUsersByTeam(@PathVariable Long teamId) {
        log.info("Fetching users for team: {}", teamId);
        List<User> users = userRepository.findByTeamId(teamId);
        List<UserDTO> userDTOs = users.stream()
                .map(UserDTO::fromUser)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }
}
