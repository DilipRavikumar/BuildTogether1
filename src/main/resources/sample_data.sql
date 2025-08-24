-- BuildTogether Sample Data for Oracle
-- Oracle SQL Script (Updated for new schema)

-- Insert sample users
INSERT INTO users (name, email, password, phone, role, github_link, linkedin_link) VALUES
('John Doe', 'john.doe@email.com', 'password123', '555-0101', 'Developer', 'https://github.com/johndoe', 'https://linkedin.com/in/johndoe');

INSERT INTO users (name, email, password, phone, role, github_link, linkedin_link) VALUES
('Jane Smith', 'jane.smith@email.com', 'password123', '555-0102', 'Designer', 'https://github.com/janesmith', 'https://linkedin.com/in/janesmith');

INSERT INTO users (name, email, password, phone, role, github_link, linkedin_link) VALUES
('Mike Johnson', 'mike.johnson@email.com', 'password123', '555-0103', 'Full Stack Developer', 'https://github.com/mikejohnson', 'https://linkedin.com/in/mikejohnson');

INSERT INTO users (name, email, password, phone, role, github_link, linkedin_link) VALUES
('Sarah Wilson', 'sarah.wilson@email.com', 'password123', '555-0104', 'Frontend Developer', 'https://github.com/sarahwilson', 'https://linkedin.com/in/sarahwilson');

INSERT INTO users (name, email, password, phone, role, github_link, linkedin_link) VALUES
('David Brown', 'david.brown@email.com', 'password123', '555-0105', 'Data Scientist', 'https://github.com/davidbrown', 'https://linkedin.com/in/davidbrown');

INSERT INTO users (name, email, password, phone, role, github_link, linkedin_link) VALUES
('Lisa Davis', 'lisa.davis@email.com', 'password123', '555-0106', 'DevOps Engineer', 'https://github.com/lisadavis', 'https://linkedin.com/in/lisadavis');

INSERT INTO users (name, email, password, phone, role, github_link, linkedin_link) VALUES
('Tom Miller', 'tom.miller@email.com', 'password123', '555-0107', 'Backend Developer', 'https://github.com/tommiller', 'https://linkedin.com/in/tommiller');

INSERT INTO users (name, email, password, phone, role, github_link, linkedin_link) VALUES
('Amy Garcia', 'amy.garcia@email.com', 'password123', '555-0108', 'Product Manager', 'https://github.com/amygarcia', 'https://linkedin.com/in/amygarcia');

-- Insert sample skills
INSERT INTO skill (skill_name) VALUES ('Java');
INSERT INTO skill (skill_name) VALUES ('Python');
INSERT INTO skill (skill_name) VALUES ('JavaScript');
INSERT INTO skill (skill_name) VALUES ('React');
INSERT INTO skill (skill_name) VALUES ('Node.js');
INSERT INTO skill (skill_name) VALUES ('SQL');
INSERT INTO skill (skill_name) VALUES ('MongoDB');
INSERT INTO skill (skill_name) VALUES ('Docker');
INSERT INTO skill (skill_name) VALUES ('AWS');
INSERT INTO skill (skill_name) VALUES ('UI/UX Design');

-- Insert sample hackathons first (needed for teams)
INSERT INTO hackathon (title, description, start_date, end_date, max_team_size) VALUES
('Innovation Challenge 2024', 'A hackathon focused on innovative solutions for modern problems', 
 TO_DATE('2024-03-15', 'YYYY-MM-DD'), TO_DATE('2024-03-17', 'YYYY-MM-DD'), 5);

INSERT INTO hackathon (title, description, start_date, end_date, max_team_size) VALUES
('Green Tech Hackathon', 'Building sustainable solutions for environmental challenges', 
 TO_DATE('2024-02-20', 'YYYY-MM-DD'), TO_DATE('2024-02-22', 'YYYY-MM-DD'), 4);

INSERT INTO hackathon (title, description, start_date, end_date, max_team_size) VALUES
('AI & Machine Learning Challenge', 'Exploring the future of artificial intelligence', 
 TO_DATE('2024-04-10', 'YYYY-MM-DD'), TO_DATE('2024-04-12', 'YYYY-MM-DD'), 6);

INSERT INTO hackathon (title, description, start_date, end_date, max_team_size) VALUES
('Web Development Sprint', 'Fast-paced web development challenge', 
 TO_DATE('2024-01-25', 'YYYY-MM-DD'), TO_DATE('2024-01-26', 'YYYY-MM-DD'), 4);

-- Insert sample teams
INSERT INTO team (hackathon_id, created_by, team_name) VALUES (1, 1, 'CodeCrafters');
INSERT INTO team (hackathon_id, created_by, team_name) VALUES (1, 2, 'InnovationHub');
INSERT INTO team (hackathon_id, created_by, team_name) VALUES (2, 3, 'TechPioneers');
INSERT INTO team (hackathon_id, created_by, team_name) VALUES (2, 4, 'DigitalDreamers');

-- Insert sample team members
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (1, 1, 'Team Leader');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (1, 2, 'Developer');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (1, 3, 'Developer');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (2, 2, 'Team Leader');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (2, 4, 'Designer');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (2, 5, 'Data Scientist');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (3, 3, 'Team Leader');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (3, 6, 'DevOps Engineer');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (3, 7, 'Backend Developer');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (4, 4, 'Team Leader');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (4, 8, 'Product Manager');

