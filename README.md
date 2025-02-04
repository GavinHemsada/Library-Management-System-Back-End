# Library-Management-System-Back-End

## Overview
The **Library Management System** is a backend application built using **Spring Boot**. It provides a secure and efficient way to manage library operations, including user authentication, book management, and email confirmation using **JWT** for authentication and **SMTP** for email verification.


## Features
- **User Authentication & Authorization**
  - JWT-based authentication
  - Role-based access control (Admin, Librarian, User)
- **Email Confirmation**
  - SMTP-based email verification for new users
- **Book Management**
  - Add, update, delete, and search books
  - Track book availability
- **User Management**
  - User registration and profile management
  - Borrowing history and fine management
- **Loan Management**
  - Issue and return books

## Technologies Used
- **Spring Boot 3** (Backend framework)
- **Spring Security** (Authentication & Authorization)
- **JWT** (JSON Web Token for secure authentication)
- **SMTP & JavaMailSender** (For email notifications)
- **Spring Data JPA** (For database operations)
- **H2 / MySQL** (Database options)
- **Lombok** (For reducing boilerplate code)
- **Swagger** (API documentation)

## Installation
### Prerequisites
- Java 17+
- Maven 3+
- MySQL (or use H2 in-memory database for testing)

### Steps to Run
1. **Clone the repository**
   ```sh
   git clone https://github.com/your-username/Library-Management-System-Back-End.git
   cd library-management-system
   ```
2. **Configure the database**
   Update `application.properties` or `application.yml` with your MySQL credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/library_db
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Configure Email SMTP**
   ```properties
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=your-email@gmail.com
   spring.mail.password=your-email-password
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true
   ```

4. **Build and Run the Application**
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

## API Endpoints
### Authentication
| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | `/api/ngCRUD/user/register` | Register a new user |
| GET | `/api/ngCRUD/user/login` | Login |
| GET | `/api/auth/tokenCheck?token=xyz` | Verify user email |

### Books
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/api/ngCRUD/books` | Get all books |
| POST | `/api/ngCRUD/books` | Add a new book |
| PUT | `/api/ngCRUD/books` | Update book details |
| DELETE | `/api/ngCRUD/books/{id}` | Delete a book |


## Security Implementation
- Users must authenticate using **JWT tokens**.
- Passwords are securely stored using **BCrypt hashing**.
- Only verified users can access restricted endpoints.

## Contribution
Feel free to contribute to this project by submitting issues or pull requests.

## License
This project is licensed under the **MIT License**.

---

Happy Coding! 🚀

