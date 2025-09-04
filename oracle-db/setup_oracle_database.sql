-- =====================================================
-- Oracle Database Setup Script for BuildTogether
-- This script creates the database user and grants necessary permissions
-- =====================================================

-- Connect as SYSTEM or SYS user to create the application user
-- Run this script as a DBA user (SYSTEM/SYS)

-- Create the application user
CREATE USER buildtogether IDENTIFIED BY buildtogether123;

-- Grant necessary privileges
GRANT CONNECT, RESOURCE TO buildtogether;
GRANT CREATE SESSION TO buildtogether;
GRANT CREATE TABLE TO buildtogether;
GRANT CREATE SEQUENCE TO buildtogether;
GRANT CREATE TRIGGER TO buildtogether;
GRANT CREATE PROCEDURE TO buildtogether;
GRANT CREATE VIEW TO buildtogether;
GRANT UNLIMITED TABLESPACE TO buildtogether;

-- Grant additional privileges for development
GRANT SELECT ANY TABLE TO buildtogether;
GRANT INSERT ANY TABLE TO buildtogether;
GRANT UPDATE ANY TABLE TO buildtogether;
GRANT DELETE ANY TABLE TO buildtogether;

-- Create tablespace (optional, for better organization)
-- CREATE TABLESPACE buildtogether_data
-- DATAFILE 'buildtogether_data.dbf' SIZE 100M
-- AUTOEXTEND ON NEXT 10M MAXSIZE UNLIMITED;

-- Assign tablespace to user (if created above)
-- ALTER USER buildtogether DEFAULT TABLESPACE buildtogether_data;

-- Verify user creation
SELECT username, account_status, created, default_tablespace, temporary_tablespace
FROM dba_users 
WHERE username = 'BUILDTOGETHER';

-- Show granted privileges
SELECT granted_role FROM dba_role_privs WHERE grantee = 'BUILDTOGETHER';

PROMPT 'Oracle database setup completed successfully!';
PROMPT 'User: buildtogether';
PROMPT 'Password: buildtogether123';
PROMPT 'Connection String: jdbc:oracle:thin:@localhost:1521:XE';
