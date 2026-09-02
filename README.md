# product-management

## Prerequisites

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
docker compose down -v
```
