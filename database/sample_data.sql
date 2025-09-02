SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

INSERT INTO users (id, name, email, password, phone, role, github_link, linkedin_link) VALUES
(users_seq.NEXTVAL, 'John Doe', 'john.doe@example.com', '$2a$10$encrypted_password_hash', '+1234567890', 'DEVELOPER', 'https://github.com/johndoe', 'https://linkedin.com/in/johndoe');

INSERT INTO users (id, name, email, password, phone, role, github_link, linkedin_link) VALUES
(users_seq.NEXTVAL, 'Jane Smith', 'jane.smith@example.com', '$2a$10$encrypted_password_hash', '+1234567891', 'DESIGNER', 'https://github.com/janesmith', 'https://linkedin.com/in/janesmith');

INSERT INTO users (id, name, email, password, phone, role, github_link, linkedin_link) VALUES
(users_seq.NEXTVAL, 'Mike Johnson', 'mike.johnson@example.com', '$2a$10$encrypted_password_hash', '+1234567892', 'DEVELOPER', 'https://github.com/mikejohnson', 'https://linkedin.com/in/mikejohnson');

INSERT INTO users (id, name, email, password, phone, role, github_link, linkedin_link) VALUES
(users_seq.NEXTVAL, 'Sarah Wilson', 'sarah.wilson@example.com', '$2a$10$encrypted_password_hash', '+1234567893', 'PRODUCT_MANAGER', 'https://github.com/sarahwilson', 'https://linkedin.com/in/sarahwilson');

INSERT INTO users (id, name, email, password, phone, role, github_link, linkedin_link) VALUES
(users_seq.NEXTVAL, 'Alex Brown', 'alex.brown@example.com', '$2a$10$encrypted_password_hash', '+1234567894', 'DEVELOPER', 'https://github.com/alexbrown', 'https://linkedin.com/in/alexbrown');

INSERT INTO users (id, name, email, password, phone, role, github_link, linkedin_link) VALUES
(users_seq.NEXTVAL, 'Emily Davis', 'emily.davis@example.com', '$2a$10$encrypted_password_hash', '+1234567895', 'DESIGNER', 'https://github.com/emilydavis', 'https://linkedin.com/in/emilydavis');

INSERT INTO users (id, name, email, password, phone, role, github_link, linkedin_link) VALUES
(users_seq.NEXTVAL, 'David Lee', 'david.lee@example.com', '$2a$10$encrypted_password_hash', '+1234567896', 'DEVELOPER', 'https://github.com/davidlee', 'https://linkedin.com/in/davidlee');

INSERT INTO users (id, name, email, password, phone, role, github_link, linkedin_link) VALUES
(users_seq.NEXTVAL, 'Lisa Chen', 'lisa.chen@example.com', '$2a$10$encrypted_password_hash', '+1234567897', 'PRODUCT_MANAGER', 'https://github.com/lisachen', 'https://linkedin.com/in/lisachen');

INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'Java');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'Spring Boot');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'React');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'Node.js');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'Python');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'Machine Learning');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'UI/UX Design');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'Product Management');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'DevOps');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'Database Design');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'RESTful APIs');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'Microservices');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'Docker');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'Kubernetes');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'AWS');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'JavaScript');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'TypeScript');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'Angular');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'Vue.js');
INSERT INTO skill (id, skill_name) VALUES (skill_seq.NEXTVAL, 'Mobile Development');

INSERT INTO hackathon (id, title, description, start_date, end_date, max_team_size, created_by) VALUES
(hackathon_seq.NEXTVAL, 'AI Innovation Challenge 2024', 'Build innovative AI solutions for real-world problems', TO_DATE('2024-03-01', 'YYYY-MM-DD'), TO_DATE('2024-03-03', 'YYYY-MM-DD'), 4, 1);

