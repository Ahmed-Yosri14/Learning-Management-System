# Learning Management System (LMS)

## 🎓 Overview
A comprehensive Learning Management System built with Spring Boot that facilitates course management, student assessments, and educational content delivery. This enterprise-grade application implements a clean architecture following domain-driven design principles.

## 🏗️ Architecture

The project follows a layered architecture pattern:

### Entity Layer
- Core domain models including:
  - Answer (AnswerFormat, MCQAnswer, TrueFalseAnswer, WrittenAnswer)
  - Assessment (Assessment, Assignment, Quiz, StudentQuiz)
  - Feedback (AssignmentFeedback, QuizFeedback)
  - Submission (AssignmentSubmission, QuizSubmission)
  - User Management (Attendance, Course, CourseMaterial)
  - Learning (Enrollment, Lesson, Notification)
  - Question Management (Question, QuestionAnswer)

### Repository Layer
Implements data access using Spring Data JPA:
```java
- AnswerFormatRepository
- AssessmentRepository
- CourseRepository
- EnrollmentRepository
- FeedbackRepository
- LessonRepository
- NotificationRepository
- QuestionRepository
- UserRepository
// ... and more
```

### Service Layer
Business logic implementation:
```java
Assessment Services:
- AssessmentService
- AssignmentService
- QuizService

Feedback Services:
- AssignmentFeedbackService
- FeedbackService
- QuizFeedbackService

Core Services:
- AuthenticationService
- CourseService
- EmailService
- FileStorageService
- NotificationService
- ProgressService
```

### Controller Layer
REST API endpoints:
```java
- AssignmentRestController
- AttendanceRestController
- AuthenticationController
- CourseRestController
- EnrollmentRestController
- LessonRestController
- QuizRestController
- UserRestController
```

## 🚀 Features
- User Authentication & Authorization
- Course Management
- Assignment Creation & Submission
- Quiz Management
- Student Progress Tracking
- Attendance Management
- Course Material Management
- Real-time Notifications
- Feedback System
- Excel Report Generation
- File Storage Integration
- Email Notifications

## 💻 Technical Stack
- **Framework**: Spring Boot
- **Security**: Spring Security with JWT
- **Database**: JPA/Hibernate
- **API Documentation**: Swagger/OpenAPI
- **Build Tool**: Maven/Gradle
- **Testing**: JUnit, Mockito

## 🛠️ Setup & Installation
1. Clone the repository
```bash
git clone https://github.com/Ahmed-Yosri14/Learning-Management-System.git
```

2. Configure database properties in `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/lms_db
spring.datasource.username=root
spring.datasource.password=your_password
```

3. Build the project
```bash
./mvnw clean install
```

4. Run the application
```bash
./mvnw spring-boot:run
```

## 📚 API Documentation
Access Swagger UI: `http://localhost:8080/swagger-ui.html`

## 🔐 Security
- JWT-based authentication
- Role-based access control
- Password encryption
- Session management

## 🔄 Database Schema
The system uses a relational database with tables corresponding to the entity classes shown in the project structure.

## 🧪 Testing
```bash
./mvnw test
```

## 📦 Deployment
1. Build the JAR file
```bash
./mvnw package
```

2. Run the JAR file
```bash
java -jar target/lms-0.0.1-SNAPSHOT.jar
```

## 🤝 Contributing
1. Fork the repository
2. Create a feature branch
3. Commit changes
4. Push to the branch
5. Create a Pull Request


## 👥 Authors
[Sherif Youssef](https://github.com/Sherif-Youssef)

[Ahmed Yosri](https://github.com/Ahmed-Yosri14)

[Mohamed Hesham](https://github.com/Mohamed-hesham-21)

[Usama Refaat](https://github.com/UsamaRefaat)

[Omar Abftah](https://github.com/Omar-abftah)

[Ali Mohsen](https://github.com/alimohse)

## 🔄 Project Status
Active Development
