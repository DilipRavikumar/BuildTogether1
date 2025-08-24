package com.buildtogether.service;

import com.buildtogether.dao.UserDAO;
import com.buildtogether.dao.impl.UserDAOImpl;
import com.buildtogether.pojo.User;

import java.util.List;

/**
 * Service class for user-related business logic
 */
public class UserService {
    
    private final UserDAO userDAO;
    
    public UserService() {
        this.userDAO = new UserDAOImpl();
    }
    
    /**
     * Register a new user
     * @param user The user to register
     * @return Success message or error message
     */
    public String registerUser(User user) {
        try {
            // Validate user data
            String validationResult = validateUserData(user);
            if (!"OK".equals(validationResult)) {
                return validationResult;
            }
            

            
            // Check if email already exists
            if (userDAO.emailExists(user.getEmail())) {
                return "Email already exists. Please use a different email address.";
            }
            
            // Add user to database
            userDAO.addUser(user);
            return "User registered successfully with ID: " + user.getUserId();
            
        } catch (Exception e) {
            System.err.println("Error registering user: " + e.getMessage());
            return "Error registering user: " + e.getMessage();
        }
    }
    
    /**
     * Authenticate user login
     * @param email The email
     * @param password The password
     * @return User object if authentication successful, null otherwise
     */
    public User loginUser(String email, String password) {
        try {
            if (email == null || email.trim().isEmpty()) {
                System.out.println("Email cannot be empty");
                return null;
            }
            
            if (password == null || password.trim().isEmpty()) {
                System.out.println("Password cannot be empty");
                return null;
            }
            
            User user = userDAO.authenticateUser(email.trim(), password);
            
            if (user != null) {
                System.out.println("Login successful for user: " + user.getName());
            } else {
                System.out.println("Invalid email or password");
            }
            
            return user;
            
        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Update user profile
     * @param user The user to update
     * @return Success message or error message
     */
    public String updateUserProfile(User user) {
        try {
            // Validate user data
            String validationResult = validateUserData(user);
            if (!"OK".equals(validationResult)) {
                return validationResult;
            }
            
            // Check if user exists
            User existingUser = userDAO.getUserById(user.getUserId());
            if (existingUser == null) {
                return "User not found with ID: " + user.getUserId();
            }
            

            
            // Check if email is being changed and if it already exists
            if (!existingUser.getEmail().equals(user.getEmail()) && 
                userDAO.emailExists(user.getEmail())) {
                return "Email already exists. Please use a different email address.";
            }
            
            // Update user
            userDAO.updateUser(user);
            return "User profile updated successfully";
            
        } catch (Exception e) {
            System.err.println("Error updating user profile: " + e.getMessage());
            return "Error updating user profile: " + e.getMessage();
        }
    }
    
    /**
     * Delete user account
     * @param userId The ID of the user to delete
     * @return Success message or error message
     */
    public String deleteUser(int userId) {
        try {
            // Check if user exists
            User user = userDAO.getUserById(userId);
            if (user == null) {
                return "User not found with ID: " + userId;
            }
            
            // Delete user
            userDAO.deleteUser(userId);
            return "User deleted successfully";
            
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return "Error deleting user: " + e.getMessage();
        }
    }
    
    /**
     * Get user by ID
     * @param userId The ID of the user
     * @return User object or null if not found
     */
    public User getUserById(int userId) {
        try {
            return userDAO.getUserById(userId);
        } catch (Exception e) {
            System.err.println("Error getting user by ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get user by email
     * @param email The email
     * @return User object or null if not found
     */
    public User getUserByEmail(String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return null;
            }
            return userDAO.getUserByEmail(email.trim());
        } catch (Exception e) {
            System.err.println("Error getting user by email: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get all users
     * @return List of all users
     */
    public List<User> getAllUsers() {
        try {
            return userDAO.getAllUsers();
        } catch (Exception e) {
            System.err.println("Error getting all users: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get users by role
     * @param role The role to filter by
     * @return List of users with the specified role
     */
    public List<User> getUsersByRole(String role) {
        try {
            if (role == null || role.trim().isEmpty()) {
                return null;
            }
            return userDAO.getUsersByRole(role.trim());
        } catch (Exception e) {
            System.err.println("Error getting users by role: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Search users by name (partial match)
     * @param name The partial name to search for
     * @return List of matching users
     */
    public List<User> searchUsersByName(String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return null;
            }
            return userDAO.searchUsersByName(name.trim());
        } catch (Exception e) {
            System.err.println("Error searching users by name: " + e.getMessage());
            return null;
        }
    }
    

    
    /**
     * Validate user data
     * @param user The user to validate
     * @return "OK" if valid, error message otherwise
     */
    private String validateUserData(User user) {
        if (user == null) {
            return "User object cannot be null";
        }
        
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            return "Name cannot be empty";
        }
        
        if (user.getName().length() < 2 || user.getName().length() > 100) {
            return "Name must be between 2 and 100 characters";
        }
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return "Email cannot be empty";
        }
        
        if (!isValidEmail(user.getEmail())) {
            return "Invalid email format";
        }
        
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return "Password cannot be empty";
        }
        
        if (user.getPassword().length() < 6) {
            return "Password must be at least 6 characters long";
        }
        

        
        return "OK";
    }
    
    /**
     * Validate email format
     * @param email The email to validate
     * @return true if valid email format, false otherwise
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
}

