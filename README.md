# Flywheel-store An Ecommerce backend application

> A robust, secure E-commerce REST API built with Java and Spring boot.

## Introduction 
Flywheel store is a backend API designed to handle modern e-commerce operations.It features a complete product catalog system, secure user authentication (JWT), and inventory management.
This project handles complex entity relationships and secure order processing.

## Key features 
* **Product Management:** Sellers can list products with categories, description and images.
* **Smart Search:** Filter products by name, brand, category or attributes
* **Shopping Cart:** Persistent cart management with real-time inventory checks.
* **Security:** Statless authentication using **JSON Web Tokens(JWT)** and Spring Security.
* **Order History:** Comprehensive order tracking with timestampping.

## Tech Stack 
* **Core:** Java 17, Spring 3
* **Database:** PostrgesSql, Hibernate(JPA)
* **Security:** Spring Security 6, JWT
  
## Run Flywheel-Store locally

### Prerequistes
*Java 17 or higher
*Postgres Installed and Running
*Maven

### 1.Getting the source
```bash
git clone https://github.com/Kernel-Kratos/Flywheel-store.git
```
### 2. Configure the Database 
Create a PostgresSql database named flywheel_db (or whatever you want to name it).
Open `src/main/resoruces/application.properties` and update your credentials : 
```
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db_name
spring.datasource.username=your_postgres_username
spring.datasource.password=your_postgres_password
# JWT Secret
auth.token.jwtSecret=your_secret_key_here
```
### 3.Run the application 
```bash
mvn spring-boot:run
```
The Api will be availabe at http:localhost8080

### Endpoint structure
* Main endpoint : localhost:8080/api/v1
* Controller endpoints : **localhost:8080/api/v1/pluralform/feature** or **localhost:8080/api/v1/pluralform/singularform/feature** 
> *Eg: localhost:8080/api/v1/products/all or localhost:8080/api/v1/products/product/add*
