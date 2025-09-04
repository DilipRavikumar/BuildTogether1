@echo off
echo ========================================
echo BuildTogether - Complete Test Scenario
echo ========================================
echo.

echo Phase 1: Testing User Management
echo ----------------------------------------
echo Creating users...

REM Create John Doe
curl -X POST "http://localhost:8080/api/v1/users" -H "Content-Type: application/json" -d "{\"name\":\"John Doe\",\"email\":\"john.doe@test.com\",\"password\":\"password123\",\"role\":\"DEVELOPER\",\"phone\":\"+1234567890\",\"githubLink\":\"https://github.com/johndoe\",\"linkedinLink\":\"https://linkedin.com/in/johndoe\"}"

echo.
echo Creating Jane Smith...
curl -X POST "http://localhost:8080/api/v1/users" -H "Content-Type: application/json" -d "{\"name\":\"Jane Smith\",\"email\":\"jane.smith@test.com\",\"password\":\"password123\",\"role\":\"DESIGNER\",\"phone\":\"+1234567891\",\"githubLink\":\"https://github.com/janesmith\",\"linkedinLink\":\"https://linkedin.com/in/janesmith\"}"

echo.
echo Creating Mike Johnson...
curl -X POST "http://localhost:8080/api/v1/users" -H "Content-Type: application/json" -d "{\"name\":\"Mike Johnson\",\"email\":\"mike.johnson@test.com\",\"password\":\"password123\",\"role\":\"DEVELOPER\",\"phone\":\"+1234567892\",\"githubLink\":\"https://github.com/mikejohnson\",\"linkedinLink\":\"https://linkedin.com/in/mikejohnson\"}"

echo.
echo Creating Sarah Wilson...
curl -X POST "http://localhost:8080/api/v1/users" -H "Content-Type: application/json" -d "{\"name\":\"Sarah Wilson\",\"email\":\"sarah.wilson@test.com\",\"password\":\"password123\",\"role\":\"PRODUCT_MANAGER\",\"phone\":\"+1234567893\",\"githubLink\":\"https://github.com/sarahwilson\",\"linkedinLink\":\"https://linkedin.com/in/sarahwilson\"}"

echo.
echo Phase 2: Testing Skill Management
echo ----------------------------------------
echo Creating skills...

curl -X POST "http://localhost:8080/api/v1/skills" -H "Content-Type: application/json" -d "{\"skillName\":\"Java\"}"
curl -X POST "http://localhost:8080/api/v1/skills" -H "Content-Type: application/json" -d "{\"skillName\":\"React\"}"
curl -X POST "http://localhost:8080/api/v1/skills" -H "Content-Type: application/json" -d "{\"skillName\":\"UI/UX Design\"}"
curl -X POST "http://localhost:8080/api/v1/skills" -H "Content-Type: application/json" -d "{\"skillName\":\"Product Management\"}"

echo.
echo Phase 3: Testing Hackathon Creation
echo ----------------------------------------
echo Creating AI Innovation Challenge 2024...

curl -X POST "http://localhost:8080/api/v1/hackathons" -H "Content-Type: application/json" -d "{\"title\":\"AI Innovation Challenge 2024\",\"description\":\"Build innovative AI solutions for real-world problems\",\"startDate\":\"2024-03-01\",\"endDate\":\"2024-03-03\",\"maxTeamSize\":4,\"createdBy\":{\"id\":1}}"

echo.
echo Phase 4: Testing Team Creation
echo ----------------------------------------
echo Creating AI Pioneers team...

curl -X POST "http://localhost:8080/api/v1/teams" -H "Content-Type: application/json" -d "{\"teamName\":\"AI Pioneers\",\"hackathon\":{\"id\":1},\"createdBy\":{\"id\":1}}"

echo.
echo Phase 5: Testing Join Request
echo ----------------------------------------
echo Sarah Wilson requesting to join AI Pioneers...

curl -X POST "http://localhost:8080/api/v1/join-requests" -H "Content-Type: application/json" -d "{\"team\":{\"id\":1},\"user\":{\"id\":4},\"status\":\"PENDING\"}"

echo.
echo Phase 6: Testing Data Retrieval
echo ----------------------------------------
echo Getting all users...
curl -X GET "http://localhost:8080/api/v1/users" -H "accept: application/json"

echo.
echo Getting all teams...
curl -X GET "http://localhost:8080/api/v1/teams" -H "accept: application/json"

echo.
echo Getting all join requests...
curl -X GET "http://localhost:8080/api/v1/join-requests" -H "accept: application/json"

echo.
echo Getting all skills...
curl -X GET "http://localhost:8080/api/v1/skills" -H "accept: application/json"

echo.
echo Getting all hackathons...
curl -X GET "http://localhost:8080/api/v1/hackathons" -H "accept: application/json"

echo.
echo ========================================
echo Test Scenario Complete!
echo ========================================
echo.
echo Check the responses above to verify:
echo 1. All endpoints are working (200/201 status)
echo 2. No lazy loading errors
echo 3. Clean JSON responses
echo 4. Data relationships maintained
echo.
pause
