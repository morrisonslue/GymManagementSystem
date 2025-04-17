package org.keyin;

import org.keyin.memberships.MembershipService;
import org.keyin.user.User;
import org.keyin.user.UserService;
import org.keyin.workoutclasses.WorkoutClass;
import org.keyin.workoutclasses.WorkoutClassService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GymApp {
    public static void main(String[] args) {
        // service objects
        UserService userService = new UserService();
        MembershipService membershipService = new MembershipService();
        WorkoutClassService workoutService = new WorkoutClassService();

        // scanner
        Scanner scanner = new Scanner(System.in);
        int choice;

        // loop main menu
        do {
            // show menu
            System.out.println("\n=== Gym Management System ===");
            System.out.println("1. Register new user");
            System.out.println("2. Login as user");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            // input check
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Enter a number.");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            // run option
            switch (choice) {
                case 1:
                    registerUser(scanner, userService);
                    break;
                case 2:
                    loginUser(scanner, userService, membershipService, workoutService);
                    break;
                case 3:
                    System.out.println("Exiting... Goodbye");
                    break;
                default:
                    System.out.println("Invalid option - try again");
            }

        } while (choice != 3);

        scanner.close();
    }

    // register new user
    private static void registerUser(Scanner scanner, UserService userService) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter role (Admin/Trainer/Member): ");
        String role = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        // try to register
        boolean success = userService.registerUser(username, password, role, email, phone, address);

        if (success) {
            System.out.println("User registered successfully");
        } else {
            System.out.println("Registration failed");
        }
    }

    // log user in and route to role menu
    private static void loginUser(Scanner scanner, UserService userService,
            MembershipService membershipService,
            WorkoutClassService workoutService) {

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // check credentials
        User user = userService.loginUser(username, password);

        if (user != null) {
            System.out.println("Login successful. Welcome " + user.getUsername());

            // send to role menu
            switch (user.getRole().toLowerCase()) {
                case "admin":
                    showAdminMenu(scanner, user, userService, membershipService, workoutService);
                    break;
                case "trainer":
                    showTrainerMenu(scanner, user, workoutService, membershipService);
                    break;
                case "member":
                    showMemberMenu(scanner, user, membershipService);
                    break;
                default:
                    System.out.println("Unknown role");
            }
        } else {
            System.out.println("Login failed");
        }
    }

    // trainer menu
    private static void showTrainerMenu(Scanner scanner, User trainer, WorkoutClassService workoutService,
            MembershipService membershipService) {
        int choice;
        do {
            // show options
            System.out.println("\n=== Trainer Menu ===");
            System.out.println("1. Add Workout Class");
            System.out.println("2. Delete Workout Class");
            System.out.println("3. View My Classes");
            System.out.println("4. Purchase Membership");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input - enter a number");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            // run choice
            switch (choice) {
                case 1:
                    System.out.print("Enter class type: ");
                    String type = scanner.nextLine();
                    System.out.print("Enter class description: ");
                    String desc = scanner.nextLine();

                    WorkoutClass wc = new WorkoutClass(type, desc, trainer.getId());
                    try {
                        workoutService.addWorkoutClass(wc);
                        System.out.println("Class added");
                    } catch (SQLException e) {
                        System.out.println("Error adding class");
                    }
                    break;

                case 2:
                    System.out.print("Enter ID of class to delete: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        workoutService.deleteWorkoutClass(id);
                        System.out.println("Class deleted");
                    } catch (SQLException e) {
                        System.out.println("Error deleting class");
                    }
                    break;

                case 3:
                    try {
                        List<WorkoutClass> classes = workoutService.getWorkoutClassesByTrainerId(trainer.getId());
                        for (WorkoutClass c : classes) {
                            System.out.println(c);
                        }
                    } catch (SQLException e) {
                        System.out.println("Could not fetch classes");
                    }
                    break;

                case 4:
                    try {
                        membershipService.purchaseMembership(scanner, trainer);
                    } catch (SQLException e) {
                        System.out.println("Error buying membership");
                    }
                    break;

                case 5:
                    System.out.println("Going back to main menu");
                    break;

                default:
                    System.out.println("Invalid option");
            }

        } while (choice != 5);
    }

    // member menu
    private static void showMemberMenu(Scanner scanner, User member, MembershipService membershipService) {
        int choice;
        do {
            System.out.println("\n=== Member Menu ===");
            System.out.println("1. View Available Classes");
            System.out.println("2. Purchase Membership");
            System.out.println("3. View Total Membership Expenses");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input - enter a number: ");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    try {
                        List<WorkoutClass> allClasses = new WorkoutClassService().getAllWorkoutClasses();
                        for (WorkoutClass wc : allClasses) {
                            System.out.println(wc);
                        }
                    } catch (SQLException e) {
                        System.out.println("Error fetching classes");
                    }
                    break;

                case 2:
                    try {
                        membershipService.purchaseMembership(scanner, member);
                    } catch (SQLException e) {
                        System.out.println("Error buying membership");
                    }
                    break;

                case 3:
                    try {
                        double total = membershipService.getMemberExpenses(member.getId());
                        System.out.println("Your total membership expenses: $" + total);
                    } catch (SQLException e) {
                        System.out.println("Error fetching expenses.");
                    }
                    break;

                case 4:
                    System.out.println("Returning to main menu.");
                    break;

                default:
                    System.out.println("Invalid option");
            }

        } while (choice != 4);
    }

    // admin menu
    private static void showAdminMenu(Scanner scanner, User admin, UserService userService,
            MembershipService membershipService,
            WorkoutClassService workoutService) {
        int choice;
        do {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View All Users");
            System.out.println("2. Delete User by Username");
            System.out.println("3. View Total Membership Expenses");
            System.out.println("4. View All Memberships");
            System.out.println("5. View Total Revenue");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input - enter a number:");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    List<User> users = userService.getAllUsers();
                    for (User u : users) {
                        System.out.println(u.getId() + ": " + u.getUsername() + " | " + u.getEmail() + " | "
                                + u.getPhoneNumber() + " | " + u.getRole());
                    }
                    break;
                case 2:
                    System.out.print("Enter username you want to delete: ");
                    String username = scanner.nextLine();
                    boolean success = userService.deleteUserByUsername(username);
                    System.out.println(success ? "User deleted" : "Error deleting user");
                    break;
                case 3:
                    try {
                        List<String> memberships = membershipService.getAllMemberships();
                        for (String m : memberships) {
                            System.out.println(m);
                        }
                    } catch (SQLException e) {
                        System.out.println("Error getting memberships");
                    }
                    break;
                case 4:
                    try {
                        double revenue = membershipService.getTotalRevenue();
                        System.out.println("Total revenue: $" + revenue);
                    } catch (SQLException e) {
                        System.out.println("Error getting revenue");
                    }
                    break;
                case 5:
                    System.out.println("Returning to main menu");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 5);
    }
}
