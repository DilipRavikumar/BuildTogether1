package com.buildtogether.dao.impl;

import com.buildtogether.dao.SubmissionDAO;
import com.buildtogether.pojo.Submission;
import com.buildtogether.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of SubmissionDAO
 */
public class SubmissionDAOImpl implements SubmissionDAO {
    
    @Override
    public boolean addSubmission(Submission submission) {
        String sql = "INSERT INTO submission (team_id, hackathon_id, project_title, project_description, " +
                    "github_link, demo_link, presentation_link, technologies, features, submitted_at, status, score) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, submission.getTeamId());
            pstmt.setInt(2, submission.getHackathonId());
            pstmt.setString(3, submission.getProjectTitle());
            pstmt.setString(4, submission.getProjectDescription());
            pstmt.setString(5, submission.getGithubLink());
            pstmt.setString(6, submission.getDemoLink());
            pstmt.setString(7, submission.getPresentationLink());
            pstmt.setString(8, submission.getTechnologies());
            pstmt.setString(9, submission.getFeatures());
            pstmt.setTimestamp(10, new Timestamp(submission.getSubmittedAt().getTime()));
            pstmt.setString(11, submission.getStatus());
            pstmt.setDouble(12, submission.getScore());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    submission.setSubmissionId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding submission: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean updateSubmission(Submission submission) {
        String sql = "UPDATE submission SET team_id = ?, hackathon_id = ?, project_title = ?, " +
                    "project_description = ?, github_link = ?, demo_link = ?, presentation_link = ?, " +
                    "technologies = ?, features = ?, submitted_at = ?, status = ?, score = ?, judge_comments = ? " +
                    "WHERE submission_id = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, submission.getTeamId());
            pstmt.setInt(2, submission.getHackathonId());
            pstmt.setString(3, submission.getProjectTitle());
            pstmt.setString(4, submission.getProjectDescription());
            pstmt.setString(5, submission.getGithubLink());
            pstmt.setString(6, submission.getDemoLink());
            pstmt.setString(7, submission.getPresentationLink());
            pstmt.setString(8, submission.getTechnologies());
            pstmt.setString(9, submission.getFeatures());
            pstmt.setTimestamp(10, new Timestamp(submission.getSubmittedAt().getTime()));
            pstmt.setString(11, submission.getStatus());
            pstmt.setDouble(12, submission.getScore());
            pstmt.setString(13, submission.getJudgeComments());
            pstmt.setInt(14, submission.getSubmissionId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating submission: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean deleteSubmission(int submissionId) {
        String sql = "DELETE FROM submission WHERE submission_id = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, submissionId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting submission: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public Submission getSubmissionById(int submissionId) {
        String sql = "SELECT * FROM submission WHERE submission_id = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, submissionId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToSubmission(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting submission by ID: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public List<Submission> getAllSubmissions() {
        String sql = "SELECT * FROM submission ORDER BY submitted_at DESC";
        List<Submission> submissions = new ArrayList<>();
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                submissions.add(mapResultSetToSubmission(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all submissions: " + e.getMessage());
        }
        return submissions;
    }
    
    @Override
    public List<Submission> getSubmissionsByTeamId(int teamId) {
        String sql = "SELECT * FROM submission WHERE team_id = ? ORDER BY submitted_at DESC";
        List<Submission> submissions = new ArrayList<>();
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, teamId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                submissions.add(mapResultSetToSubmission(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting submissions by team ID: " + e.getMessage());
        }
        return submissions;
    }
    
    @Override
    public List<Submission> getSubmissionsByHackathonId(int hackathonId) {
        String sql = "SELECT * FROM submission WHERE hackathon_id = ? ORDER BY score DESC, submitted_at DESC";
        List<Submission> submissions = new ArrayList<>();
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, hackathonId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                submissions.add(mapResultSetToSubmission(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting submissions by hackathon ID: " + e.getMessage());
        }
        return submissions;
    }
    
    @Override
    public List<Submission> getSubmissionsByStatus(String status) {
        String sql = "SELECT * FROM submission WHERE status = ? ORDER BY submitted_at DESC";
        List<Submission> submissions = new ArrayList<>();
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                submissions.add(mapResultSetToSubmission(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting submissions by status: " + e.getMessage());
        }
        return submissions;
    }
    
    @Override
    public List<Submission> getSubmissionsByScoreRange(double minScore, double maxScore) {
        String sql = "SELECT * FROM submission WHERE score BETWEEN ? AND ? ORDER BY score DESC";
        List<Submission> submissions = new ArrayList<>();
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, minScore);
            pstmt.setDouble(2, maxScore);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                submissions.add(mapResultSetToSubmission(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting submissions by score range: " + e.getMessage());
        }
        return submissions;
    }
    
    @Override
    public List<Submission> getTopSubmissions(int limit) {
        String sql = "SELECT * FROM submission WHERE score > 0 ORDER BY score DESC LIMIT ?";
        List<Submission> submissions = new ArrayList<>();
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                submissions.add(mapResultSetToSubmission(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting top submissions: " + e.getMessage());
        }
        return submissions;
    }
    
    @Override
    public boolean teamHasSubmitted(int teamId, int hackathonId) {
        String sql = "SELECT COUNT(*) FROM submission WHERE team_id = ? AND hackathon_id = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, teamId);
            pstmt.setInt(2, hackathonId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking if team has submitted: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean updateSubmissionStatus(int submissionId, String status) {
        String sql = "UPDATE submission SET status = ? WHERE submission_id = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, submissionId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating submission status: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean updateSubmissionScore(int submissionId, double score, String comments) {
        String sql = "UPDATE submission SET score = ?, judge_comments = ? WHERE submission_id = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, score);
            pstmt.setString(2, comments);
            pstmt.setInt(3, submissionId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating submission score: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public List<Submission> searchSubmissionsByTitle(String title) {
        String sql = "SELECT * FROM submission WHERE project_title LIKE ? ORDER BY submitted_at DESC";
        List<Submission> submissions = new ArrayList<>();
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                submissions.add(mapResultSetToSubmission(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching submissions by title: " + e.getMessage());
        }
        return submissions;
    }
    
    @Override
    public List<Submission> getSubmissionsByTechnologies(String technologies) {
        String sql = "SELECT * FROM submission WHERE technologies LIKE ? ORDER BY submitted_at DESC";
        List<Submission> submissions = new ArrayList<>();
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + technologies + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                submissions.add(mapResultSetToSubmission(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting submissions by technologies: " + e.getMessage());
        }
        return submissions;
    }
    
    @Override
    public boolean deleteAllSubmissionsForTeam(int teamId) {
        String sql = "DELETE FROM submission WHERE team_id = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, teamId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting all submissions for team: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean deleteAllSubmissionsForHackathon(int hackathonId) {
        String sql = "DELETE FROM submission WHERE hackathon_id = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, hackathonId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting all submissions for hackathon: " + e.getMessage());
        }
        return false;
    }
    
    private Submission mapResultSetToSubmission(ResultSet rs) throws SQLException {
        Submission submission = new Submission();
        submission.setSubmissionId(rs.getInt("submission_id"));
        submission.setTeamId(rs.getInt("team_id"));
        submission.setHackathonId(rs.getInt("hackathon_id"));
        submission.setProjectTitle(rs.getString("project_title"));
        submission.setProjectDescription(rs.getString("project_description"));
        submission.setGithubLink(rs.getString("github_link"));
        submission.setDemoLink(rs.getString("demo_link"));
        submission.setPresentationLink(rs.getString("presentation_link"));
        submission.setTechnologies(rs.getString("technologies"));
        submission.setFeatures(rs.getString("features"));
        submission.setSubmittedAt(rs.getTimestamp("submitted_at"));
        submission.setStatus(rs.getString("status"));
        submission.setScore(rs.getDouble("score"));
        submission.setJudgeComments(rs.getString("judge_comments"));
        return submission;
    }
}
