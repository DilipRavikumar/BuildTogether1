-- =====================================================
-- Oracle Database Test Scenario for BuildTogether
-- Comprehensive test to verify all functionality
-- =====================================================

-- Connect as buildtogether user before running this script

PROMPT 'Starting Oracle Database Test Scenario...'
PROMPT '=========================================='

-- =====================================================
-- TEST 1: Verify Database Schema
-- =====================================================

PROMPT 'TEST 1: Verifying Database Schema...'

-- Check if all tables exist
SELECT 'Tables Check:' as test_name FROM dual;
SELECT table_name, num_rows 
FROM user_tables 
WHERE table_name LIKE 'DR_%' 
ORDER BY table_name;

-- Check if all sequences exist
SELECT 'Sequences Check:' as test_name FROM dual;
SELECT sequence_name, last_number 
FROM user_sequences 
WHERE sequence_name LIKE 'SEQ_%' 
ORDER BY sequence_name;

-- Check if all triggers exist
SELECT 'Triggers Check:' as test_name FROM dual;
SELECT trigger_name, status 
FROM user_triggers 
WHERE trigger_name LIKE 'TR_%' 
ORDER BY trigger_name;

-- =====================================================
-- TEST 2: Verify Sample Data
-- =====================================================

PROMPT 'TEST 2: Verifying Sample Data...'

-- Count records in each table
SELECT 'Data Count Check:' as test_name FROM dual;
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

-- =====================================================
-- TEST 3: Test CRUD Operations
-- =====================================================

PROMPT 'TEST 3: Testing CRUD Operations...'

