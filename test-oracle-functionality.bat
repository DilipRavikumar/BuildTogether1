@echo off
echo ========================================
echo BuildTogether Oracle Functionality Test
echo ========================================
echo.

echo Starting Spring Boot application with Oracle configuration...
echo.

REM Start the application in background
start /B mvn spring-boot:run

echo Waiting for application to start...
timeout /t 30 /nobreak > nul

echo.
echo Testing Oracle Database connectivity and functionality...
echo.

REM Test 1: Health Check
echo 1. Testing Health Check...
curl -s -o nul -w "Health Check: %%{http_code}\n" "http://localhost:8080/actuator/health"

REM Test 2: Users API
echo 2. Testing Users API...
curl -s -o nul -w "Users API: %%{http_code}\n" "http://localhost:8080/api/v1/users"

REM Test 3: Skills API
echo 3. Testing Skills API...
curl -s -o nul -w "Skills API: %%{http_code}\n" "http://localhost:8080/api/v1/skills"

REM Test 4: Hackathons API
echo 4. Testing Hackathons API...
curl -s -o nul -w "Hackathons API: %%{http_code}\n" "http://localhost:8080/api/v1/hackathons"

REM Test 5: Teams API
echo 5. Testing Teams API...
curl -s -o nul -w "Teams API: %%{http_code}\n" "http://localhost:8080/api/v1/teams"

REM Test 6: User Skills API
echo 6. Testing User Skills API...
curl -s -o nul -w "User Skills API: %%{http_code}\n" "http://localhost:8080/api/v1/user-skills"

REM Test 7: Team Members API
echo 7. Testing Team Members API...
curl -s -o nul -w "Team Members API: %%{http_code}\n" "http://localhost:8080/api/v1/team-members"

REM Test 8: Join Requests API
echo 8. Testing Join Requests API...
curl -s -o nul -w "Join Requests API: %%{http_code}\n" "http://localhost:8080/api/v1/join-requests"

REM Test 9: Submissions API
echo 9. Testing Submissions API...
curl -s -o nul -w "Submissions API: %%{http_code}\n" "http://localhost:8080/api/v1/submissions"

echo.
echo ========================================
echo Oracle Functionality Test Complete!
echo ========================================
echo.
echo All endpoints should return HTTP 200 status.
echo If any endpoint shows errors, check:
echo 1. Oracle Database is running
echo 2. Database user 'buildtogether' exists
echo 3. Schema and sample data are loaded
echo 4. Application logs for detailed errors
echo.
echo Swagger UI: http://localhost:8080/swagger-ui.html
echo Health Check: http://localhost:8080/actuator/health
echo.
echo Press any key to continue...
pause > nul
