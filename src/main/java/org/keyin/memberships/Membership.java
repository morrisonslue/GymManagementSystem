package org.keyin.memberships;

// Classes
public class Membership {
    private int id;
    private String membershipType;
    private String description;
    private double membershipPrice;
    private int userId;

    // Constructors
    public Membership(String membershipType, String description, double membershipPrice, int userId) {
        this.membershipType = membershipType;
        this.description = description;
        this.membershipPrice = membershipPrice;
        this.userId = userId;
    }

    public Membership(int id, String membershipType, String description, double membershipPrice, int userId) {
        this.id = id;
        this.membershipType = membershipType;
        this.description = description;
        this.membershipPrice = membershipPrice;
        this.userId = userId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public String getDescription() {
        return description;
    }

    public double getMembershipPrice() {
        return membershipPrice;
    }

    public int getUserId() {
        return userId;
    }

    // Setters
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMembershipPrice(double membershipPrice) {
        this.membershipPrice = membershipPrice;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