INSERT INTO hackathon (id, title, description, start_date, end_date, max_team_size, created_by) VALUES
(hackathon_seq.NEXTVAL, 'Web3 Development Hackathon', 'Create decentralized applications using blockchain technology', TO_DATE('2024-04-15', 'YYYY-MM-DD'), TO_DATE('2024-04-17', 'YYYY-MM-DD'), 5, 3);

INSERT INTO hackathon (id, title, description, start_date, end_date, max_team_size, created_by) VALUES
(hackathon_seq.NEXTVAL, 'Sustainability Tech Challenge', 'Develop technology solutions for environmental sustainability', TO_DATE('2024-05-20', 'YYYY-MM-DD'), TO_DATE('2024-05-22', 'YYYY-MM-DD'), 4, 2);

INSERT INTO hackathon (id, title, description, start_date, end_date, max_team_size, created_by) VALUES
(hackathon_seq.NEXTVAL, 'Healthcare Innovation Hackathon', 'Build digital health solutions for better patient care', TO_DATE('2024-06-10', 'YYYY-MM-DD'), TO_DATE('2024-06-12', 'YYYY-MM-DD'), 5, 6);

INSERT INTO team (id, hackathon_id, created_by, team_name) VALUES
(team_seq.NEXTVAL, 1, 1, 'AI Pioneers');

INSERT INTO team (id, hackathon_id, created_by, team_name) VALUES
(team_seq.NEXTVAL, 1, 4, 'Smart Solutions');

INSERT INTO team (id, hackathon_id, created_by, team_name) VALUES
(team_seq.NEXTVAL, 2, 3, 'Blockchain Builders');

INSERT INTO team (id, hackathon_id, created_by, team_name) VALUES
(team_seq.NEXTVAL, 2, 7, 'Web3 Warriors');

INSERT INTO team (id, hackathon_id, created_by, team_name) VALUES
(team_seq.NEXTVAL, 3, 2, 'Eco Innovators');

INSERT INTO team (id, hackathon_id, created_by, team_name) VALUES
(team_seq.NEXTVAL, 3, 5, 'Green Tech');

INSERT INTO team (id, hackathon_id, created_by, team_name) VALUES
(team_seq.NEXTVAL, 4, 6, 'Health Heroes');

INSERT INTO team (id, hackathon_id, created_by, team_name) VALUES
(team_seq.NEXTVAL, 4, 8, 'Digital Care');

INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (1, 1, 'TEAM_LEAD');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (1, 2, 'UI_UX_DESIGNER');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (1, 5, 'DEVELOPER');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (2, 4, 'PRODUCT_MANAGER');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (2, 3, 'DEVELOPER');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (2, 7, 'DEVELOPER');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (3, 3, 'TEAM_LEAD');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (3, 6, 'UI_UX_DESIGNER');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (3, 1, 'DEVELOPER');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (4, 7, 'TEAM_LEAD');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (4, 8, 'PRODUCT_MANAGER');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (4, 2, 'UI_UX_DESIGNER');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (5, 2, 'TEAM_LEAD');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (5, 5, 'DATA_SCIENTIST');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (5, 1, 'DEVELOPER');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (6, 5, 'TEAM_LEAD');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (6, 3, 'DEVELOPER');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (6, 7, 'DEVOPS_ENGINEER');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (7, 6, 'TEAM_LEAD');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (7, 4, 'PRODUCT_MANAGER');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (7, 8, 'DATA_SCIENTIST');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (8, 8, 'PRODUCT_MANAGER');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (8, 6, 'UI_UX_DESIGNER');
INSERT INTO team_member (team_id, user_id, role_in_team) VALUES (8, 3, 'DEVELOPER');

INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (1, 1, 'ADVANCED');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (1, 2, 'ADVANCED');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (1, 11, 'EXPERT');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (2, 7, 'EXPERT');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (2, 17, 'ADVANCED');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (3, 3, 'ADVANCED');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (3, 16, 'EXPERT');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (4, 8, 'EXPERT');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (4, 18, 'INTERMEDIATE');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (5, 5, 'ADVANCED');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (5, 6, 'INTERMEDIATE');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (6, 7, 'ADVANCED');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (6, 3, 'INTERMEDIATE');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (7, 1, 'INTERMEDIATE');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (7, 10, 'ADVANCED');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (8, 8, 'ADVANCED');
INSERT INTO user_skill (user_id, skill_id, proficiency_level) VALUES (8, 19, 'INTERMEDIATE');

