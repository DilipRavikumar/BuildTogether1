# Oracle Database Setup Guide for BuildTogether

This guide will help you set up Oracle Database for the BuildTogether application.

## Prerequisites

1. **Oracle Database Installation**
   - Oracle Database 12c or higher (Express Edition is sufficient for development)
   - Oracle SQL Developer or SQL*Plus for database management

2. **Java Development Environment**
   - Java 21 or higher
   - Maven 3.6 or higher

## Step 1: Install Oracle Database

### Option A: Oracle Database Express Edition (XE)
1. Download Oracle Database XE from Oracle's website
2. Install following the installation wizard
3. Note the default SID (usually XE) and port (1521)

### Option B: Oracle Database Standard/Enterprise
1. Download from Oracle's website
2. Follow the installation guide for your platform
3. Create a database instance

## Step 2: Create Database User

1. Connect to Oracle as SYSTEM or SYS user
2. Run the setup script:
   ```sql
   @setup_oracle_database.sql
   ```

## Step 3: Create Database Schema

1. Connect as the `buildtogether` user
2. Run the schema creation script:
   ```sql
   @oracle_schema.sql
   ```

## Step 4: Insert Sample Data

1. Run the sample data script:
   ```sql
   @oracle_sample_data.sql
   ```

## Step 5: Verify Installation

Run these queries to verify everything is set up correctly:

```sql
-- Check tables
SELECT table_name FROM user_tables ORDER BY table_name;

-- Check sequences
SELECT sequence_name FROM user_sequences ORDER BY sequence_name;

-- Check sample data
SELECT COUNT(*) as user_count FROM dr_users;
SELECT COUNT(*) as hackathon_count FROM dr_hackathon;
SELECT COUNT(*) as team_count FROM dr_team;
```

## Step 6: Configure Application

The application is already configured for Oracle with these settings:

- **Database URL**: `jdbc:oracle:thin:@localhost:1521:XE`
- **Username**: `buildtogether`
- **Password**: `buildtogether123`
- **Driver**: `oracle.jdbc.OracleDriver`

## Connection Details

| Parameter | Value |
|-----------|-------|
| Host | localhost |
| Port | 1521 |
| SID | XE |
| Username | buildtogether |
| Password | buildtogether123 |
| Service Name | XE |

## Troubleshooting

### Common Issues

1. **Connection Refused**
   - Ensure Oracle Database service is running
   - Check if port 1521 is accessible
   - Verify SID or service name

2. **Authentication Failed**
   - Verify username and password
   - Check if user account is unlocked
   - Ensure user has necessary privileges

3. **Schema Creation Fails**
   - Ensure user has CREATE TABLE privilege
   - Check if tablespace has sufficient space
   - Verify sequence creation permissions

### Useful Commands

```sql
-- Check database status
SELECT status FROM v$instance;

-- Check user privileges
SELECT * FROM user_sys_privs;

-- Check tablespace usage
SELECT tablespace_name, bytes/1024/1024 as size_mb 
FROM user_ts_quotas;

-- Unlock user account (if needed)
ALTER USER buildtogether ACCOUNT UNLOCK;
```

## Performance Optimization

1. **Indexes**: The schema includes optimized indexes for common queries
2. **Sequences**: Auto-increment sequences for primary keys
3. **Triggers**: Automatic timestamp updates
4. **Constraints**: Data integrity constraints

## Security Considerations

1. **Change Default Password**: Update the default password in production
2. **Network Security**: Use Oracle Net encryption in production
3. **User Privileges**: Grant only necessary privileges
4. **Audit Logging**: Enable audit logging for sensitive operations

## Backup and Recovery

```sql
-- Create backup script
CREATE OR REPLACE PROCEDURE backup_buildtogether AS
BEGIN
    -- Export data using Oracle Data Pump
    -- or create backup tables
    NULL;
END;
/
```

## Monitoring

```sql
-- Monitor active sessions
SELECT username, machine, program, status 
FROM v$session 
WHERE username = 'BUILDTOGETHER';

-- Monitor table sizes
SELECT table_name, num_rows, blocks, avg_row_len 
FROM user_tables 
ORDER BY num_rows DESC;
```

## Support

For Oracle-specific issues:
1. Check Oracle documentation
2. Review Oracle error logs
3. Consult Oracle support forums
4. Contact database administrator

---

**Note**: This setup is optimized for development. For production deployment, additional security and performance configurations are recommended.
