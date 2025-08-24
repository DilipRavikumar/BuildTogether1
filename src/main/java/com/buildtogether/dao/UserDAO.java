package com.buildtogether.dao;

import com.buildtogether.pojo.User;
import java.util.List;

/**
 * Data Access Object interface for User entity
 */
public interface UserDAO {
    
    /**
     * Add a new user to the database
     * @param user The user to add
     */
    void addUser(User user);
    
    /**
     * Update an existing user in the database
     * @param user The user to update
     */
    void updateUser(User user);
    
    /**
     * Delete a user from the database
     * @param userId The ID of the user to delete
     */
    void deleteUser(int userId);
    
    /**
     * Get a user by their ID
     * @param userId The ID of the user
     * @return The user object, or null if not found
     */
    User getUserById(int userId);
    
    /**
     * Get a user by their email
     * @param email The email to search for
     * @return The user object, or null if not found
     */
    User getUserByEmail(String email);
    
    /**
     * Get all users from the database
     * @return List of all users
     */
    List<User> getAllUsers();
    
    /**
     * Get users by role
     * @param role The role to search for
     * @return List of users with the specified role
     */
    List<User> getUsersByRole(String role);
    
    /**
     * Authenticate a user with email and password
     * @param email The email
     * @param password The password
     * @return The user object if authentication successful, null otherwise
     */
    User authenticateUser(String email, String password);
    
    /**
     * Check if an email already exists
     * @param email The email to check
     * @return true if email exists, false otherwise
     */
    boolean emailExists(String email);
    
    /**
     * Search users by name (partial match)
     * @param name The partial name to search for
     * @return List of matching users
     */
    List<User> searchUsersByName(String name);
}
