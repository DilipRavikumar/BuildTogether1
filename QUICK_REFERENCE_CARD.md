# 🎯 BuildTogether Quick Reference Card

## 🚀 Technology Stack (S.O.L.O)
- **S**pring Boot 3.2.0 - Rapid development framework
- **O**racle Database 12c+ - Enterprise database
- **L**ombok - Code generation
- **O**penAPI/Swagger - API documentation

## 🏗️ Architecture Layers (C.B.P.D)
- **C**ontroller - REST API endpoints
- **B**usiness - DTOs and validation
- **P**ersistence - Repositories and entities
- **D**atabase - Oracle tables and constraints

## 🔑 Key Annotations
```java
@Entity          // Maps class to database table
@Table(name="")  // Specifies table name
@Id              // Primary key
@GeneratedValue  // Auto-generated ID
@Column          // Column mapping
@OneToMany       // One-to-many relationship
@ManyToOne       // Many-to-one relationship
@JoinColumn      // Foreign key mapping
@JsonIgnore      // Prevents circular references
@Transactional   // Transaction management
```

## 🗄️ Database Features
- **IDENTITY** - Auto-incrementing primary keys
- **CLOB** - Large text storage
- **VARCHAR2** - Variable length strings
- **NUMBER** - Numeric data types
- **TIMESTAMP** - Date and time
- **CONSTRAINTS** - Data integrity rules

## 📊 Entity Relationships
```
User (1) ──── (M) UserSkill (M) ──── (1) Skill
User (1) ──── (M) TeamMember (M) ──── (1) Team
User (1) ──── (M) JoinRequest (M) ──── (1) Team
User (1) ──── (M) Team (M) ──── (1) Hackathon
Team (1) ──── (M) Submission (M) ──── (1) Hackathon
```

## 🎯 DTO Pattern Benefits
- **Clean API** - Only expose necessary data
- **No Lazy Loading** - Prevents session issues
- **Versioning** - Easy API evolution
- **Performance** - Reduced data transfer

## ⚡ Performance Tips
- **Lazy Loading** - Load data on demand
- **Eager Loading** - Load data immediately
- **Caching** - Store frequently accessed data
- **Indexing** - Speed up database queries

## 🔧 Common Patterns
- **Repository Pattern** - Data access abstraction
- **DTO Pattern** - Clean data transfer
- **Layered Architecture** - Separation of concerns
- **MVC Pattern** - Model-View-Controller

## 🎨 Validation Annotations
```java
@NotBlank        // Required non-empty string
@NotNull         // Required non-null value
@Size(min, max)  // String/collection size
@Email           // Valid email format
@Future          // Future date
@Min(value)      // Minimum numeric value
@Max(value)      // Maximum numeric value
```

## 🚨 Error Handling
```java
@ExceptionHandler        // Handle specific exceptions
ResponseEntity          // HTTP response wrapper
@ResponseStatus         // HTTP status code
ErrorResponse          // Custom error format
```

## 📝 Best Practices
- **Package Structure** - Organize by layer
- **Naming Conventions** - Clear, descriptive names
- **Documentation** - Swagger/OpenAPI
- **Testing** - Unit and integration tests
- **Configuration** - External properties
- **Security** - Input validation

## 🎓 Learning Mnemonics
- **Spring Boot** = "S.T.A.R.S" (Starter, Tomcat, Actuator, REST, Security)
- **JPA** = "J.E.T.S" (JoinColumn, Entity, Table, SequenceGenerator)
- **Oracle** = "A.C.I.D" (Atomicity, Consistency, Isolation, Durability)
- **Relationships** = "O.N.E" (One-to-One, Not Null, Eager/Lazy)

## 🔄 Development Workflow
1. **Design** - Plan entities and relationships
2. **Create** - Build entities with annotations
3. **Map** - Configure JPA relationships
4. **Validate** - Add validation annotations
5. **Test** - Create repositories and services
6. **API** - Build REST controllers
7. **Document** - Add Swagger documentation
8. **Deploy** - Configure for production

## 🎯 Key Takeaways
- **Spring Boot** = Fast development
- **JPA/Hibernate** = Object-relational mapping
- **Oracle** = Enterprise database
- **DTOs** = Clean API design
- **Validation** = Data quality
- **Documentation** = Self-documenting APIs

---

*Keep this card handy for quick reference during development and interviews!*
