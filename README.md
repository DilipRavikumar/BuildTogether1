# BuildTogether

Java Maven project for hackathon management with Oracle database.

## Features

- User registration and login
- Team creation and management
- Hackathon events
- Project submissions
- Skill management
- Join request system

## Requirements

- Java 21
- Maven 3.6+
- Oracle Database 19c+

## Setup

### Database Setup

Create Oracle database user and run schema:

```sql
-- Connect as SYSTEM or SYS
CREATE USER buildtogether IDENTIFIED BY password;
GRANT CONNECT, RESOURCE, DBA TO buildtogether;
GRANT CREATE SESSION TO buildtogether;
GRANT UNLIMITED TABLESPACE TO buildtogether;

-- Connect as buildtogether user
CONNECT buildtogether/password;

-- Run schema and sample data
@src/main/resources/schema.sql
@src/main/resources/sample_data.sql
```

### Run Application

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.buildtogether.main.BuildTogetherApp"
```

## Database

Tables: users, skills, user_skills, teams, team_members, join_requests, hackathons, submissions

## Usage

Login with: john.doe@email.com / password123

## Project Structure

```
src/main/java/com/buildtogether/
├── pojo/           # Model classes
├── dao/            # Data Access Object interfaces
├── dao/impl/       # DAO implementations
├── service/        # Business logic services
├── util/           # Utility classes
└── main/           # Main application class
```
