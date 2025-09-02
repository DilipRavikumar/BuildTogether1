# 🏢 CORPORATE LAPTOP SETUP GUIDE
## BuildTogether Application Deployment

### 📋 Prerequisites for Corporate Environment

#### **1. Java Requirements**
- ✅ **Java 21** (or higher) - Check with: `java -version`
- ✅ **Maven 3.6+** - Check with: `mvn -version`
- ✅ **Corporate Java approval** - Ensure Java is approved by IT

#### **2. Database Access**
- ✅ **Corporate Oracle Database** access credentials
- ✅ **Network access** to corporate database server
- ✅ **Firewall permissions** for database connection
- ✅ **VPN connection** (if required)

#### **3. Corporate Network**
- ✅ **Approved ports** (8443 recommended)
- ✅ **Domain restrictions** for CORS
- ✅ **Proxy settings** (if required)
- ✅ **Firewall exceptions**

---

### 🗄️ Database Configuration

#### **Option 1: Environment Variables (Recommended)**
```bash
# Windows Command Prompt
set DB_HOST=your-corporate-db-server
set DB_PORT=1521
set DB_SID=your-database-sid
set DB_USERNAME=your-corporate-username
set DB_PASSWORD=your-corporate-password
set DB_SCHEMA=your-schema-name

# Windows PowerShell
$env:DB_HOST="your-corporate-db-server"
$env:DB_PORT="1521"
$env:DB_SID="your-database-sid"
$env:DB_USERNAME="your-corporate-username"
$env:DB_PASSWORD="your-corporate-password"
$env:DB_SCHEMA="your-schema-name"

# Linux/Mac
export DB_HOST=your-corporate-db-server
export DB_PORT=1521
export DB_SID=your-database-sid
export DB_USERNAME=your-corporate-username
export DB_PASSWORD=your-corporate-password
export DB_SCHEMA=your-schema-name
```

#### **Option 2: Direct Configuration**
Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:oracle:thin:@your-corporate-db-server:1521:your-sid
spring.datasource.username=your-corporate-username
spring.datasource.password=your-corporate-password
spring.jpa.properties.hibernate.default_schema=your-schema-name
```

---

### 🌐 Server Configuration

#### **Port Configuration**
```bash
# Use corporate-approved port (8443 recommended)
set SERVER_PORT=8443

# Or update application.properties directly
server.port=8443
```

#### **CORS Configuration**
```bash
# Restrict to corporate domains
set CORS_ORIGINS=https://your-corporate-domain.com,https://app.corporate.com
set CORS_METHODS=GET,POST,PUT,DELETE,OPTIONS
set CORS_HEADERS=Content-Type,Authorization,X-Requested-With
```

---

### 📝 Logging Configuration

#### **Corporate-Friendly Logging**
```bash
# Reduce logging for corporate environments
set LOG_LEVEL=INFO
set HIBERNATE_SQL=WARN
set HIBERNATE_BINDER=WARN
set SPRING_WEB=WARN
set SPRING_DATA=WARN
set HIBERNATE_ORM=WARN

# Log file settings
set LOG_FILE=logs/buildtogether.log
set LOG_MAX_SIZE=5MB
set LOG_HISTORY=7
```

---

### 🚀 Deployment Steps

#### **Step 1: Environment Setup**
```bash
# 1. Set all required environment variables
# 2. Ensure VPN connection (if required)
# 3. Verify database connectivity
```

#### **Step 2: Database Schema**
```bash
# Connect to corporate database
sqlplus username/password@host:port:sid

# Run schema script
@database/schema.sql

# Run sample data (optional)
@database/sample_data.sql
```

#### **Step 3: Application Deployment**
```bash
# 1. Clean and compile
mvn clean compile

# 2. Run application
mvn spring-boot:run

# 3. Verify startup
# Check logs for successful database connection
# Verify application accessible at: https://localhost:8443/api/v1
```

---

### 🔍 Verification Checklist

#### **Database Connection**
- ✅ Application starts without database errors
- ✅ Database tables created successfully
- ✅ Sample data loaded (if applicable)

#### **Application Access**
- ✅ Application accessible on configured port
- ✅ API endpoints responding
- ✅ CORS working for corporate domains

#### **Logging**
- ✅ Appropriate log levels set
- ✅ Log files created in specified location
- ✅ No sensitive information in logs

---

### 🚨 Troubleshooting

#### **Common Issues**

**1. Database Connection Failed**
```bash
# Check environment variables
echo %DB_HOST%
echo %DB_USERNAME%

# Test database connectivity
sqlplus username/password@host:port:sid
```

**2. Port Already in Use**
```bash
# Check port usage
netstat -an | findstr 8443

# Use different port
set SERVER_PORT=8444
```

**3. CORS Issues**
```bash
# Verify CORS configuration
echo %CORS_ORIGINS%
echo %CORS_METHODS%
```

**4. Java Version Issues**
```bash
# Check Java version
java -version

# Ensure JAVA_HOME is set
echo %JAVA_HOME%
```

---

### 📞 Corporate IT Support

#### **Required Information**
- Application name: BuildTogether
- Port: 8443 (configurable)
- Database: Oracle (JDBC)
- Java: Version 21
- Maven: Version 3.6+

#### **Firewall Exceptions**
- Port: 8443 (application)
- Database: 1521 (Oracle)
- Outbound: Corporate database server

#### **Domain Restrictions**
- CORS origins: Corporate domains only
- Methods: GET, POST, PUT, DELETE, OPTIONS
- Headers: Content-Type, Authorization, X-Requested-With

---

### ✅ Success Criteria

Your BuildTogether application is successfully deployed on corporate laptop when:

1. ✅ **Application starts** without errors
2. ✅ **Database connects** successfully
3. ✅ **API accessible** on configured port
4. ✅ **CORS working** for corporate domains
5. ✅ **Logging appropriate** for corporate environment
6. ✅ **No security violations** detected
7. ✅ **IT compliance** requirements met

---

### 🔐 Security Notes

- **Never commit** corporate credentials to version control
- **Use environment variables** for sensitive configuration
- **Restrict CORS** to corporate domains only
- **Monitor logs** for sensitive information
- **Follow corporate** security policies
- **Regular updates** for security patches
