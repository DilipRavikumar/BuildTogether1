-- =====================================================
-- BuildTogether Oracle Database Schema (12c+ Optimized)
-- No Sequences, using IDENTITY columns
-- =====================================================

-- Drop tables in correct order
DROP TABLE dr_submission CASCADE CONSTRAINTS;
DROP TABLE dr_join_request CASCADE CONSTRAINTS;
DROP TABLE dr_team_member CASCADE CONSTRAINTS;
DROP TABLE dr_user_skill CASCADE CONSTRAINTS;
DROP TABLE dr_team CASCADE CONSTRAINTS;
DROP TABLE dr_hackathon CASCADE CONSTRAINTS;
DROP TABLE dr_skill CASCADE CONSTRAINTS;
DROP TABLE dr_users CASCADE CONSTRAINTS;

-- =====================================================
-- CREATE TABLES
-- =====================================================

-- Users
CREATE TABLE dr_users (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    email VARCHAR2(255) UNIQUE NOT NULL,
    password VARCHAR2(255) NOT NULL,
    phone VARCHAR2(20),
    role VARCHAR2(50),
    github_link VARCHAR2(500),
    linkedin_link VARCHAR2(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Skills
CREATE TABLE dr_skill (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    skill_name VARCHAR2(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Hackathons
CREATE TABLE dr_hackathon (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR2(255) NOT NULL,
    description CLOB,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    max_team_size NUMBER(3) DEFAULT 5,
    created_by NUMBER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_hackathon_created_by FOREIGN KEY (created_by) 
        REFERENCES dr_users(id) ON DELETE CASCADE
);

-- Teams
CREATE TABLE dr_team (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    hackathon_id NUMBER NOT NULL,
    created_by NUMBER NOT NULL,
    team_name VARCHAR2(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_team_hackathon FOREIGN KEY (hackathon_id) REFERENCES dr_hackathon(id) ON DELETE CASCADE,
    CONSTRAINT fk_team_created_by FOREIGN KEY (created_by) REFERENCES dr_users(id) ON DELETE CASCADE,
    CONSTRAINT uq_team_name_per_hackathon UNIQUE (hackathon_id, team_name)
);

-- User Skills (Composite key)
CREATE TABLE dr_user_skill (
    user_id NUMBER NOT NULL,
    skill_id NUMBER NOT NULL,
    proficiency_level VARCHAR2(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_user_skill PRIMARY KEY (user_id, skill_id),
    CONSTRAINT fk_user_skill_user FOREIGN KEY (user_id) REFERENCES dr_users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_skill_skill FOREIGN KEY (skill_id) REFERENCES dr_skill(id) ON DELETE CASCADE,
    CONSTRAINT chk_proficiency_level CHECK (proficiency_level IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED', 'EXPERT'))
);

-- Team Members (Composite key)
CREATE TABLE dr_team_member (
    team_id NUMBER NOT NULL,
    user_id NUMBER NOT NULL,
    role_in_team VARCHAR2(30) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_team_member PRIMARY KEY (team_id, user_id),
    CONSTRAINT fk_team_member_team FOREIGN KEY (team_id) REFERENCES dr_team(id) ON DELETE CASCADE,
    CONSTRAINT fk_team_member_user FOREIGN KEY (user_id) REFERENCES dr_users(id) ON DELETE CASCADE,
    CONSTRAINT chk_role_in_team CHECK (role_in_team IN ('TEAM_LEAD', 'DEVELOPER', 'DESIGNER', 'PRODUCT_MANAGER', 'DATA_SCIENTIST', 'DEVOPS_ENGINEER', 'UI_UX_DESIGNER'))
);

-- Join Requests
CREATE TABLE dr_join_request (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    team_id NUMBER NOT NULL,
    user_id NUMBER NOT NULL,
    status VARCHAR2(20) DEFAULT 'PENDING',
    requested_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_join_request_team FOREIGN KEY (team_id) REFERENCES dr_team(id) ON DELETE CASCADE,
    CONSTRAINT fk_join_request_user FOREIGN KEY (user_id) REFERENCES dr_users(id) ON DELETE CASCADE,
    CONSTRAINT uq_join_request UNIQUE (team_id, user_id),
    CONSTRAINT chk_join_request_status CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED'))
);

-- Submissions
CREATE TABLE dr_submission (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    team_id NUMBER NOT NULL,
    hackathon_id NUMBER NOT NULL,
    project_title VARCHAR2(255) NOT NULL,
    project_description CLOB NOT NULL,
    github_link VARCHAR2(500),
    demo_link VARCHAR2(500),
    presentation_link VARCHAR2(500),
    technologies VARCHAR2(4000),
    features VARCHAR2(4000),
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status VARCHAR2(20) DEFAULT 'SUBMITTED',
    score NUMBER(5,2) DEFAULT 0.00,
    judge_comments CLOB,
    submitted_by NUMBER NOT NULL,
    CONSTRAINT fk_submission_team FOREIGN KEY (team_id) REFERENCES dr_team(id) ON DELETE CASCADE,
    CONSTRAINT fk_submission_hackathon FOREIGN KEY (hackathon_id) REFERENCES dr_hackathon(id) ON DELETE CASCADE,
    CONSTRAINT fk_submission_submitted_by FOREIGN KEY (submitted_by) REFERENCES dr_users(id) ON DELETE CASCADE,
    CONSTRAINT chk_submission_status CHECK (status IN ('SUBMITTED', 'UNDER_REVIEW', 'APPROVED', 'REJECTED'))
);

-- =====================================================
-- TRIGGERS FOR updated_at
-- =====================================================

-- Users updated_at trigger
CREATE OR REPLACE TRIGGER tr_users_updated_at
    BEFORE UPDATE ON dr_users
    FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/

-- Hackathon updated_at trigger
CREATE OR REPLACE TRIGGER tr_hackathon_updated_at
    BEFORE UPDATE ON dr_hackathon
    FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/

-- Team updated_at trigger
CREATE OR REPLACE TRIGGER tr_team_updated_at
    BEFORE UPDATE ON dr_team
    FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/

-- Skill updated_at trigger
CREATE OR REPLACE TRIGGER tr_skill_updated_at
    BEFORE UPDATE ON dr_skill
    FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/

-- User Skill updated_at trigger
CREATE OR REPLACE TRIGGER tr_user_skill_updated_at
    BEFORE UPDATE ON dr_user_skill
    FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/

-- Team Member updated_at trigger
CREATE OR REPLACE TRIGGER tr_team_member_updated_at
    BEFORE UPDATE ON dr_team_member
    FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/

-- Join Request updated_at trigger
CREATE OR REPLACE TRIGGER tr_join_request_updated_at
    BEFORE UPDATE ON dr_join_request
    FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/

-- Submission updated_at trigger
CREATE OR REPLACE TRIGGER tr_submission_updated_at
    BEFORE UPDATE ON dr_submission
    FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/

-- =====================================================
-- CREATE INDEXES FOR PERFORMANCE
-- =====================================================

-- Users
CREATE INDEX idx_users_email ON dr_users(email);
CREATE INDEX idx_users_role ON dr_users(role);

-- Hackathons
CREATE INDEX idx_hackathon_dates ON dr_hackathon(start_date, end_date);
CREATE INDEX idx_hackathon_created_by ON dr_hackathon(created_by);

-- Teams
CREATE INDEX idx_team_hackathon ON dr_team(hackathon_id);
CREATE INDEX idx_team_created_by ON dr_team(created_by);

-- User Skills
CREATE INDEX idx_user_skill_user ON dr_user_skill(user_id);
CREATE INDEX idx_user_skill_skill ON dr_user_skill(skill_id);
CREATE INDEX idx_user_skill_combo ON dr_user_skill(user_id, skill_id);

-- Team Members
CREATE INDEX idx_team_member_team ON dr_team_member(team_id);
CREATE INDEX idx_team_member_user ON dr_team_member(user_id);
CREATE INDEX idx_team_member_combo ON dr_team_member(team_id, user_id);

-- Join Requests
CREATE INDEX idx_join_request_team ON dr_join_request(team_id);
CREATE INDEX idx_join_request_user ON dr_join_request(user_id);
CREATE INDEX idx_join_request_status ON dr_join_request(status);

-- Submissions
CREATE INDEX idx_submission_team ON dr_submission(team_id);
CREATE INDEX idx_submission_hackathon ON dr_submission(hackathon_id);
CREATE INDEX idx_submission_submitted_by ON dr_submission(submitted_by);
CREATE INDEX idx_submission_status ON dr_submission(status);

-- =====================================================
-- COMMIT
-- =====================================================
COMMIT;

-- =====================================================
-- VERIFICATION
-- =====================================================
SELECT table_name FROM user_tables WHERE table_name LIKE 'DR_%' ORDER BY table_name;
SELECT trigger_name FROM user_triggers WHERE trigger_name LIKE 'TR_%' ORDER BY trigger_name;
SELECT index_name FROM user_indexes WHERE index_name LIKE 'IDX_%' ORDER BY index_name;

PROMPT 'Oracle schema created successfully (12c+ with IDENTITY)!';