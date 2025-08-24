package com.buildtogether;

import com.buildtogether.util.DBConnection;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Simple test class to verify database connection
 */
public class DBConnectionTest {
    
    @Test
    public void testDatabaseConnection() {
        try {
            DBConnection dbConnection = DBConnection.getInstance();
            boolean isConnected = dbConnection.testConnection();
            
            // This test will pass if database is properly configured
            // and fail if database is not accessible
            assertTrue("Database connection should be successful", isConnected);
            
            System.out.println("Database connection test passed!");
            
        } catch (Exception e) {
            fail("Database connection test failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testSingletonPattern() {
        DBConnection instance1 = DBConnection.getInstance();
        DBConnection instance2 = DBConnection.getInstance();
        
        // Verify singleton pattern works
        assertSame("DBConnection should be a singleton", instance1, instance2);
    }
}
