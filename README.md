# Book Network API

A robust backend service for a Book Networking platform built with **Java 17**, **Spring Boot 3**, and **Spring Security**. This application handles user registration, secure authentication, and email verification.

## 🚀 Features

- **User Management**: Registration and authentication with encrypted passwords.
- **Secure Authentication**: JWT-based stateless authentication.
- **Email Verification**: Asynchronous email sending using Thymeleaf templates for account activation.
- **Validation**: Strict input validation using Jakarta Validation.
- **Database**: Persistence layer powered by Spring Data JPA.

## 🛠️ Tech Stack

- **Backend**: Java 17, Spring Boot 3.4+
- **Security**: Spring Security, JWT (JSON Web Tokens)
- **Database**: PostgreSQL / MySQL (via Spring Data JPA)
- **Email**: Spring Mail, Thymeleaf Templates
- **Utilities**: Lombok, Jakarta EE

## 📋 Prerequisites

- **Java SDK 17** or higher
- **Maven** 3.8+
- **Mail Server**: Access to an SMTP server (e.g., Mailtrap for development)
- **Database**: A running instance of your preferred SQL database

## ⚙️ Configuration

Update your `src/main/resources/application.yml` (or `application.properties`) with your environment details:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/book_network
    username: your_username
    password: your_password
  
  mail:
    host: smtp.mailtrap.io
    port: 2525
    username: your_smtp_username
    password: your_smtp_password

application:
  mailing:
    frontend:
      activation-url: http://localhost:4200/activate-account
```
