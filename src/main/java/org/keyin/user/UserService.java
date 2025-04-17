package org.keyin.user;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class UserService {
    private UserDao userDao = new UserDao();

    // Register new user with hashing and details
    public boolean registerUser(String username, String password, String role, String email, String phoneNumber, String address) {
        try {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = new User(username, hashedPassword, role, email, phoneNumber, address);
            userDao.saveUser(user);
            return true;
        } catch (Exception e) {
            System.out.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    // Login user by checking credentials
    public User loginUser(String username, String password) {
        try {
            User user = userDao.getUserByUsername(username);
            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                return user;
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }

    // Admin: fetch all users
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    // Admin: delete a user by their username
    public boolean deleteUserByUsername(String username) {
        return userDao.deleteUserByUsername(username);
    }
}

