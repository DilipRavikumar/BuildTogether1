# BuildTogether - Hackathon Management System

A comprehensive Spring Boot application for managing hackathons, teams, and project submissions.

## 🚀 Features

- **User Management**: Register, authenticate, and manage user profiles
- **Hackathon Management**: Create and manage hackathon events
- **Team Formation**: Form teams and manage team members
- **Skill Management**: Track user skills and proficiency levels
- **Join Requests**: Handle team join requests with approval/rejection
- **Project Submissions**: Submit and evaluate hackathon projects
- **RESTful API**: Complete REST API for all operations

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Database**: Oracle Database 19c/21c
- **ORM**: Spring Data JPA with Hibernate
- **Security**: Spring Security with JWT
- **Build Tool**: Maven
- **Java Version**: 21

## 📋 Prerequisites

- Java 21 or higher
- Oracle Database 19c or higher
- Maven 3.6 or higher

## 🗄️ Database Setup

1. **Install Oracle Database**:
   - Download and install Oracle Database 19c/21c
   - Create a new database instance (XE for development)

2. **Create Database User**:
   ```sql
   CREATE USER buildtogether IDENTIFIED BY buildtogether123;
   GRANT CONNECT, RESOURCE, DBA TO buildtogether;
   GRANT CREATE SESSION TO buildtogether;
   GRANT UNLIMITED TABLESPACE TO buildtogether;
   ```

3. **Run Schema Script**:
   ```bash
   sqlplus buildtogether/buildtogether123@localhost:1521:XE @database/schema.sql
   ```

4. **Insert Sample Data** (Optional):
   ```bash
   sqlplus buildtogether/buildtogether123@localhost:1521:XE @database/sample_data.sql
   ```

## ⚙️ Configuration

Update `src/main/resources/application.properties` with your Oracle database credentials:

```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:XE
spring.datasource.username=buildtogether
spring.datasource.password=buildtogether123
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
```

## 🏃‍♂️ Running the Application

1. **Compile the project**:
   ```bash
   mvn clean compile
   ```

2. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

3. **Access the API**:
   - Base URL: `http://localhost:8080/api/v1`
   - Swagger UI: `http://localhost:8080/api/v1/swagger-ui.html`

## 📚 API Endpoints

### Users
- `GET /users` - Get all users
- `GET /users/{id}` - Get user by ID
- `POST /users` - Create user
- `PUT /users/{id}` - Update user
- `DELETE /users/{id}` - Delete user

### Teams
- `GET /teams` - Get all teams
- `GET /teams/{id}` - Get team by ID
- `POST /teams` - Create team
- `PUT /teams/{id}` - Update team
- `DELETE /teams/{id}` - Delete team

### Hackathons
- `GET /hackathons` - Get all hackathons
- `GET /hackathons/{id}` - Get hackathon by ID
- `POST /hackathons` - Create hackathon
- `PUT /hackathons/{id}` - Update hackathon
- `DELETE /hackathons/{id}` - Delete hackathon

### Skills
- `GET /skills` - Get all skills
- `GET /skills/{id}` - Get skill by ID
- `POST /skills` - Create skill
- `PUT /skills/{id}` - Update skill
- `DELETE /skills/{id}` - Delete skill

### Team Members
- `GET /team-members` - Get all team members
- `GET /team-members/{id}` - Get team member by ID
- `POST /team-members` - Create team member
- `PUT /team-members/{id}` - Update team member
- `DELETE /team-members/{id}` - Delete team member

### Join Requests
- `GET /join-requests` - Get all join requests
- `GET /join-requests/{id}` - Get join request by ID
- `POST /join-requests` - Create join request
- `PUT /join-requests/{id}` - Update join request
- `DELETE /join-requests/{id}` - Delete join request
- `PUT /join-requests/{id}/approve` - Approve request
- `PUT /join-requests/{id}/reject` - Reject request

### Submissions
- `GET /submissions` - Get all submissions
- `GET /submissions/{id}` - Get submission by ID
- `POST /submissions` - Create submission
- `PUT /submissions/{id}` - Update submission
- `DELETE /submissions/{id}` - Delete submission
- `PUT /submissions/{id}/score` - Update submission score
- `PUT /submissions/{id}/status` - Update submission status

### User Skills
- `GET /user-skills` - Get all user skills
- `GET /user-skills/{id}` - Get user skill by ID
- `POST /user-skills` - Create user skill
- `PUT /user-skills/{id}` - Update user skill
- `DELETE /user-skills/{id}` - Delete user skill

## 🏗️ Project Structure

```
BuildTogether/
├── src/
│   ├── main/
│   │   ├── java/com/buildtogether/
│   │   │   ├── controller/     # REST controllers
│   │   │   ├── entity/         # JPA entities
│   │   │   ├── repository/     # Data repositories
│   │   │   ├── exception/      # Custom exceptions
│   │   │   ├── config/         # Configuration classes
│   │   │   └── BuildTogetherApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── database/
│   ├── schema.sql              # Oracle database schema
│   └── sample_data.sql         # Sample data for Oracle
├── pom.xml                     # Maven configuration
└── README.md                   # This file
```

## 🔧 Development

### Adding New Features
1. Create entity class in `entity/` package
2. Create repository interface in `repository/` package
3. Create controller class in `controller/` package
4. Update database schema if needed

### Database Changes
1. Update `database/schema.sql`
2. Test with `mvn clean compile`
3. Run the application to verify

## 🗄️ Oracle Database Features

- **Sequences**: Auto-incrementing IDs using Oracle sequences
- **Triggers**: Automatic timestamp updates using Oracle triggers
- **CLOB**: Large text fields for descriptions and comments
- **Constraints**: Proper foreign key and check constraints
- **Indexes**: Performance optimization with strategic indexing

## 📝 License

This project is licensed under the MIT License.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📞 Support

For support and questions, please open an issue in the repository.
