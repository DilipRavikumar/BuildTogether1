package com.buildtogether.service;

import com.buildtogether.dao.HackathonDAO;
import com.buildtogether.dao.impl.HackathonDAOImpl;
import com.buildtogether.pojo.Hackathon;

import java.util.Date;
import java.util.List;

/**
 * Service class for hackathon-related business logic
 */
public class HackathonService {
    
    private final HackathonDAO hackathonDAO;
    
    public HackathonService() {
        this.hackathonDAO = new HackathonDAOImpl();
    }
    
    /**
     * Create a new hackathon
     * @param hackathon The hackathon to create
     * @return Success message or error message
     */
    public String createHackathon(Hackathon hackathon) {
        try {
            // Validate hackathon data
            String validationResult = validateHackathonData(hackathon);
            if (!"OK".equals(validationResult)) {
                return validationResult;
            }
            
            // Check if hackathon name already exists
            if (hackathonDAO.hackathonNameExists(hackathon.getTitle())) {
                return "Hackathon with this title already exists. Please use a different title.";
            }
            
            // Add hackathon to database
            hackathonDAO.addHackathon(hackathon);
            return "Hackathon created successfully with ID: " + hackathon.getHackathonId();
            
        } catch (Exception e) {
            System.err.println("Error creating hackathon: " + e.getMessage());
            return "Error creating hackathon: " + e.getMessage();
        }
    }
    
    /**
     * Update an existing hackathon
     * @param hackathon The hackathon to update
     * @return Success message or error message
     */
    public String updateHackathon(Hackathon hackathon) {
        try {
            // Validate hackathon data
            String validationResult = validateHackathonData(hackathon);
            if (!"OK".equals(validationResult)) {
                return validationResult;
            }
            
            // Check if hackathon exists
            Hackathon existingHackathon = hackathonDAO.getHackathonById(hackathon.getHackathonId());
            if (existingHackathon == null) {
                return "Hackathon not found with ID: " + hackathon.getHackathonId();
            }
            
            // Check if title is being changed and if it already exists
            if (!existingHackathon.getTitle().equals(hackathon.getTitle()) && 
                hackathonDAO.hackathonNameExists(hackathon.getTitle())) {
                return "Hackathon with this title already exists. Please use a different title.";
            }
            
            // Update hackathon
            hackathonDAO.updateHackathon(hackathon);
            return "Hackathon updated successfully";
            
        } catch (Exception e) {
            System.err.println("Error updating hackathon: " + e.getMessage());
            return "Error updating hackathon: " + e.getMessage();
        }
    }
    
    /**
     * Delete a hackathon
     * @param hackathonId The ID of the hackathon to delete
     * @return Success message or error message
     */
    public String deleteHackathon(int hackathonId) {
        try {
            // Check if hackathon exists
            Hackathon hackathon = hackathonDAO.getHackathonById(hackathonId);
            if (hackathon == null) {
                return "Hackathon not found with ID: " + hackathonId;
            }
            
            // Delete hackathon
            hackathonDAO.deleteHackathon(hackathonId);
            return "Hackathon deleted successfully";
            
        } catch (Exception e) {
            System.err.println("Error deleting hackathon: " + e.getMessage());
            return "Error deleting hackathon: " + e.getMessage();
        }
    }
    
