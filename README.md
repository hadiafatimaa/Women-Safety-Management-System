# Women Safety Management System

A web-based women safety application built with **Java Servlets** (backend) and **HTML/CSS/JavaScript** (frontend) using MVC architecture and MySQL.

## Project Structure
## 📁 Project Structure

```
WomenSafetySystem/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/womensafety/
│       │       ├── db/
│       │       ├── filehandler/
│       │       │   ├── AppFile.java
│       │       │   ├── ChatHistory.java
│       │       │   ├── JournalEntry.java
│       │       │   └── SosLog.java
│       │       └── servlets/
│       │           ├── Contact.java
│       │           ├── Debug.java
│       │           ├── GetSession.java
│       │           ├── Incident.java
│       │           ├── Journal.java
│       │           ├── Login.java
│       │           ├── Logout.java
│       │           ├── Register.java
│       │           ├── SOS.java
│       │           ├── TestDB.java
│       │           └── UpdateProfile.java
│       ├── resources/
│       └── webapp/
│           ├── WEB-INF/
│           │   └── web.xml
│           ├── index.html
│           ├── dashboard.html
│           ├── SOS.html
│           ├── Incident.html
│           └── contacts.html
└── pom.xml
```

## Prerequisites

- Java 11+
- Apache Tomcat 9+
- MySQL 8+
- Maven
- IntelliJ IDEA (recommended)

## Step 1: Clone the Project

```bash
git clone https://github.com/hadiafatimaa/Women-Safety-Management-System.git
```

Open in IntelliJ IDEA → File → Open.

## Step 2: Setup MySQL Database

1. Start MySQL service
2. Create database: `women_safety`
3. Run the schema file:

```sql
source path/to/schema.sql
```

## Step 3: Backend Setup

Edit database credentials in `db/` connection class, then build:

```bash
mvn clean install
```

Deploy WAR to Apache Tomcat. Backend runs at:
`http://localhost:8080/Women_Safety_Management_System/`

## Step 4: Access the App

Open browser and go to:

http://localhost:8080/Women_Safety_Management_System/

## Demo Login

| Role | Email | Password |
|------|-------|----------|
| User | your email | •••• |

## Features

- User registration (Full Name, Email, Password, Phone Number)
- User login and logout
- SOS Alert — send emergency alert with live location
- Report Incident — report a safety incident with location details
- Emergency Contacts — manage your emergency contact list
- My Profile — view and update profile information
- Daily Journal — write daily safety thoughts and notes
- Session management

## Tech Stack

- **Frontend:** HTML, CSS, JavaScript
- **Backend:** Java Servlets (MVC)
- **Database:** MySQL
- **Build Tool:** Maven
- **Server:** Apache Tomcat (SmartTomcat plugin)

## Author

Hadia Fatima
