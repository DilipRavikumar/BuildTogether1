DROP TABLE dr_submission CASCADE CONSTRAINTS;
DROP TABLE dr_join_request CASCADE CONSTRAINTS;
DROP TABLE dr_team_member CASCADE CONSTRAINTS;
DROP TABLE dr_user_skill CASCADE CONSTRAINTS;
DROP TABLE dr_team CASCADE CONSTRAINTS;
DROP TABLE dr_hackathon CASCADE CONSTRAINTS;
DROP TABLE dr_skill CASCADE CONSTRAINTS;
DROP TABLE dr_users CASCADE CONSTRAINTS;

DROP SEQUENCE users_seq;
DROP SEQUENCE skill_seq;
DROP SEQUENCE hackathon_seq;
DROP SEQUENCE team_seq;
DROP SEQUENCE join_request_seq;
DROP SEQUENCE submission_seq;

CREATE SEQUENCE users_seq START WITH 1 INCREMENT BY 1 CACHE 20;
CREATE SEQUENCE skill_seq START WITH 1 INCREMENT BY 1 CACHE 20;
CREATE SEQUENCE hackathon_seq START WITH 1 INCREMENT BY 1 CACHE 20;
CREATE SEQUENCE team_seq START WITH 1 INCREMENT BY 1 CACHE 20;
CREATE SEQUENCE join_request_seq START WITH 1 INCREMENT BY 1 CACHE 20;
CREATE SEQUENCE submission_seq START WITH 1 INCREMENT BY 1 CACHE 20;

CREATE TABLE dr_users (
    id NUMBER(19) DEFAULT users_seq.NEXTVAL PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    password VARCHAR2(100) NOT NULL,
    phone VARCHAR2(20),
    role VARCHAR2(50) CHECK (role IN ('DEVELOPER', 'DESIGNER', 'PRODUCT_MANAGER', 'DEVOPS', 'DATA_SCIENTIST', 'UI_UX_DESIGNER')),
    github_link VARCHAR2(255),
    linkedin_link VARCHAR2(255),
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP
);