INSERT INTO join_request (id, team_id, user_id, status) VALUES
(join_request_seq.NEXTVAL, 1, 6, 'PENDING');

INSERT INTO join_request (id, team_id, user_id, status) VALUES
(join_request_seq.NEXTVAL, 2, 5, 'APPROVED');

INSERT INTO join_request (id, team_id, user_id, status) VALUES
(join_request_seq.NEXTVAL, 3, 8, 'PENDING');

INSERT INTO join_request (id, team_id, user_id, status) VALUES
(join_request_seq.NEXTVAL, 4, 1, 'REJECTED');

INSERT INTO join_request (id, team_id, user_id, status) VALUES
(join_request_seq.NEXTVAL, 5, 7, 'PENDING');

INSERT INTO join_request (id, team_id, user_id, status) VALUES
(join_request_seq.NEXTVAL, 6, 2, 'APPROVED');

INSERT INTO join_request (id, team_id, user_id, status) VALUES
(join_request_seq.NEXTVAL, 7, 1, 'PENDING');

INSERT INTO join_request (id, team_id, user_id, status) VALUES
(join_request_seq.NEXTVAL, 8, 5, 'PENDING');

INSERT INTO submission (id, team_id, hackathon_id, project_title, project_description, github_link, demo_link, presentation_link, technologies, features, score, judge_comments, status, submitted_by) VALUES
(submission_seq.NEXTVAL, 1, 1, 'AI-Powered Smart Home Assistant', 'An intelligent home automation system using machine learning for energy optimization', 'https://github.com/team1/smart-home-ai', 'https://demo.smarthome.ai', 'https://presentation.smarthome.ai', 'Python, TensorFlow, IoT, Machine Learning', 'Voice control, Energy optimization, Smart scheduling', 95.50, 'Excellent implementation of AI algorithms. Great user experience design.', 'SUBMITTED', 1);

INSERT INTO submission (id, team_id, hackathon_id, project_title, project_description, github_link, demo_link, presentation_link, technologies, features, score, judge_comments, status, submitted_by) VALUES
(submission_seq.NEXTVAL, 2, 1, 'Predictive Healthcare Analytics', 'Machine learning platform for predicting patient health outcomes', 'https://github.com/team2/health-analytics', 'https://demo.healthanalytics.com', 'https://presentation.healthanalytics.com', 'Python, Scikit-learn, React, PostgreSQL', 'Predictive modeling, Data visualization, API integration', 88.75, 'Strong technical implementation. Could improve user interface.', 'SUBMITTED', 4);

INSERT INTO submission (id, team_id, hackathon_id, project_title, project_description, github_link, demo_link, presentation_link, technologies, features, score, judge_comments, status, submitted_by) VALUES
(submission_seq.NEXTVAL, 3, 2, 'DeFi Lending Platform', 'Decentralized lending protocol built on Ethereum', 'https://github.com/team3/defi-lending', 'https://demo.defilending.com', 'https://presentation.defilending.com', 'Solidity, React, Web3.js, Hardhat', 'Smart contracts, DeFi protocols, Yield farming', 92.00, 'Innovative blockchain solution. Excellent security implementation.', 'SUBMITTED', 3);

INSERT INTO submission (id, team_id, hackathon_id, project_title, project_description, github_link, demo_link, presentation_link, technologies, features, score, judge_comments, status, submitted_by) VALUES
(submission_seq.NEXTVAL, 4, 2, 'NFT Marketplace', 'Peer-to-peer NFT trading platform with advanced features', 'https://github.com/team4/nft-marketplace', 'https://demo.nftmarketplace.com', 'https://presentation.nftmarketplace.com', 'Solidity, Next.js, IPFS, MetaMask', 'NFT minting, Trading, Royalty system', 85.25, 'Good concept. Needs better performance optimization.', 'SUBMITTED', 7);

