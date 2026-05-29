

# Women Safety Management System

A web-based women safety application built with **Java Servlets** (backend) and **HTML/CSS** (frontend) using MVC architecture and MySQL.

## Project Structure

WomenSafetySystem/
├── src/main/
│   ├── java/
│   │   └── com.womensafety/
│   │       ├── db/                     # Database connection
│   │       ├── filehandler/
│   │       │   ├── AppFile.java        # File handling
│   │       │   ├── ChatHistory.java    # Chat history
│   │       │   ├── JournalEntry.java   # Journal entries
│   │       │   └── SosLog.java        # SOS logs
│   │       └── servlets/
│   │           ├── Contact.java        # Emergency contacts
│   │           ├── Debug.java          # Debug servlet
│   │           ├── GetSession.java     # Session handling
│   │           ├── Incident.java       # Incident reporting
│   │           ├── Journal.java        # Journal servlet
│   │           ├── Login.java          # Authentication
│   │           ├── Logout.java         # Logout
│   │           ├── Register.java       # User registration
│   │           ├── SOS.java            # SOS alert system
│   │           ├── TestDB.java         # Database testing
│   │           └── UpdateProfile.java  # Profile update
│   ├── resources/                      # Config resources
│   └── webapp/
│       ├── WEB-INF/
│       │   └── web.xml                 # Servlet configuration
│       └── [HTML pages]                # Frontend pages
└── pom.xml                             # Maven dependencies

## Prerequisites

- Java 11+
- Apache Tomcat 9+
- MySQL 8+
- Maven
- VS Code or IntelliJ IDEA

## Step 1: Clone the Project

```bash
git clone https://github.com/hadiafatimaa/Women-Safety-Management-System.git
```

Open the folder in your IDE.

## Step 2: Setup MySQL Database

1. Start MySQL service
2. Create a database named `women_safety`
3. Import the schema:

```sql
source path/to/schema.sql
```

## Step 3: Configure Backend

Edit `src/main/webapp/WEB-INF/web.xml` with your DB credentials:

```xml
<context-param>
  <param-name>DB_PASSWORD</param-name>
  <param-value>your_mysql_password</param-value>
</context-param>
```

Then build and run:

```bash
mvn clean install
```

Deploy the WAR to Tomcat.

## Step 4: Access the App

App runs at: `http://localhost:8080/Women-Safety-Management-System`

## Demo Login

| Role  | Email              | Password  |
|-------|--------------------|-----------|
| Admin | admin@safety.com   | admin123  |
| User  | user@safety.com    | user123   |

## Features

- User registration and login
- SOS alert system
- Incident reporting
- Emergency contacts management
- User dashboard
- Responsive HTML frontend

## Tech Stack

- **Frontend:** HTML, CSS
- **Backend:** Java Servlets, JSP
- **Database:** MySQL
- **Build Tool:** Maven
- **Server:** Apache Tomcat

## Author

Hadia Fatima