-- Insert sample user skills
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (1, 1, 'EXPERT');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (1, 6, 'ADVANCED');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (2, 2, 'EXPERT');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (2, 3, 'ADVANCED');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (3, 1, 'INTERMEDIATE');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (3, 5, 'ADVANCED');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (4, 4, 'EXPERT');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (4, 10, 'ADVANCED');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (5, 2, 'INTERMEDIATE');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (5, 7, 'BEGINNER');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (6, 8, 'ADVANCED');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (6, 9, 'INTERMEDIATE');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (7, 3, 'EXPERT');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (7, 4, 'ADVANCED');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (8, 10, 'EXPERT');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (8, 4, 'INTERMEDIATE');

-- Insert sample join requests
INSERT INTO join_request (team_id, user_id, status) VALUES (1, 5, 'PENDING');
INSERT INTO join_request (team_id, user_id, status) VALUES (2, 6, 'APPROVED');
INSERT INTO join_request (team_id, user_id, status) VALUES (3, 7, 'PENDING');
INSERT INTO join_request (team_id, user_id, status) VALUES (4, 8, 'REJECTED');

-- Insert sample submissions
INSERT INTO submission (team_id, hackathon_id, project_title, project_description, github_link, demo_link, presentation_link, technologies, features, status, score, judge_comments) VALUES
(1, 1, 'EcoTracker - Environmental Monitoring App', 'A mobile application that tracks environmental data and provides insights for sustainable living', 
 'https://github.com/team1/ecotracker', 'https://ecotracker-demo.herokuapp.com', 'https://slides.com/ecotracker-presentation',
 'React Native, Node.js, MongoDB, AWS', 'Real-time data tracking, AI-powered insights, Community features', 'APPROVED', 85.5, 'Excellent UI/UX design and innovative approach to environmental monitoring');

INSERT INTO submission (team_id, hackathon_id, project_title, project_description, github_link, demo_link, presentation_link, technologies, features, status, score, judge_comments) VALUES
(2, 1, 'SmartCity Solutions Platform', 'An integrated platform for smart city management with IoT integration', 
 'https://github.com/team2/smartcity', 'https://smartcity-demo.netlify.app', 'https://prezi.com/smartcity-overview',
 'React, Python, PostgreSQL, Docker', 'IoT integration, Real-time analytics, Dashboard, API Gateway', 'UNDER_REVIEW', 0.0, NULL);

INSERT INTO submission (team_id, hackathon_id, project_title, project_description, github_link, demo_link, presentation_link, technologies, features, status, score, judge_comments) VALUES
(3, 2, 'GreenEnergy Optimizer', 'AI-powered system for optimizing renewable energy consumption', 
 'https://github.com/team3/greenenergy', 'https://greenenergy-demo.vercel.app', 'https://canva.com/greenenergy-pitch',
 'Python, TensorFlow, Flask, Redis', 'Machine Learning, Energy prediction, Optimization algorithms', 'SUBMITTED', 0.0, NULL);

INSERT INTO submission (team_id, hackathon_id, project_title, project_description, github_link, demo_link, presentation_link, technologies, features, status, score, judge_comments) VALUES
(4, 2, 'WasteManagement AI', 'Intelligent waste sorting and management system using computer vision', 
 'https://github.com/team4/wastemanagement', 'https://waste-ai-demo.streamlit.app', 'https://slideshare.net/waste-ai',
 'Python, OpenCV, TensorFlow, FastAPI', 'Computer Vision, AI classification, Mobile app, Analytics', 'APPROVED', 92.0, 'Outstanding technical implementation with practical real-world application');

INSERT INTO submission (team_id, hackathon_id, project_title, project_description, github_link, demo_link, presentation_link, technologies, features, status, score, judge_comments) VALUES
(1, 3, 'AI-Powered Learning Assistant', 'Personalized learning platform with AI-driven content recommendations', 
 'https://github.com/team1/learning-assistant', 'https://learning-ai-demo.herokuapp.com', 'https://slides.com/learning-ai',
 'React, Python, TensorFlow, PostgreSQL', 'AI recommendations, Progress tracking, Adaptive learning', 'REJECTED', 45.0, 'Good concept but implementation needs improvement');

INSERT INTO submission (team_id, hackathon_id, project_title, project_description, github_link, demo_link, presentation_link, technologies, features, status, score, judge_comments) VALUES
(2, 3, 'Healthcare Analytics Dashboard', 'Comprehensive healthcare data visualization and analytics platform', 
 'https://github.com/team2/healthcare-analytics', 'https://healthcare-demo.netlify.app', 'https://prezi.com/healthcare-analytics',
 'React, D3.js, Node.js, MongoDB', 'Data visualization, Real-time dashboards, Export functionality', 'SUBMITTED', 0.0, NULL);

-- Display success message
SELECT 'Sample data inserted successfully!' AS message FROM DUAL;
SELECT 'Users: 8 sample users created' AS users FROM DUAL;
SELECT 'Skills: 10 different skills added' AS skills FROM DUAL;
SELECT 'Hackathons: 4 hackathons created' AS hackathons FROM DUAL;
SELECT 'Teams: 4 teams created with members' AS teams FROM DUAL;
SELECT 'User Skills: 16 skill assignments added' AS user_skills FROM DUAL;
SELECT 'Join Requests: 4 join requests with different statuses' AS join_requests FROM DUAL;
SELECT 'Submissions: 6 project submissions with various statuses and scores' AS submissions FROM DUAL;
SELECT '' AS empty_line FROM DUAL;
SELECT 'You can now test the application with this sample data!' AS test_message FROM DUAL;
SELECT 'Default login credentials: email = john.doe@email.com, password = password123' AS credentials FROM DUAL;
