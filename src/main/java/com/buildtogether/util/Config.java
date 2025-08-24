package com.buildtogether.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration utility class for application settings
 */
public class Config {
    
    private static final String CONFIG_FILE = "config.properties";
    private static Properties properties;
    
    // Database configuration
    public static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    public static final String DB_USERNAME = "buildtogether";
    public static final String DB_PASSWORD = "password";
    
    // Application settings
    public static final int DEFAULT_MAX_TEAM_SIZE = 5;
    public static final int DEFAULT_MAX_HACKATHON_TEAMS = 50;
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    // File paths
    public static final String SQL_SCRIPTS_DIR = "sql/";
    public static final String LOGS_DIR = "logs/";
    
    static {
        loadProperties();
    }
    
    /**
     * Load configuration properties from file
     */
    private static void loadProperties() {
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(CONFIG_FILE);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            System.out.println("Config file not found, using default values: " + e.getMessage());
            // Use default values if config file is not found
        }
    }
    
    /**
     * Get property value
     * @param key The property key
     * @return The property value, or null if not found
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Get property value with default
     * @param key The property key
     * @param defaultValue The default value if key not found
     * @return The property value or default value
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Get integer property value
     * @param key The property key
     * @param defaultValue The default value if key not found or invalid
     * @return The integer property value or default value
     */
    public static int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.err.println("Invalid integer value for property " + key + ": " + value);
            }
        }
        return defaultValue;
    }
    
    /**
     * Get boolean property value
     * @param key The property key
     * @param defaultValue The default value if key not found
     * @return The boolean property value or default value
     */
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            return Boolean.parseBoolean(value);
        }
        return defaultValue;
    }
    
    /**
     * Get database URL from configuration
     * @return Database URL
     */
    public static String getDatabaseUrl() {
        return getProperty("database.url", DB_URL);
    }
    
    /**
     * Get database username from configuration
     * @return Database username
     */
    public static String getDatabaseUsername() {
        return getProperty("database.username", DB_USERNAME);
    }
    
    /**
     * Get database password from configuration
     * @return Database password
     */
    public static String getDatabasePassword() {
        return getProperty("database.password", DB_PASSWORD);
    }
    
    /**
     * Get maximum team size from configuration
     * @return Maximum team size
     */
    public static int getMaxTeamSize() {
        return getIntProperty("team.max_size", DEFAULT_MAX_TEAM_SIZE);
    }
    
    /**
     * Get maximum hackathon teams from configuration
     * @return Maximum hackathon teams
     */
    public static int getMaxHackathonTeams() {
        return getIntProperty("hackathon.max_teams", DEFAULT_MAX_HACKATHON_TEAMS);
    }
    
    /**
     * Get date format from configuration
     * @return Date format string
     */
    public static String getDateFormat() {
        return getProperty("app.date_format", DEFAULT_DATE_FORMAT);
    }
    
    /**
     * Check if debug mode is enabled
     * @return true if debug mode is enabled
     */
    public static boolean isDebugMode() {
        return getBooleanProperty("app.debug", false);
    }
    
    /**
     * Get log level from configuration
     * @return Log level
     */
    public static String getLogLevel() {
        return getProperty("app.log_level", "INFO");
    }
}