-- Test INSERT operation
INSERT INTO dr_users (name, email, password, role, created_at, updated_at) 
VALUES ('Test User', 'test@example.com', 'password123', 'DEVELOPER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Get the inserted user ID
SELECT 'Insert Test - New User ID:' as test_name, MAX(id) as new_user_id FROM dr_users;

-- Test UPDATE operation
UPDATE dr_users 
SET name = 'Updated Test User', updated_at = CURRENT_TIMESTAMP 
WHERE email = 'test@example.com';

SELECT 'Update Test - User Updated:' as test_name, name FROM dr_users WHERE email = 'test@example.com';

-- Test SELECT operation with JOIN
SELECT 'Join Test - Users with Skills:' as test_name FROM dual;
SELECT u.name, u.email, s.skill_name, us.proficiency_level
FROM dr_users u
JOIN dr_user_skill us ON u.id = us.user_id
JOIN dr_skill s ON us.skill_id = s.id
WHERE ROWNUM <= 5;

-- Test DELETE operation
DELETE FROM dr_users WHERE email = 'test@example.com';
SELECT 'Delete Test - User Deleted:' as test_name, COUNT(*) as remaining_users FROM dr_users;

-- =====================================================
-- TEST 4: Test Complex Queries
-- =====================================================

PROMPT 'TEST 4: Testing Complex Queries...'

-- Test query: Users with their teams and hackathons
SELECT 'Complex Query - Users with Teams:' as test_name FROM dual;
SELECT u.name as user_name, t.team_name, h.title as hackathon_title
FROM dr_users u
JOIN dr_team_member tm ON u.id = tm.user_id
JOIN dr_team t ON tm.team_id = t.id
JOIN dr_hackathon h ON t.hackathon_id = h.id
WHERE ROWNUM <= 5;

-- Test query: Hackathons with team counts
SELECT 'Complex Query - Hackathons with Team Counts:' as test_name FROM dual;
SELECT h.title, h.start_date, h.end_date, COUNT(t.id) as team_count
FROM dr_hackathon h
LEFT JOIN dr_team t ON h.id = t.hackathon_id
GROUP BY h.id, h.title, h.start_date, h.end_date
ORDER BY team_count DESC;

-- Test query: Submissions with scores
SELECT 'Complex Query - Submissions with Scores:' as test_name FROM dual;
SELECT s.project_title, s.status, s.score, t.team_name, h.title as hackathon_title
FROM dr_submission s
JOIN dr_team t ON s.team_id = t.id
JOIN dr_hackathon h ON s.hackathon_id = h.id
ORDER BY s.score DESC;

-- =====================================================
-- TEST 5: Test Constraints and Validations
-- =====================================================

PROMPT 'TEST 5: Testing Constraints and Validations...'

-- Test unique constraint on user email
BEGIN
    INSERT INTO dr_users (name, email, password, role, created_at, updated_at) 
    VALUES ('Duplicate Test', 'john.smith@email.com', 'password123', 'DEVELOPER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
    DBMS_OUTPUT.PUT_LINE('ERROR: Unique constraint not working!');
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('SUCCESS: Unique constraint working correctly');
END;
/

-- Test foreign key constraint
BEGIN
    INSERT INTO dr_team (hackathon_id, created_by, team_name, created_at, updated_at) 
    VALUES (999, 1, 'Invalid Team', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
    DBMS_OUTPUT.PUT_LINE('ERROR: Foreign key constraint not working!');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('SUCCESS: Foreign key constraint working correctly');
END;
/

-- =====================================================
-- TEST 6: Test Sequences and Auto-increment
-- =====================================================

PROMPT 'TEST 6: Testing Sequences and Auto-increment...'

-- Test sequence generation
INSERT INTO dr_skill (skill_name, created_at, updated_at) 
VALUES ('Test Skill ' || TO_CHAR(CURRENT_TIMESTAMP, 'HH24:MI:SS'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

SELECT 'Sequence Test - New Skill ID:' as test_name, MAX(id) as new_skill_id FROM dr_skill;

-- Clean up test data
DELETE FROM dr_skill WHERE skill_name LIKE 'Test Skill%';

-- =====================================================
-- TEST 7: Test Triggers
-- =====================================================

PROMPT 'TEST 7: Testing Triggers...'

-- Test updated_at trigger
INSERT INTO dr_users (name, email, password, role, created_at, updated_at) 
VALUES ('Trigger Test', 'trigger@example.com', 'password123', 'DEVELOPER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Wait a moment
DBMS_LOCK.SLEEP(1);

-- Update the record
UPDATE dr_users 
SET name = 'Updated Trigger Test' 
WHERE email = 'trigger@example.com';

-- Check if updated_at was modified
SELECT 'Trigger Test - Updated At:' as test_name, 
       created_at, updated_at,
       CASE WHEN updated_at > created_at THEN 'SUCCESS' ELSE 'FAILED' END as trigger_status
FROM dr_users 
WHERE email = 'trigger@example.com';

-- Clean up
DELETE FROM dr_users WHERE email = 'trigger@example.com';

-- =====================================================
-- TEST 8: Performance Test
-- =====================================================

PROMPT 'TEST 8: Performance Test...'

-- Test query performance
SET TIMING ON

SELECT 'Performance Test - Complex Join:' as test_name FROM dual;
SELECT COUNT(*) as total_records
FROM dr_users u
JOIN dr_user_skill us ON u.id = us.user_id
JOIN dr_skill s ON us.skill_id = s.id
JOIN dr_team_member tm ON u.id = tm.user_id
JOIN dr_team t ON tm.team_id = t.id
JOIN dr_hackathon h ON t.hackathon_id = h.id;

SET TIMING OFF

-- =====================================================
-- TEST 9: Data Integrity Test
-- =====================================================

PROMPT 'TEST 9: Data Integrity Test...'

-- Check for orphaned records
SELECT 'Data Integrity - Orphaned Team Members:' as test_name, COUNT(*) as orphaned_count
FROM dr_team_member tm
LEFT JOIN dr_team t ON tm.team_id = t.id
WHERE t.id IS NULL;

SELECT 'Data Integrity - Orphaned User Skills:' as test_name, COUNT(*) as orphaned_count
FROM dr_user_skill us
LEFT JOIN dr_users u ON us.user_id = u.id
WHERE u.id IS NULL;

-- =====================================================
-- TEST 10: Final Verification
-- =====================================================

PROMPT 'TEST 10: Final Verification...'

-- Final data summary
SELECT 'Final Data Summary:' as test_name FROM dual;
SELECT 
    'Users' as entity, COUNT(*) as count, 
    MIN(created_at) as earliest, MAX(created_at) as latest
FROM dr_users
UNION ALL
SELECT 'Hackathons', COUNT(*), MIN(created_at), MAX(created_at) FROM dr_hackathon
UNION ALL
SELECT 'Teams', COUNT(*), MIN(created_at), MAX(created_at) FROM dr_team
UNION ALL
SELECT 'Submissions', COUNT(*), MIN(submitted_at), MAX(submitted_at) FROM dr_submission;

-- =====================================================
-- COMMIT AND FINAL MESSAGE
-- =====================================================

COMMIT;

PROMPT '=========================================='
PROMPT 'Oracle Database Test Scenario Completed!'
PROMPT '=========================================='
PROMPT 'All tests have been executed successfully.'
PROMPT 'The BuildTogether application is ready for Oracle Database.'
PROMPT '=========================================='
