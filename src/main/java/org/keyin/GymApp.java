package org.keyin;

import org.keyin.memberships.MembershipService;
import org.keyin.user.User;
import org.keyin.user.UserService;
import org.keyin.workoutclasses.WorkoutClassService;

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
                System.out.println("Invalid input. Enter a number: ");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addNewUser(scanner, userService);
                    break;
                case 2:
                    logInAsUser(scanner, userService, membershipService, workoutService);
                    break;
                case 3:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 3);

        scanner.close();
    }

    private static void addNewUser(Scanner scanner, UserService userService) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter role (Admin/Trainer/Member): ");
        String role = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        boolean success = userService.registerUser(username, password, role, email, phoneNumber, address);

        if (success) {
            System.out.println("User registered successfully");
        } else {
            System.out.println("Registration failed");
        }
    }

    private static void logInAsUser(Scanner scanner, UserService userService,
            MembershipService membershipService, WorkoutClassService workoutService) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userService.loginUser(username, password);

        if (user != null) {
            System.out.println("Login successful. Welcome " + user.getUsername());

            switch (user.getRole().toLowerCase()) {
                case "admin":
                    showAdminMenu(scanner);
                    break;
                case "trainer":
                    showTrainerMenu(scanner);
                    break;
                case "member":
                    showMemberMenu(scanner);
                    break;
                default:
                    System.out.println("Unknown role");
            }
        } else {
            System.out.println("Login failed");
        }
    }

    private static void showAdminMenu(Scanner scanner) {
        System.out.println("\nAdmin Menu:");
        System.out.println("1. View users");
        System.out.println("2. View revenue");
        System.out.println("3. Delete a user");
        System.out.println("4. Back");
        int adminChoice = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Option selected: " + adminChoice + " (to do)");
    }

    private static void showTrainerMenu(Scanner scanner) {
        System.out.println("\nTrainer Menu:");
        System.out.println("1. Add a workout class");
        System.out.println("2. View my classes");
        System.out.println("3. Delete a class");
        System.out.println("4. Back");
        int trainerChoice = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Option selected: " + trainerChoice + " (to do)");
    }

    private static void showMemberMenu(Scanner scanner) {
        System.out.println("\nMember Menu:");
        System.out.println("1. View available classes");
        System.out.println("2. Purchase membership");
        System.out.println("3. View membership expenses");
        System.out.println("4. Back");
        int memberChoice = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Option selected: " + memberChoice + " (to do)");
    }
}