    /**
     * Get hackathon by ID
     * @param hackathonId The ID of the hackathon
     * @return Hackathon object or null if not found
     */
    public Hackathon getHackathonById(int hackathonId) {
        try {
            return hackathonDAO.getHackathonById(hackathonId);
        } catch (Exception e) {
            System.err.println("Error getting hackathon by ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get hackathon by name
     * @param name The name of the hackathon
     * @return Hackathon object or null if not found
     */
    public Hackathon getHackathonByName(String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return null;
            }
            return hackathonDAO.getHackathonByName(name.trim());
        } catch (Exception e) {
            System.err.println("Error getting hackathon by name: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get all hackathons
     * @return List of all hackathons
     */
    public List<Hackathon> getAllHackathons() {
        try {
            return hackathonDAO.getAllHackathons();
        } catch (Exception e) {
            System.err.println("Error getting all hackathons: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get hackathons by status
     * @param status The status to filter by
     * @return List of hackathons with the specified status
     */
    public List<Hackathon> getHackathonsByStatus(String status) {
        try {
            if (status == null || status.trim().isEmpty()) {
                return null;
            }
            return hackathonDAO.getHackathonsByStatus(status.trim());
        } catch (Exception e) {
            System.err.println("Error getting hackathons by status: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get upcoming hackathons
     * @return List of upcoming hackathons
     */
    public List<Hackathon> getUpcomingHackathons() {
        try {
            return hackathonDAO.getUpcomingHackathons();
        } catch (Exception e) {
            System.err.println("Error getting upcoming hackathons: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get active hackathons
     * @return List of active hackathons
     */
    public List<Hackathon> getActiveHackathons() {
        try {
            return hackathonDAO.getActiveHackathons();
        } catch (Exception e) {
            System.err.println("Error getting active hackathons: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get completed hackathons
     * @return List of completed hackathons
     */
    public List<Hackathon> getCompletedHackathons() {
        try {
            return hackathonDAO.getCompletedHackathons();
        } catch (Exception e) {
            System.err.println("Error getting completed hackathons: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Search hackathons by name
     * @param name The partial name to search for
     * @return List of matching hackathons
     */
    public List<Hackathon> searchHackathonsByName(String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return null;
            }
            return hackathonDAO.searchHackathonsByName(name.trim());
        } catch (Exception e) {
            System.err.println("Error searching hackathons by name: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get hackathons by theme
     * @param theme The theme to filter by
     * @return List of hackathons with the specified theme
     */
    public List<Hackathon> getHackathonsByTheme(String theme) {
        try {
            if (theme == null || theme.trim().isEmpty()) {
                return null;
            }
            return hackathonDAO.getHackathonsByTheme(theme.trim());
        } catch (Exception e) {
            System.err.println("Error getting hackathons by theme: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get hackathons within a date range
     * @param startDate The start date
     * @param endDate The end date
     * @return List of hackathons within the date range
     */
    public List<Hackathon> getHackathonsByDateRange(Date startDate, Date endDate) {
        try {
            if (startDate == null || endDate == null) {
                return null;
            }
            if (startDate.after(endDate)) {
                return null; // Invalid date range
            }
            return hackathonDAO.getHackathonsByDateRange(startDate, endDate);
        } catch (Exception e) {
            System.err.println("Error getting hackathons by date range: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Update hackathon status based on current date
     * @param hackathonId The ID of the hackathon
     * @return Success message or error message
     */
    public String updateHackathonStatus(int hackathonId) {
        try {
            // Check if hackathon exists
            Hackathon hackathon = hackathonDAO.getHackathonById(hackathonId);
            if (hackathon == null) {
                return "Hackathon not found with ID: " + hackathonId;
            }
            
            // Update status
            hackathonDAO.updateHackathonStatus(hackathonId);
            return "Hackathon status updated successfully";
            
        } catch (Exception e) {
            System.err.println("Error updating hackathon status: " + e.getMessage());
            return "Error updating hackathon status: " + e.getMessage();
        }
    }
    
    /**
     * Get hackathon status based on dates
     * @param hackathon The hackathon to check
     * @return Status string (UPCOMING, ACTIVE, COMPLETED)
     */
    public String getHackathonStatus(Hackathon hackathon) {
        if (hackathon == null) {
            return "UNKNOWN";
        }
        
        Date now = new Date();
        Date startDate = hackathon.getStartDate();
        Date endDate = hackathon.getEndDate();
        
        if (now.before(startDate)) {
            return "UPCOMING";
        } else if (now.after(endDate)) {
            return "COMPLETED";
        } else {
            return "ACTIVE";
        }
    }
    
    /**
     * Validate hackathon data
     * @param hackathon The hackathon to validate
     * @return "OK" if valid, error message otherwise
     */
    private String validateHackathonData(Hackathon hackathon) {
        if (hackathon == null) {
            return "Hackathon object cannot be null";
        }
        
        if (hackathon.getTitle() == null || hackathon.getTitle().trim().isEmpty()) {
            return "Title cannot be empty";
        }
        
        if (hackathon.getTitle().length() < 3 || hackathon.getTitle().length() > 200) {
            return "Title must be between 3 and 200 characters";
        }
        
        if (hackathon.getDescription() == null || hackathon.getDescription().trim().isEmpty()) {
            return "Description cannot be empty";
        }
        
        if (hackathon.getStartDate() == null) {
            return "Start date cannot be null";
        }
        
        if (hackathon.getEndDate() == null) {
            return "End date cannot be null";
        }
        
        if (hackathon.getStartDate().after(hackathon.getEndDate())) {
            return "Start date cannot be after end date";
        }
        
        if (hackathon.getMaxTeamSize() <= 0) {
            return "Maximum team size must be greater than 0";
        }
        
        if (hackathon.getMaxTeamSize() > 20) {
            return "Maximum team size cannot exceed 20";
        }
        
        return "OK";
    }
}