CREATE TABLE dr_skill (
    id NUMBER(19) DEFAULT skill_seq.NEXTVAL PRIMARY KEY,
    skill_name VARCHAR2(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP
);

CREATE TABLE dr_user_skill (
    user_id NUMBER(19) NOT NULL,
    skill_id NUMBER(19) NOT NULL,
    proficiency_level VARCHAR2(20) CHECK (proficiency_level IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED', 'EXPERT')) NOT NULL,
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY (user_id, skill_id),
    CONSTRAINT fk_user_skill_user FOREIGN KEY (user_id) REFERENCES dr_users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_skill_skill FOREIGN KEY (skill_id) REFERENCES dr_skill(id) ON DELETE CASCADE
);

CREATE TABLE dr_hackathon (
    id NUMBER(19) DEFAULT hackathon_seq.NEXTVAL PRIMARY KEY,
    title VARCHAR2(200) NOT NULL,
    description CLOB,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    max_team_size NUMBER(3) DEFAULT 5 CHECK (max_team_size > 0 AND max_team_size <= 10),
    created_by NUMBER(19) NOT NULL,
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    CONSTRAINT chk_hackathon_dates CHECK (end_date > start_date),
    CONSTRAINT fk_hackathon_created_by FOREIGN KEY (created_by) REFERENCES dr_users(id) ON DELETE CASCADE
);

CREATE TABLE dr_team (
    id NUMBER(19) DEFAULT team_seq.NEXTVAL PRIMARY KEY,
    hackathon_id NUMBER(19) NOT NULL,
    created_by NUMBER(19) NOT NULL,
    team_name VARCHAR2(100) NOT NULL,
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    CONSTRAINT fk_team_hackathon FOREIGN KEY (hackathon_id) REFERENCES dr_hackathon(id) ON DELETE CASCADE,
    CONSTRAINT fk_team_created_by FOREIGN KEY (created_by) REFERENCES dr_users(id) ON DELETE CASCADE,
    CONSTRAINT uk_team_name_hackathon UNIQUE (team_name, hackathon_id)
);

CREATE TABLE dr_team_member (
    team_id NUMBER(19) NOT NULL,
    user_id NUMBER(19) NOT NULL,
    role_in_team VARCHAR2(50) NOT NULL CHECK (role_in_team IN ('TEAM_LEAD', 'DEVELOPER', 'DESIGNER', 'PRODUCT_MANAGER', 'DATA_SCIENTIST', 'DEVOPS_ENGINEER', 'UI_UX_DESIGNER')),
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY (team_id, user_id),
    CONSTRAINT fk_team_member_team FOREIGN KEY (team_id) REFERENCES dr_team(id) ON DELETE CASCADE,
    CONSTRAINT fk_team_member_user FOREIGN KEY (user_id) REFERENCES dr_users(id) ON DELETE CASCADE
);

CREATE TABLE dr_join_request (
    id NUMBER(19) DEFAULT join_request_seq.NEXTVAL PRIMARY KEY,
    team_id NUMBER(19) NOT NULL,
    user_id NUMBER(19) NOT NULL,
    status VARCHAR2(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),
    requested_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    CONSTRAINT fk_join_request_team FOREIGN KEY (team_id) REFERENCES dr_team(id) ON DELETE CASCADE,
    CONSTRAINT fk_join_request_user FOREIGN KEY (user_id) REFERENCES dr_users(id) ON DELETE CASCADE,
    CONSTRAINT unique_team_user UNIQUE (team_id, user_id)
);

CREATE TABLE dr_submission (
    id NUMBER(19) DEFAULT submission_seq.NEXTVAL PRIMARY KEY,
    team_id NUMBER(19) NOT NULL,
    hackathon_id NUMBER(19) NOT NULL,
    project_title VARCHAR2(255) NOT NULL,
    project_description CLOB,
    github_link VARCHAR2(255),
    demo_link VARCHAR2(255),
    presentation_link VARCHAR2(255),
    technologies CLOB,
    features CLOB,
    score NUMBER(5,2) CHECK (score >= 0 AND score <= 100),
    judge_comments CLOB,
    status VARCHAR2(20) DEFAULT 'SUBMITTED' CHECK (status IN ('SUBMITTED', 'UNDER_REVIEW', 'APPROVED', 'REJECTED')),
    submitted_by NUMBER(19) NOT NULL,
    submitted_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    CONSTRAINT fk_submission_team FOREIGN KEY (team_id) REFERENCES dr_team(id) ON DELETE CASCADE,
    CONSTRAINT fk_submission_hackathon FOREIGN KEY (hackathon_id) REFERENCES dr_hackathon(id) ON DELETE CASCADE,
    CONSTRAINT fk_submission_submitted_by FOREIGN KEY (submitted_by) REFERENCES dr_users(id) ON DELETE CASCADE
);

CREATE INDEX idx_users_email ON dr_users(email);
CREATE INDEX idx_users_role ON dr_users(role);
CREATE INDEX idx_hackathon_created_by ON dr_hackathon(created_by);
CREATE INDEX idx_team_hackathon ON dr_team(hackathon_id);
CREATE INDEX idx_team_created_by ON dr_team(created_by);
CREATE INDEX idx_join_request_status ON dr_join_request(status);
CREATE INDEX idx_submission_status ON dr_submission(status);
CREATE INDEX idx_submission_team_hackathon ON dr_submission(team_id, hackathon_id);
CREATE INDEX idx_submission_submitted_by ON dr_submission(submitted_by);
CREATE INDEX idx_user_skill_user ON dr_user_skill(user_id);
CREATE INDEX idx_user_skill_skill ON dr_user_skill(skill_id);
CREATE INDEX idx_team_member_team ON dr_team_member(team_id);
CREATE INDEX idx_team_member_user ON dr_team_member(user_id);

CREATE OR REPLACE TRIGGER users_update_trigger
    BEFORE UPDATE ON dr_users
    FOR EACH ROW
BEGIN
    :NEW.updated_at := SYSTIMESTAMP;
END;
/

CREATE OR REPLACE TRIGGER skill_update_trigger
    BEFORE UPDATE ON dr_skill
    FOR EACH ROW
BEGIN
    :NEW.updated_at := SYSTIMESTAMP;
END;
/

CREATE OR REPLACE TRIGGER hackathon_update_trigger
    BEFORE UPDATE ON dr_hackathon
    FOR EACH ROW
BEGIN
    :NEW.updated_at := SYSTIMESTAMP;
END;
/

CREATE OR REPLACE TRIGGER team_update_trigger
    BEFORE UPDATE ON dr_team
    FOR EACH ROW
BEGIN
    :NEW.updated_at := SYSTIMESTAMP;
END;
/

CREATE OR REPLACE TRIGGER join_request_update_trigger
    BEFORE UPDATE ON dr_join_request
    FOR EACH ROW
BEGIN
    :NEW.updated_at := SYSTIMESTAMP;
END;
/

CREATE OR REPLACE TRIGGER submission_update_trigger
    BEFORE UPDATE ON dr_submission
    FOR EACH ROW
BEGIN
    :NEW.updated_at := SYSTIMESTAMP;
END;
/

CREATE OR REPLACE VIEW team_stats AS
SELECT 
    t.id as team_id,
    t.team_name,
    h.title as hackathon_title,
    COUNT(tm.user_id) as member_count,
    t.created_at
FROM dr_team t
JOIN dr_hackathon h ON t.hackathon_id = h.id
LEFT JOIN dr_team_member tm ON t.id = tm.team_id
GROUP BY t.id, t.team_name, h.title, t.created_at;

GRANT SELECT ON team_stats TO buildtogether;
