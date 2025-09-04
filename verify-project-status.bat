@echo off
echo ========================================
echo BuildTogether - Project Status Verification
echo ========================================
echo.

echo Checking if application is running...
curl -s -o nul -w "HTTP Status: %%{http_code}\n" "http://localhost:8080/actuator/health"

echo.
echo Testing all major endpoints...
echo.

echo 1. Users endpoint...
curl -s -o nul -w "Users API: %%{http_code}\n" "http://localhost:8080/api/v1/users"

echo 2. Teams endpoint...
curl -s -o nul -w "Teams API: %%{http_code}\n" "http://localhost:8080/api/v1/teams"

echo 3. Hackathons endpoint...
curl -s -o nul -w "Hackathons API: %%{http_code}\n" "http://localhost:8080/api/v1/hackathons"

echo 4. Skills endpoint...
curl -s -o nul -w "Skills API: %%{http_code}\n" "http://localhost:8080/api/v1/skills"

echo 5. Join Requests endpoint...
curl -s -o nul -w "Join Requests API: %%{http_code}\n" "http://localhost:8080/api/v1/join-requests"

echo 6. Team Members endpoint...
curl -s -o nul -w "Team Members API: %%{http_code}\n" "http://localhost:8080/api/v1/team-members"

echo 7. Submissions endpoint...
curl -s -o nul -w "Submissions API: %%{http_code}\n" "http://localhost:8080/api/v1/submissions"

echo 8. User Skills endpoint...
curl -s -o nul -w "User Skills API: %%{http_code}\n" "http://localhost:8080/api/v1/user-skills"

echo.
echo ========================================
echo Verification Complete!
echo ========================================
echo.
echo All endpoints should return HTTP 200 status.
echo If any endpoint shows errors, check the application logs.
echo.
echo Swagger UI: http://localhost:8080/swagger-ui.html
echo Health Check: http://localhost:8080/actuator/health
echo.
pause
