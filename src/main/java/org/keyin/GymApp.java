package org.keyin;

import org.keyin.memberships.MembershipService;
import org.keyin.user.User;
import org.keyin.user.UserService;
import org.keyin.workoutclasses.WorkoutClassService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GymApp {
    public static void main(String[] args) {
        UserService userService = new UserService();
        MembershipService membershipService = new MembershipService();
        WorkoutClassService workoutService = new WorkoutClassService();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Gym Management System ===");
            System.out.println("1. Add a new user");
            System.out.println("2. Login as a user");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addNewUser(scanner, userService);
                    break;
                case 2:
                    logInAsUser(scanner, userService, membershipService, workoutService);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (choice != 3);

        scanner.close();
    }

    private static void addNewUser(Scanner scanner, UserService userService) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Role (Admin/Trainer/Member): ");
        String role = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();

        boolean success = userService.registerUser(username, password, role, email, phone, address);

        if (success) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Registration failed.");
        }
    }

    private static void logInAsUser(Scanner scanner, UserService userService, MembershipService membershipService,
            WorkoutClassService workoutService) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = userService.loginUser(username, password);
        if (user != null) {
            System.out.println("Welcome " + user.getUsername());

            switch (user.getRole().toLowerCase()) {
                case "admin":
                    showAdminMenu(scanner, userService);
                    break;
                case "trainer":
                    System.out.println("Trainer menu (to be implemented)");
                    break;
                case "member":
                    System.out.println("Member menu (to be implemented)");
                    break;
                default:
                    System.out.println("Unknown role.");
            }
        } else {
            System.out.println("Login failed.");
        }
    }

    private static void showAdminMenu(Scanner scanner, UserService userService) {
        int option;
        do {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Users");
            System.out.println("2. Delete a User by Username");
            System.out.println("3. Go Back");
            System.out.print("Your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input.");
                scanner.next();
            }

            option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    List<User> users = userService.getAllUsers();
                    for (User u : users) {
                        System.out.println(
                                u.getId() + " | " + u.getUsername() + " | " + u.getRole() + " | " + u.getEmail());
                    }
                    break;
                case 2:
                    System.out.print("Enter username to delete: ");
                    String userToDelete = scanner.nextLine();
                    boolean deleted = userService.deleteUserByUsername(userToDelete);
                    if (deleted) {
                        System.out.println("User deleted.");
                    } else {
                        System.out.println("User not found or error.");
                    }
                    break;
                case 3:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }

        } while (option != 3);
    }
}
