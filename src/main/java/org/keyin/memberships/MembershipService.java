package org.keyin.memberships;

import org.keyin.user.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MembershipService {

    private MembershipDAO membershipDAO = new MembershipDAO();

    // member or trainer membership purchase
    public void purchaseMembership(Scanner scanner, User user) throws SQLException {
        System.out.print("Enter membership type (e.g., Basic, Premium): ");
        String type = scanner.nextLine();

        System.out.print("Enter membership description: ");
        String description = scanner.nextLine();

        System.out.print("Enter membership price: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        double price = scanner.nextDouble();
        scanner.nextLine(); 

        Membership membership = new Membership(type, description, price, user.getId());
        membershipDAO.addMembership(membership);
        System.out.println("Membership purchased successfully!");
    }

    // view all memberships
    public List<String> getAllMemberships() throws SQLException {
        return membershipDAO.getAllMemberships();
    }

    // view total revenue
    public double getTotalRevenue() throws SQLException {
        return membershipDAO.getTotalRevenue();
    }
}
