-- BuildTogether Database Setup Script for Oracle
-- This script creates all necessary tables and sequences

-- Drop existing tables if they exist (in reverse dependency order)
DROP TABLE submission CASCADE CONSTRAINTS;
DROP TABLE join_request CASCADE CONSTRAINTS;
DROP TABLE team_member CASCADE CONSTRAINTS;
DROP TABLE user_skill CASCADE CONSTRAINTS;
DROP TABLE team CASCADE CONSTRAINTS;
DROP TABLE hackathon CASCADE CONSTRAINTS;
DROP TABLE skill CASCADE CONSTRAINTS;
DROP TABLE users CASCADE CONSTRAINTS;

-- Drop sequences if they exist
DROP SEQUENCE seq_user_id;
DROP SEQUENCE seq_skill_id;
DROP SEQUENCE seq_hackathon_id;
DROP SEQUENCE seq_team_id;
DROP SEQUENCE seq_request_id;
DROP SEQUENCE seq_submission_id;

-- Create sequences for auto-incrementing IDs
CREATE SEQUENCE seq_user_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_skill_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_hackathon_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_team_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_request_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_submission_id START WITH 1 INCREMENT BY 1;

-- Create users table
CREATE TABLE users (
    user_id NUMBER PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    password VARCHAR2(100) NOT NULL,
    phone VARCHAR2(20),
    role VARCHAR2(50),
    github_link VARCHAR2(255),
    linkedin_link VARCHAR2(255)
);

-- Create skill table
CREATE TABLE skill (
    skill_id NUMBER PRIMARY KEY,
    skill_name VARCHAR2(100) UNIQUE NOT NULL
);

-- Create user_skill junction table (composite primary key)
CREATE TABLE user_skill (
    user_id NUMBER NOT NULL,
    skill_id NUMBER NOT NULL,
    proficiency_level VARCHAR2(20) NOT NULL CHECK (proficiency_level IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED', 'EXPERT')),
    PRIMARY KEY (user_id, skill_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES skill(skill_id) ON DELETE CASCADE
);

-- Create hackathon table
CREATE TABLE hackathon (
    hackathon_id NUMBER PRIMARY KEY,
    title VARCHAR2(200) NOT NULL,
    description CLOB,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    max_team_size NUMBER DEFAULT 5 CHECK (max_team_size > 0)
);

-- Create team table
CREATE TABLE team (
    team_id NUMBER PRIMARY KEY,
    hackathon_id NUMBER NOT NULL,
    created_by NUMBER NOT NULL,
    team_name VARCHAR2(100) NOT NULL,
    FOREIGN KEY (hackathon_id) REFERENCES hackathon(hackathon_id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create team_member junction table (composite primary key)
CREATE TABLE team_member (
    team_id NUMBER NOT NULL,
    user_id NUMBER NOT NULL,
    role_in_team VARCHAR2(50) NOT NULL,
    PRIMARY KEY (team_id, user_id),
    FOREIGN KEY (team_id) REFERENCES team(team_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create join_request table
CREATE TABLE join_request (
    request_id NUMBER PRIMARY KEY,
    team_id NUMBER NOT NULL,
    user_id NUMBER NOT NULL,
    status VARCHAR2(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),
    requested_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (team_id) REFERENCES team(team_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT unique_team_user UNIQUE (team_id, user_id)
);

-- Create submission table
CREATE TABLE submission (
    submission_id NUMBER PRIMARY KEY,
    team_id NUMBER NOT NULL,
    hackathon_id NUMBER NOT NULL,
    project_title VARCHAR2(255) NOT NULL,
    project_description CLOB NOT NULL,
    github_link VARCHAR2(500),
    demo_link VARCHAR2(500),
    presentation_link VARCHAR2(500),
    technologies CLOB,
    features CLOB,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR2(20) DEFAULT 'SUBMITTED' CHECK (status IN ('SUBMITTED', 'UNDER_REVIEW', 'APPROVED', 'REJECTED')),
    score NUMBER(5,2) DEFAULT 0.00,
    judge_comments CLOB,
    FOREIGN KEY (team_id) REFERENCES team(team_id) ON DELETE CASCADE,
    FOREIGN KEY (hackathon_id) REFERENCES hackathon(hackathon_id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_skill_name ON skill(skill_name);
CREATE INDEX idx_user_skill_user ON user_skill(user_id);
CREATE INDEX idx_user_skill_skill ON user_skill(skill_id);
CREATE INDEX idx_hackathon_dates ON hackathon(start_date, end_date);
CREATE INDEX idx_team_hackathon ON team(hackathon_id);
CREATE INDEX idx_team_created_by ON team(created_by);
CREATE INDEX idx_team_member_team ON team_member(team_id);
CREATE INDEX idx_team_member_user ON team_member(user_id);
CREATE INDEX idx_join_request_team ON join_request(team_id);
CREATE INDEX idx_join_request_user ON join_request(user_id);
CREATE INDEX idx_join_request_status ON join_request(status);
CREATE INDEX idx_submission_team ON submission(team_id);
CREATE INDEX idx_submission_hackathon ON submission(hackathon_id);
CREATE INDEX idx_submission_status ON submission(status);

-- Add constraints
ALTER TABLE hackathon ADD CONSTRAINT check_hackathon_dates CHECK (end_date > start_date);
ALTER TABLE team ADD CONSTRAINT unique_team_name_per_hackathon UNIQUE (hackathon_id, team_name);

-- Create triggers for auto-incrementing IDs
CREATE OR REPLACE TRIGGER trg_users_id
    BEFORE INSERT ON users
    FOR EACH ROW
BEGIN
    SELECT seq_user_id.NEXTVAL INTO :NEW.user_id FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER trg_skill_id
    BEFORE INSERT ON skill
    FOR EACH ROW
BEGIN
    SELECT seq_skill_id.NEXTVAL INTO :NEW.skill_id FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER trg_hackathon_id
    BEFORE INSERT ON hackathon
    FOR EACH ROW
BEGIN
    SELECT seq_hackathon_id.NEXTVAL INTO :NEW.hackathon_id FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER trg_team_id
    BEFORE INSERT ON team
    FOR EACH ROW
BEGIN
    SELECT seq_team_id.NEXTVAL INTO :NEW.team_id FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER trg_request_id
    BEFORE INSERT ON join_request
    FOR EACH ROW
BEGIN
    SELECT seq_request_id.NEXTVAL INTO :NEW.request_id FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER trg_submission_id
    BEFORE INSERT ON submission
    FOR EACH ROW
BEGIN
    SELECT seq_submission_id.NEXTVAL INTO :NEW.submission_id FROM DUAL;
END;
/

-- Display success message
SELECT 'Database schema created successfully!' AS message FROM DUAL;
SELECT 'Tables: users, skill, user_skill, team, team_member, join_request, hackathon, submission' AS tables FROM DUAL;
SELECT 'Sequences and triggers configured for auto-incrementing IDs' AS auto_increment FROM DUAL;
SELECT 'Indexes created for better performance' AS indexes FROM DUAL;
SELECT 'Constraints added for data integrity' AS constraints FROM DUAL;
