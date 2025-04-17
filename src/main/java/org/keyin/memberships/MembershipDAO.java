package org.keyin.memberships;

import org.keyin.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembershipDAO {

    public void addMembership(Membership membership) throws SQLException {
        String sql = "INSERT INTO memberships (membershiptype, membership_price, membership_description, date_purchased, user_id) VALUES (?, ?, ?, CURRENT_DATE, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, membership.getMembershipType());
            pstmt.setDouble(2, membership.getMembershipPrice());
            pstmt.setString(3, membership.getDescription());
            pstmt.setInt(4, membership.getUserId());

            pstmt.executeUpdate();
        }
    }

    public List<String> getAllMemberships() throws SQLException {
        String sql = "SELECT * FROM memberships";
        List<String> memberships = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String entry = rs.getInt("membership_id") + ": " +
                        rs.getString("membershiptype") + " - $" +
                        rs.getDouble("membership_price") + " (" +
                        rs.getString("membership_description") + ")";
                memberships.add(entry);
            }
        }

        return memberships;
    }

    public double getTotalRevenue() throws SQLException {
        String sql = "SELECT SUM(membership_price) AS total FROM memberships";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("total");
            }
        }

        return 0.0;
    }

    public double getTotalExpensesByUserId(int userId) throws SQLException {
        String sql = "SELECT COALESCE(SUM(membership_price), 0) AS total FROM memberships WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        }
        return 0.0;
    }

}
