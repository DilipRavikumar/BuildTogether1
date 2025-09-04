-- =====================================================
-- BuildTogether Oracle Sample Data (IDENTITY Compatible)
-- Comprehensive sample data for testing all functionality
-- =====================================================

-- Clear existing data
DELETE FROM dr_submission;
DELETE FROM dr_join_request;
DELETE FROM dr_team_member;
DELETE FROM dr_user_skill;
DELETE FROM dr_team;
DELETE FROM dr_hackathon;
DELETE FROM dr_skill;
DELETE FROM dr_users;

-- =====================================================
-- INSERT SAMPLE DATA
-- =====================================================

-- Insert Users (IDENTITY columns will auto-generate IDs)
INSERT INTO dr_users (name, email, password, phone, role, github_link, linkedin_link, created_at, updated_at) VALUES
('John Smith', 'john.smith@email.com', 'password123', '+1-555-0101', 'DEVELOPER', 'https://github.com/johnsmith', 'https://linkedin.com/in/johnsmith', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_users (name, email, password, phone, role, github_link, linkedin_link, created_at, updated_at) VALUES
('Sarah Johnson', 'sarah.johnson@email.com', 'password123', '+1-555-0102', 'DESIGNER', 'https://github.com/sarahjohnson', 'https://linkedin.com/in/sarahjohnson', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_users (name, email, password, phone, role, github_link, linkedin_link, created_at, updated_at) VALUES
('Mike Chen', 'mike.chen@email.com', 'password123', '+1-555-0103', 'PRODUCT_MANAGER', 'https://github.com/mikechen', 'https://linkedin.com/in/mikechen', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_users (name, email, password, phone, role, github_link, linkedin_link, created_at, updated_at) VALUES
('Emily Davis', 'emily.davis@email.com', 'password123', '+1-555-0104', 'DATA_SCIENTIST', 'https://github.com/emilydavis', 'https://linkedin.com/in/emilydavis', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_users (name, email, password, phone, role, github_link, linkedin_link, created_at, updated_at) VALUES
('Alex Rodriguez', 'alex.rodriguez@email.com', 'password123', '+1-555-0105', 'DEVOPS', 'https://github.com/alexrodriguez', 'https://linkedin.com/in/alexrodriguez', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_users (name, email, password, phone, role, github_link, linkedin_link, created_at, updated_at) VALUES
('Lisa Wang', 'lisa.wang@email.com', 'password123', '+1-555-0106', 'UI_UX_DESIGNER', 'https://github.com/lisawang', 'https://linkedin.com/in/lisawang', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_users (name, email, password, phone, role, github_link, linkedin_link, created_at, updated_at) VALUES
('David Brown', 'david.brown@email.com', 'password123', '+1-555-0107', 'DEVELOPER', 'https://github.com/davidbrown', 'https://linkedin.com/in/davidbrown', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_users (name, email, password, phone, role, github_link, linkedin_link, created_at, updated_at) VALUES
('Maria Garcia', 'maria.garcia@email.com', 'password123', '+1-555-0108', 'DESIGNER', 'https://github.com/mariagarcia', 'https://linkedin.com/in/mariagarcia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Skills
INSERT INTO dr_skill (skill_name, created_at, updated_at) VALUES
('Java', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_skill (skill_name, created_at, updated_at) VALUES
('Python', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_skill (skill_name, created_at, updated_at) VALUES
('JavaScript', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_skill (skill_name, created_at, updated_at) VALUES
('React', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_skill (skill_name, created_at, updated_at) VALUES
('Node.js', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_skill (skill_name, created_at, updated_at) VALUES
('Spring Boot', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_skill (skill_name, created_at, updated_at) VALUES
('Docker', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_skill (skill_name, created_at, updated_at) VALUES
('AWS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_skill (skill_name, created_at, updated_at) VALUES
('Figma', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_skill (skill_name, created_at, updated_at) VALUES
('Machine Learning', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Hackathons (using user IDs from above)
INSERT INTO dr_hackathon (title, description, start_date, end_date, max_team_size, created_by, created_at, updated_at) VALUES
('Tech Innovation Challenge 2024', 'A 48-hour hackathon focused on innovative solutions for modern problems using cutting-edge technology.', DATE '2024-10-15', DATE '2024-10-17', 5, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_hackathon (title, description, start_date, end_date, max_team_size, created_by, created_at, updated_at) VALUES
('AI for Good Hackathon', 'Build AI-powered solutions that make a positive impact on society and the environment.', DATE '2024-11-20', DATE '2024-11-22', 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_hackathon (title, description, start_date, end_date, max_team_size, created_by, created_at, updated_at) VALUES
('FinTech Revolution', 'Create innovative financial technology solutions for the digital economy.', DATE '2024-12-10', DATE '2024-12-12', 6, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Teams
INSERT INTO dr_team (hackathon_id, created_by, team_name, created_at, updated_at) VALUES
(1, 1, 'Code Warriors', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_team (hackathon_id, created_by, team_name, created_at, updated_at) VALUES
(1, 2, 'Design Masters', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_team (hackathon_id, created_by, team_name, created_at, updated_at) VALUES
(2, 4, 'AI Innovators', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_team (hackathon_id, created_by, team_name, created_at, updated_at) VALUES
(3, 5, 'FinTech Pioneers', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert User Skills
INSERT INTO dr_user_skill (user_id, skill_id, proficiency_level, created_at, updated_at) VALUES
(1, 1, 'EXPERT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_user_skill (user_id, skill_id, proficiency_level, created_at, updated_at) VALUES
(1, 6, 'ADVANCED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_user_skill (user_id, skill_id, proficiency_level, created_at, updated_at) VALUES
(2, 9, 'EXPERT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_user_skill (user_id, skill_id, proficiency_level, created_at, updated_at) VALUES
(2, 3, 'ADVANCED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_user_skill (user_id, skill_id, proficiency_level, created_at, updated_at) VALUES
(3, 2, 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_user_skill (user_id, skill_id, proficiency_level, created_at, updated_at) VALUES
(4, 10, 'EXPERT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_user_skill (user_id, skill_id, proficiency_level, created_at, updated_at) VALUES
(4, 2, 'ADVANCED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_user_skill (user_id, skill_id, proficiency_level, created_at, updated_at) VALUES
(5, 7, 'EXPERT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_user_skill (user_id, skill_id, proficiency_level, created_at, updated_at) VALUES
(5, 8, 'ADVANCED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_user_skill (user_id, skill_id, proficiency_level, created_at, updated_at) VALUES
(6, 9, 'EXPERT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_user_skill (user_id, skill_id, proficiency_level, created_at, updated_at) VALUES
(6, 4, 'ADVANCED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_user_skill (user_id, skill_id, proficiency_level, created_at, updated_at) VALUES
(7, 1, 'ADVANCED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_user_skill (user_id, skill_id, proficiency_level, created_at, updated_at) VALUES
(7, 3, 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_user_skill (user_id, skill_id, proficiency_level, created_at, updated_at) VALUES
(8, 9, 'ADVANCED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_user_skill (user_id, skill_id, proficiency_level, created_at, updated_at) VALUES
(8, 4, 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Team Members
INSERT INTO dr_team_member (team_id, user_id, role_in_team, created_at, updated_at) VALUES
(1, 1, 'TEAM_LEAD', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_team_member (team_id, user_id, role_in_team, created_at, updated_at) VALUES
(1, 7, 'DEVELOPER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_team_member (team_id, user_id, role_in_team, created_at, updated_at) VALUES
(2, 2, 'TEAM_LEAD', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_team_member (team_id, user_id, role_in_team, created_at, updated_at) VALUES
(2, 8, 'DESIGNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_team_member (team_id, user_id, role_in_team, created_at, updated_at) VALUES
(3, 4, 'TEAM_LEAD', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_team_member (team_id, user_id, role_in_team, created_at, updated_at) VALUES
(3, 3, 'PRODUCT_MANAGER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_team_member (team_id, user_id, role_in_team, created_at, updated_at) VALUES
(4, 5, 'TEAM_LEAD', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_team_member (team_id, user_id, role_in_team, created_at, updated_at) VALUES
(4, 6, 'UI_UX_DESIGNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Join Requests
INSERT INTO dr_join_request (team_id, user_id, status, requested_at, updated_at) VALUES
(1, 3, 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_join_request (team_id, user_id, status, requested_at, updated_at) VALUES
(2, 4, 'APPROVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_join_request (team_id, user_id, status, requested_at, updated_at) VALUES
(3, 1, 'REJECTED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dr_join_request (team_id, user_id, status, requested_at, updated_at) VALUES
(4, 2, 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Submissions
INSERT INTO dr_submission (team_id, hackathon_id, project_title, project_description, github_link, demo_link, presentation_link, technologies, features, submitted_at, status, score, judge_comments, submitted_by) VALUES
(1, 1, 'Smart Task Manager', 'An intelligent task management system that uses AI to prioritize and schedule tasks automatically.', 'https://github.com/codewarriors/smart-task-manager', 'https://demo.smarttaskmanager.com', 'https://presentation.smarttaskmanager.com', 'Java, Spring Boot, React, PostgreSQL', 'AI-powered prioritization, Real-time collaboration, Mobile responsive', CURRENT_TIMESTAMP, 'UNDER_REVIEW', 85.50, 'Excellent concept and implementation. Great use of AI for task prioritization.', 1);

INSERT INTO dr_submission (team_id, hackathon_id, project_title, project_description, github_link, demo_link, presentation_link, technologies, features, submitted_at, status, score, judge_comments, submitted_by) VALUES
(2, 1, 'EcoTracker', 'A mobile app that helps users track their carbon footprint and suggests eco-friendly alternatives.', 'https://github.com/designmasters/ecotracker', 'https://demo.ecotracker.com', 'https://presentation.ecotracker.com', 'React Native, Node.js, MongoDB', 'Carbon footprint tracking, Eco-friendly suggestions, Social sharing', CURRENT_TIMESTAMP, 'APPROVED', 92.00, 'Outstanding design and user experience. Very relevant to current environmental concerns.', 2);

INSERT INTO dr_submission (team_id, hackathon_id, project_title, project_description, github_link, demo_link, presentation_link, technologies, features, submitted_at, status, score, judge_comments, submitted_by) VALUES
(3, 2, 'AI Health Assistant', 'An AI-powered health monitoring system that provides personalized health recommendations.', 'https://github.com/aiinnovators/health-assistant', 'https://demo.aihealthassistant.com', 'https://presentation.aihealthassistant.com', 'Python, TensorFlow, Flask, SQLite', 'Health monitoring, AI recommendations, Data visualization', CURRENT_TIMESTAMP, 'SUBMITTED', 0.00, NULL, 4);

INSERT INTO dr_submission (team_id, hackathon_id, project_title, project_description, github_link, demo_link, presentation_link, technologies, features, submitted_at, status, score, judge_comments, submitted_by) VALUES
(4, 3, 'Blockchain Payment Gateway', 'A secure and fast payment gateway using blockchain technology for cryptocurrency transactions.', 'https://github.com/fintechpioneers/blockchain-gateway', 'https://demo.blockchaingateway.com', 'https://presentation.blockchaingateway.com', 'Solidity, Web3.js, Node.js, Ethereum', 'Blockchain transactions, Security features, Real-time processing', CURRENT_TIMESTAMP, 'UNDER_REVIEW', 78.25, 'Good technical implementation. Security features need improvement.', 5);

-- =====================================================
-- COMMIT TRANSACTION
-- =====================================================

COMMIT;

-- =====================================================
-- VERIFICATION QUERIES
-- =====================================================

-- Count records in each table
SELECT 'Users' as table_name, COUNT(*) as record_count FROM dr_users
UNION ALL
SELECT 'Skills', COUNT(*) FROM dr_skill
UNION ALL
SELECT 'Hackathons', COUNT(*) FROM dr_hackathon
UNION ALL
SELECT 'Teams', COUNT(*) FROM dr_team
UNION ALL
SELECT 'User Skills', COUNT(*) FROM dr_user_skill
UNION ALL
SELECT 'Team Members', COUNT(*) FROM dr_team_member
UNION ALL
SELECT 'Join Requests', COUNT(*) FROM dr_join_request
UNION ALL
SELECT 'Submissions', COUNT(*) FROM dr_submission;

-- Sample data verification
SELECT 'Sample Users:' as info FROM dual;
SELECT id, name, email, role FROM dr_users ORDER BY id;

SELECT 'Sample Skills:' as info FROM dual;
SELECT id, skill_name FROM dr_skill ORDER BY id;

SELECT 'Sample Hackathons:' as info FROM dual;
SELECT id, title, start_date, end_date FROM dr_hackathon ORDER BY id;

SELECT 'Sample Teams:' as info FROM dual;
SELECT id, team_name, hackathon_id FROM dr_team ORDER BY id;

PROMPT 'Oracle sample data inserted successfully (IDENTITY compatible)!';