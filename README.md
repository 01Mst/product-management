# product-management

## SET-UP Prerequisites

Before running the application, make sure you have:

* **Git** – To clone the repository.
* **Docker Desktop** – To build and run the application.

> Java, Maven, and PostgreSQL do not need to be installed separately when using Docker. They are provided through the Docker setup.

## Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd product-management
```

### 2. Create `.env` File

Create a `.env` file in the project root:

```env
DB_URL=jdbc:postgresql://postgres:5432/product_management
DB_USERNAME=postgres
DB_PASSWORD=postgres

JWT_SECRET=your-secret-key
JWT_EXPIRATION=900000

ADMIN_USERNAME=admin
ADMIN_PASSWORD=your-admin-password
```

### 3. Start the Application

Run:

```bash
docker compose up --build
```

This will automatically:

* Start PostgreSQL
* Build the Spring Boot application
* Start the application
* Connect the application to PostgreSQL

### 4. Access the Application

Application:

```text
http://localhost:8080
```

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

### 5. Stop the Application

```bash
docker compose down
```

> To remove the database data as well:

```bash


Application Architecture

The application follows a layered architecture using Spring Boot.

Controller Layer

Handles incoming HTTP requests and returns API responses. It also performs request validation.

Service Layer

Contains the application's business logic. It handles product and item operations, validations, and business rules.

Repository Layer

Uses Spring Data JPA to communicate with the PostgreSQL database and perform CRUD operations.

Entity Layer

Contains the JPA entities that represent the database tables, mainly Product and Item.

Security Layer

Handles JWT-based authentication and role-based authorization.

USER – Can view products and items.
ADMIN – Can view, create, update, and delete products and create items.
Exception Handling

A global exception handler manages validation, resource-not-found, business, and unexpected errors and returns consistent error responses.

Application Bootstrap

During application startup, the application automatically checks whether the default admin user exists.

If the admin user does not exist, it creates the user with the configured credentials and assigns the ADMIN role.

The credentials are configured using environment variables:

app:
  admin:
    username: ${ADMIN_USERNAME:admin}
    password: ${ADMIN_PASSWORD:Admin@123}

Default credentials:

Username: admin
Password: Admin@123
Role: ADMIN

For production, the default credentials should be replaced using environment variables.

Functional Flow
Authentication
User sends username and password to the login API.
The application validates the credentials.
A JWT access token is generated.
The client uses the token for secured APIs.
Product Management
Client sends a product request.
JWT authentication and role authorization are checked.
Controller receives the request.
Service processes the business logic.
Repository performs the database operation.
The response is returned to the client.
Item Management
Client sends an item request for a product.
JWT authentication and role authorization are checked.
Service validates the product and item data.
Repository saves or retrieves the item.
The response is returned to the client.
Product Deletion

Before deleting a product, the application checks whether the product has associated items. If items exist, the deletion is rejected to maintain the product-item relationship.

API Testing

The APIs can be tested using Swagger UI.

Open:

http://localhost:8080/swagger-ui/index.html
Login

Use:

POST /api/v1/auth/login

Default admin credentials:

{
  "username": "admin",
  "password": "Admin@123"
}

Copy the JWT token from the response.

Authorize Swagger

Click Authorize and enter:

Bearer <JWT_TOKEN>

Then test the secured APIs.

Role-Based Testing
ADMIN can create, update, and delete products and create items.
USER can view products and items.
A request without a valid token returns 401 Unauthorized.
A USER attempting an ADMIN operation returns 403 Forbidden.
docker compose down -v
```
