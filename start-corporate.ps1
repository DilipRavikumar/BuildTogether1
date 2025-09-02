# ========================================
#    BUILDTOGETHER CORPORATE DEPLOYMENT
# ========================================

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   BUILDTOGETHER CORPORATE DEPLOYMENT" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check Java version
Write-Host "[1/6] Checking Java version..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1 | Select-String "version"
    if ($javaVersion) {
        Write-Host "Java found: $javaVersion" -ForegroundColor Green
    } else {
        throw "Java not found"
    }
} catch {
    Write-Host "ERROR: Java not found! Please install Java 21+" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

# Check Maven version
Write-Host "[2/6] Checking Maven version..." -ForegroundColor Yellow
try {
    $mavenVersion = mvn -version 2>&1 | Select-String "Apache Maven"
    if ($mavenVersion) {
        Write-Host "Maven found: $mavenVersion" -ForegroundColor Green
    } else {
        throw "Maven not found"
    }
} catch {
    Write-Host "ERROR: Maven not found! Please install Maven 3.6+" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

# Set default environment variables if not set
Write-Host "[3/6] Setting environment variables..." -ForegroundColor Yellow
if (-not $env:DB_HOST) {
    Write-Host "Setting default DB_HOST to localhost" -ForegroundColor Gray
    $env:DB_HOST = "localhost"
}
if (-not $env:DB_PORT) {
    Write-Host "Setting default DB_PORT to 1521" -ForegroundColor Gray
    $env:DB_PORT = "1521"
}
if (-not $env:DB_SID) {
    Write-Host "Setting default DB_SID to XE" -ForegroundColor Gray
    $env:DB_SID = "XE"
}
if (-not $env:DB_USERNAME) {
    Write-Host "Setting default DB_USERNAME to buildtogether" -ForegroundColor Gray
    $env:DB_USERNAME = "buildtogether"
}
if (-not $env:DB_PASSWORD) {
    Write-Host "Setting default DB_PASSWORD to buildtogether123" -ForegroundColor Gray
    $env:DB_PASSWORD = "buildtogether123"
}
if (-not $env:SERVER_PORT) {
    Write-Host "Setting default SERVER_PORT to 8443" -ForegroundColor Gray
    $env:SERVER_PORT = "8443"
}

# Set corporate-friendly logging
Write-Host "[4/6] Setting corporate logging levels..." -ForegroundColor Yellow
$env:LOG_LEVEL = "INFO"
$env:HIBERNATE_SQL = "WARN"
$env:HIBERNATE_BINDER = "WARN"
$env:SPRING_WEB = "WARN"
$env:SPRING_DATA = "WARN"
$env:HIBERNATE_ORM = "WARN"

# Set CORS for corporate environment
Write-Host "[5/6] Setting CORS configuration..." -ForegroundColor Yellow
$env:CORS_ORIGINS = "http://localhost:3000,https://corporate-domain.com"
$env:CORS_METHODS = "GET,POST,PUT,DELETE,OPTIONS"
$env:CORS_HEADERS = "Content-Type,Authorization,X-Requested-With"

Write-Host "Environment variables set successfully!" -ForegroundColor Green
Write-Host ""
Write-Host "Current Configuration:" -ForegroundColor Cyan
Write-Host "- Database: $($env:DB_HOST):$($env:DB_PORT):$($env:DB_SID)" -ForegroundColor White
Write-Host "- Username: $($env:DB_USERNAME)" -ForegroundColor White
Write-Host "- Port: $($env:SERVER_PORT)" -ForegroundColor White
Write-Host "- Log Level: $($env:LOG_LEVEL)" -ForegroundColor White
Write-Host ""

# Clean and compile
Write-Host "[6/6] Building application..." -ForegroundColor Yellow
Write-Host "Cleaning previous build..." -ForegroundColor Gray
try {
    mvn clean compile
    if ($LASTEXITCODE -ne 0) {
        throw "Build failed with exit code $LASTEXITCODE"
    }
    Write-Host "Build successful!" -ForegroundColor Green
} catch {
    Write-Host "ERROR: Build failed! Please check the errors above." -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "    DEPLOYMENT READY!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "To start the application, run:" -ForegroundColor Yellow
Write-Host "  mvn spring-boot:run" -ForegroundColor White
Write-Host ""
Write-Host "Application will be available at:" -ForegroundColor Yellow
Write-Host "  http://localhost:$($env:SERVER_PORT)/api/v1" -ForegroundColor White
Write-Host ""

$startNow = Read-Host "Do you want to start the application now? (y/n)"
if ($startNow -eq "y" -or $startNow -eq "Y") {
    Write-Host "Starting BuildTogether application..." -ForegroundColor Green
    mvn spring-boot:run
} else {
    Write-Host "Application ready to start. Run 'mvn spring-boot:run' when ready." -ForegroundColor Yellow
}
