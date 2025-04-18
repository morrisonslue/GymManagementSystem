# Java Gym Management System – Javadoc & Developer Documentation

## 1. Key Class and Method Overviews

This section explains the major classes and methods.

### GymApp.java

This is the main class of the whole application. It runs the menus, handles user input, and calls other services.

**Main methods**

- `main()`: Starts the whole program and shows the main menu
- `registerUser()`: Collects input and registers a user
- `loginUser()`: Logs users in and sends them to the right menu
- `showAdminMenu()`, `showTrainerMenu()`, `showMemberMenu()`: Role-based menu options

### User.java

A data class that stores information about each user.

### UserService.java

Handles user logic like logging in and signing up. Uses BCrypt to hash passwords. Talks to UserDao for database access.

**Key methods**

- `registerUser()`: Registers a user and hashes their password
- `loginUser()`: Checks user login
- `getAllUsers()`, `deleteUserByUsername()`: Admin-only actions

### UserDao.java

Handles SQL interactions for users saving, finding, and deleting from the database.

### WorkoutClass.java

Keeps information about a workout class.

### WorkoutClassService.java

Handles creating, editing, viewing, and deleting of workout classes.

**Key methods**

- `addWorkoutClass()`: Create a new class
- `getWorkoutClassesByTrainerId()`: Show all a trainer’s classes
- `updateWorkoutClass()`: Change class info
- `deleteWorkoutClass()`: Remove a class
- `getAllWorkoutClasses()`: Show all classes

### Membership.java

Simple data model for memberships. Has type, description, price, date, and user ID.

### MembershipService.java

Used when someone wants to purchase a membership or view totals.

**Key methods**

- `purchaseMembership()`: Lets members or trainers buy a membership
- `getAllMemberships()`: Admin can view all
- `getTotalRevenue()`: Admin-only
- `getMemberExpenses()`: Shows total spending for a user

## 2. Project Structure

GymManagementSystem/
├── pom.xml
├── README.md
├── javadoc.md
├── scripts.sql
└── src/
└── main/
└── java/
└── org/
└── keyin/
├── GymApp.java
├── database/
│ └── DatabaseConnection.java
├── memberships/
│ ├── Membership.java
│ ├── MembershipDAO.java
│ └── MembershipService.java
├── user/
│ ├── User.java
│ ├── UserDao.java
│ └── UserService.java
│ └── childclasses/
│ ├── Admin.java
│ ├── Member.java
│ └── Trainer.java
└── workoutclasses/
├── WorkoutClass.java
└── WorkoutClassService.java

## 3. Build & Dependency Setup

This is a Maven project. It uses two dependencies:

- `org.postgresql:postgresql`: PostgreSQL JDBC driver
- `org.mindrot:jbcrypt`: Password hashing

To compile:

mvn compile

To run:

mvn exec:java -Dexec.mainClass="org.keyin.GymApp"

## 4. Setting Up the Database

1. Make sure PostgreSQL is installed and running.
2. Create a database.
3. Open terminal or pgAdmin and run the SQL file:

\i scripts.sql

This will create the required tables and insert sample data from the sql file.

## 5. Cloning and Running from GitHub

git clone https://github.com/YOUR_USERNAME/GymManagementSystem.git
cd GymManagementSystem
mvn compile
mvn exec:java -Dexec.mainClass="org.keyin.GymApp"
