@echo off
echo ========================================
echo    BUILDTOGETHER CORPORATE DEPLOYMENT
echo ========================================
echo.

REM Check Java version
echo [1/6] Checking Java version...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java not found! Please install Java 21+
    pause
    exit /b 1
)
echo Java found successfully!

REM Check Maven version
echo [2/6] Checking Maven version...
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Maven not found! Please install Maven 3.6+
    pause
    exit /b 1
)
echo Maven found successfully!

REM Set default environment variables if not set
echo [3/6] Setting environment variables...
if "%DB_HOST%"=="" (
    echo Setting default DB_HOST to localhost
    set DB_HOST=localhost
)
if "%DB_PORT%"=="" (
    echo Setting default DB_PORT to 1521
    set DB_PORT=1521
)
if "%DB_SID%"=="" (
    echo Setting default DB_SID to XE
    set DB_SID=XE
)
if "%DB_USERNAME%"=="" (
    echo Setting default DB_USERNAME to buildtogether
    set DB_USERNAME=buildtogether
)
if "%DB_PASSWORD%"=="" (
    echo Setting default DB_PASSWORD to buildtogether123
    set DB_PASSWORD=buildtogether123
)
if "%SERVER_PORT%"=="" (
    echo Setting default SERVER_PORT to 8443
    set SERVER_PORT=8443
)

REM Set corporate-friendly logging
echo [4/6] Setting corporate logging levels...
set LOG_LEVEL=INFO
set HIBERNATE_SQL=WARN
set HIBERNATE_BINDER=WARN
set SPRING_WEB=WARN
set SPRING_DATA=WARN
set HIBERNATE_ORM=WARN

REM Set CORS for corporate environment
echo [5/6] Setting CORS configuration...
set CORS_ORIGINS=http://localhost:3000,https://corporate-domain.com
set CORS_METHODS=GET,POST,PUT,DELETE,OPTIONS
set CORS_HEADERS=Content-Type,Authorization,X-Requested-With

echo Environment variables set successfully!
echo.
echo Current Configuration:
echo - Database: %DB_HOST%:%DB_PORT%:%DB_SID%
echo - Username: %DB_USERNAME%
echo - Port: %SERVER_PORT%
echo - Log Level: %LOG_LEVEL%
echo.

REM Clean and compile
echo [6/6] Building application...
echo Cleaning previous build...
call mvn clean compile
if %errorlevel% neq 0 (
    echo ERROR: Build failed! Please check the errors above.
    pause
    exit /b 1
)
echo Build successful!

echo.
echo ========================================
echo    DEPLOYMENT READY!
echo ========================================
echo.
echo To start the application, run:
echo   mvn spring-boot:run
echo.
echo Application will be available at:
echo   http://localhost:%SERVER_PORT%/api/v1
echo.
echo Press any key to start the application now...
pause >nul

echo Starting BuildTogether application...
call mvn spring-boot:run
