# Gym Management System

A Java console app for managing users, workout classes, and gym memberships.

## Features

### Login & Registration

- Register new users
- Secure login via hashed passwords

### Admin

- View all users
- Delete a user by username
- View all memberships
- View total membership revenue

### Trainer

- Add new workout classes
- View own classes
- Update or delete own classes
- Purchase memberships

### Member

- View available classes
- Purchase memberships
- See membership expenses

## Technologies Used

- Java
- PostgreSQL
- BCrypt

## How to Run

- Set up PostgreSQL and run `scripts.sql` to create the tables
- Update your `DatabaseConnection.java` with your DB credentials
- Run `GymApp.java` from your CLI