INSERT INTO submission (id, team_id, hackathon_id, project_title, project_description, github_link, demo_link, presentation_link, technologies, features, score, judge_comments, status, submitted_by) VALUES
(submission_seq.NEXTVAL, 5, 3, 'Carbon Footprint Tracker', 'Mobile app for tracking and reducing personal carbon footprint', 'https://github.com/team5/carbon-tracker', 'https://demo.carbontracker.com', 'https://presentation.carbontracker.com', 'React Native, Node.js, MongoDB, AWS', 'GPS tracking, Carbon calculation, Social sharing', 90.00, 'Excellent environmental impact. Great user engagement features.', 'SUBMITTED', 2);

INSERT INTO submission (id, team_id, hackathon_id, project_title, project_description, github_link, demo_link, presentation_link, technologies, features, score, judge_comments, status, submitted_by) VALUES
(submission_seq.NEXTVAL, 6, 3, 'Smart Waste Management', 'IoT-based waste sorting and recycling system', 'https://github.com/team6/smart-waste', 'https://demo.smartwaste.com', 'https://presentation.smartwaste.com', 'Arduino, Python, IoT, Machine Learning', 'Automated sorting, Recycling guidance, Analytics', 87.50, 'Innovative IoT solution. Good hardware integration.', 'SUBMITTED', 5);

INSERT INTO submission (id, team_id, hackathon_id, project_title, project_description, github_link, demo_link, presentation_link, technologies, features, score, judge_comments, status, submitted_by) VALUES
(submission_seq.NEXTVAL, 7, 4, 'Telemedicine Platform', 'Comprehensive telehealth solution for remote patient care', 'https://github.com/team7/telemedicine', 'https://demo.telemedicine.com', 'https://presentation.telemedicine.com', 'React, Node.js, WebRTC, PostgreSQL', 'Video calls, Patient records, Prescription management', 93.75, 'Excellent healthcare solution. Great accessibility features.', 'SUBMITTED', 6);

INSERT INTO submission (id, team_id, hackathon_id, project_title, project_description, github_link, demo_link, presentation_link, technologies, features, score, judge_comments, status, submitted_by) VALUES
(submission_seq.NEXTVAL, 8, 4, 'Patient Health Monitoring', 'Real-time health monitoring system for chronic disease management', 'https://github.com/team8/health-monitor', 'https://demo.healthmonitor.com', 'https://presentation.healthmonitor.com', 'Flutter, Firebase, IoT sensors, Machine Learning', 'Real-time monitoring, Alert system, Data analytics', 89.00, 'Good technical implementation. Could enhance data visualization.', 'SUBMITTED', 8);

CREATE INDEX idx_user_skill_proficiency ON user_skill(proficiency_level);
CREATE INDEX idx_team_member_role ON team_member(role_in_team);
CREATE INDEX idx_submission_score ON submission(score);

ANALYZE TABLE users COMPUTE STATISTICS;
ANALYZE TABLE skill COMPUTE STATISTICS;
ANALYZE TABLE user_skill COMPUTE STATISTICS;
ANALYZE TABLE hackathon COMPUTE STATISTICS;
ANALYZE TABLE team COMPUTE STATISTICS;
ANALYZE TABLE team_member COMPUTE STATISTICS;
ANALYZE TABLE join_request COMPUTE STATISTICS;
ANALYZE TABLE submission COMPUTE STATISTICS;

COMMIT;

SELECT 'Data insertion completed successfully' as status FROM dual;
SELECT COUNT(*) as total_users FROM users;
SELECT COUNT(*) as total_skills FROM skill;
SELECT COUNT(*) as total_hackathons FROM hackathon;
SELECT COUNT(*) as total_teams FROM team;
SELECT COUNT(*) as total_submissions FROM submission;
