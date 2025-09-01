-- Oracle Database Schema for BuildTogether
-- Drop tables in reverse dependency order
DROP TABLE submission CASCADE CONSTRAINTS;
DROP TABLE join_request CASCADE CONSTRAINTS;
DROP TABLE team_member CASCADE CONSTRAINTS;
DROP TABLE user_skill CASCADE CONSTRAINTS;
DROP TABLE team CASCADE CONSTRAINTS;
DROP TABLE hackathon CASCADE CONSTRAINTS;
DROP TABLE skill CASCADE CONSTRAINTS;
DROP TABLE users CASCADE CONSTRAINTS;

-- Drop sequences
DROP SEQUENCE users_seq;
DROP SEQUENCE skill_seq;
DROP SEQUENCE hackathon_seq;
DROP SEQUENCE team_seq;
DROP SEQUENCE join_request_seq;
DROP SEQUENCE submission_seq;

-- Create sequences for auto-incrementing IDs
CREATE SEQUENCE users_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE skill_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE hackathon_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE team_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE join_request_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE submission_seq START WITH 1 INCREMENT BY 1;

-- Create users table
CREATE TABLE users (
    id NUMBER(19) DEFAULT users_seq.NEXTVAL PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    password VARCHAR2(100) NOT NULL,
    phone VARCHAR2(20),
    role VARCHAR2(50),
    github_link VARCHAR2(255),
    linkedin_link VARCHAR2(255),
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP
);

-- Create skill table
CREATE TABLE skill (
    id NUMBER(19) DEFAULT skill_seq.NEXTVAL PRIMARY KEY,
    skill_name VARCHAR2(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP
);

-- Create user_skill table
CREATE TABLE user_skill (
    user_id NUMBER(19) NOT NULL,
    skill_id NUMBER(19) NOT NULL,
    proficiency_level VARCHAR2(20) CHECK (proficiency_level IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED', 'EXPERT')) NOT NULL,
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY (user_id, skill_id),
    CONSTRAINT fk_user_skill_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_skill_skill FOREIGN KEY (skill_id) REFERENCES skill(id) ON DELETE CASCADE
);

-- Create hackathon table
CREATE TABLE hackathon (
    id NUMBER(19) DEFAULT hackathon_seq.NEXTVAL PRIMARY KEY,
    title VARCHAR2(200) NOT NULL,
    description CLOB,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    max_team_size NUMBER(3) DEFAULT 5 CHECK (max_team_size > 0),
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP
);

-- Create team table
CREATE TABLE team (
    id NUMBER(19) DEFAULT team_seq.NEXTVAL PRIMARY KEY,
    hackathon_id NUMBER(19) NOT NULL,
    created_by NUMBER(19) NOT NULL,
    team_name VARCHAR2(100) NOT NULL,
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    CONSTRAINT fk_team_hackathon FOREIGN KEY (hackathon_id) REFERENCES hackathon(id) ON DELETE CASCADE,
    CONSTRAINT fk_team_created_by FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE
);

-- Create team_member table
CREATE TABLE team_member (
    team_id NUMBER(19) NOT NULL,
    user_id NUMBER(19) NOT NULL,
    role_in_team VARCHAR2(50) NOT NULL,
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY (team_id, user_id),
    CONSTRAINT fk_team_member_team FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE,
    CONSTRAINT fk_team_member_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create join_request table
CREATE TABLE join_request (
    id NUMBER(19) DEFAULT join_request_seq.NEXTVAL PRIMARY KEY,
    team_id NUMBER(19) NOT NULL,
    user_id NUMBER(19) NOT NULL,
    status VARCHAR2(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),
    requested_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    CONSTRAINT fk_join_request_team FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE,
    CONSTRAINT fk_join_request_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT unique_team_user UNIQUE (team_id, user_id)
);

-- Create submission table
CREATE TABLE submission (
    id NUMBER(19) DEFAULT submission_seq.NEXTVAL PRIMARY KEY,
    team_id NUMBER(19) NOT NULL,
    hackathon_id NUMBER(19) NOT NULL,
    project_title VARCHAR2(255) NOT NULL,
    project_description CLOB,
    github_link VARCHAR2(255),
    demo_link VARCHAR2(255),
    score NUMBER(5,2),
    judge_comments CLOB,
    status VARCHAR2(20) DEFAULT 'SUBMITTED' CHECK (status IN ('SUBMITTED', 'UNDER_REVIEW', 'APPROVED', 'REJECTED')),
    submitted_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    CONSTRAINT fk_submission_team FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE,
    CONSTRAINT fk_submission_hackathon FOREIGN KEY (hackathon_id) REFERENCES hackathon(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_team_hackathon ON team(hackathon_id);
CREATE INDEX idx_team_created_by ON team(created_by);
CREATE INDEX idx_join_request_status ON join_request(status);
CREATE INDEX idx_submission_status ON submission(status);
CREATE INDEX idx_submission_team_hackathon ON submission(team_id, hackathon_id);

-- Create triggers for updating timestamps
CREATE OR REPLACE TRIGGER users_update_trigger
    BEFORE UPDATE ON users
    FOR EACH ROW
BEGIN
    :NEW.updated_at := SYSTIMESTAMP;
END;
/

CREATE OR REPLACE TRIGGER skill_update_trigger
    BEFORE UPDATE ON skill
    FOR EACH ROW
BEGIN
    :NEW.updated_at := SYSTIMESTAMP;
END;
/

CREATE OR REPLACE TRIGGER hackathon_update_trigger
    BEFORE UPDATE ON hackathon
    FOR EACH ROW
BEGIN
    :NEW.updated_at := SYSTIMESTAMP;
END;
/

CREATE OR REPLACE TRIGGER team_update_trigger
    BEFORE UPDATE ON team
    FOR EACH ROW
BEGIN
    :NEW.updated_at := SYSTIMESTAMP;
END;
/

CREATE OR REPLACE TRIGGER join_request_update_trigger
    BEFORE UPDATE ON join_request
    FOR EACH ROW
BEGIN
    :NEW.updated_at := SYSTIMESTAMP;
END;
/

CREATE OR REPLACE TRIGGER submission_update_trigger
    BEFORE UPDATE ON submission
    FOR EACH ROW
BEGIN
    :NEW.updated_at := SYSTIMESTAMP;
END;
/
