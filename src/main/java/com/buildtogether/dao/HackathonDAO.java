package com.buildtogether.dao;

import com.buildtogether.pojo.Hackathon;
import java.util.Date;
import java.util.List;

/**
 * Data Access Object interface for Hackathon entity
 */
public interface HackathonDAO {
    
    /**
     * Add a new hackathon to the database
     * @param hackathon The hackathon to add
     */
    void addHackathon(Hackathon hackathon);
    
    /**
     * Update an existing hackathon in the database
     * @param hackathon The hackathon to update
     */
    void updateHackathon(Hackathon hackathon);
    
    /**
     * Delete a hackathon from the database
     * @param hackathonId The ID of the hackathon to delete
     */
    void deleteHackathon(int hackathonId);
    
    /**
     * Get a hackathon by its ID
     * @param hackathonId The ID of the hackathon
     * @return The hackathon object, or null if not found
     */
    Hackathon getHackathonById(int hackathonId);
    
    /**
     * Get a hackathon by its name
     * @param name The name of the hackathon
     * @return The hackathon object, or null if not found
     */
    Hackathon getHackathonByName(String name);
    
    /**
     * Get all hackathons from the database
     * @return List of all hackathons
     */
    List<Hackathon> getAllHackathons();
    
    /**
     * Get hackathons by status
     * @param status The status to filter by (UPCOMING, ACTIVE, COMPLETED, CANCELLED)
     * @return List of hackathons with the specified status
     */
    List<Hackathon> getHackathonsByStatus(String status);
    
    /**
     * Get upcoming hackathons (registration deadline not passed)
     * @return List of upcoming hackathons
     */
    List<Hackathon> getUpcomingHackathons();
    
    /**
     * Get active hackathons (currently running)
     * @return List of active hackathons
     */
    List<Hackathon> getActiveHackathons();
    
    /**
     * Get completed hackathons
     * @return List of completed hackathons
     */
    List<Hackathon> getCompletedHackathons();
    
    /**
     * Search hackathons by name (partial match)
     * @param name The partial hackathon name to search for
     * @return List of matching hackathons
     */
    List<Hackathon> searchHackathonsByName(String name);
    
    /**
     * Get hackathons by theme
     * @param theme The theme to filter by
     * @return List of hackathons with the specified theme
     */
    List<Hackathon> getHackathonsByTheme(String theme);
    
    /**
     * Get hackathons within a date range
     * @param startDate The start date
     * @param endDate The end date
     * @return List of hackathons within the date range
     */
    List<Hackathon> getHackathonsByDateRange(Date startDate, Date endDate);
    
    /**
     * Check if a hackathon name already exists
     * @param name The hackathon name to check
     * @return true if hackathon name exists, false otherwise
     */
    boolean hackathonNameExists(String name);
    
    /**
     * Update hackathon status based on current date
     * @param hackathonId The ID of the hackathon
     */
    void updateHackathonStatus(int hackathonId);
}
