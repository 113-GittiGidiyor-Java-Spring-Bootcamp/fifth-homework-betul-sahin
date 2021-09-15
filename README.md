### School Management System
This is school management system application. You can simply manage students, courses and instructors on the system.

- CRUD operations for Student/Lesson/Instructor.
- Student age validation. (Must be between 18-40 years old)
- Checking the course code. (The course code must be unique)
- Checking student registration. (Maximum 20 students for each course)
- Returning errors as response with the Global Exception Handler.
- Logging of error history.


#### Tech Stack
Spring Boot  
Spring Data Jpa  
Postgre Sql  
Mapstruct  
Lombok  
Swagger

#### Run the Application
1. Run below command.  
   `mvn clean install`
2. Navigate to "target" directory and run the application with below command.  
   `java -jar SchoolManagementSystemV5-0.0.1-SNAPSHOT.jar`

#### License
Distributed under the MIT License. See [LICENSE](https://github.com/113-GittiGidiyor-Java-Spring-Bootcamp/fifth-homework-betul-sahin/blob/main/LICENSE) for more information.  

