package org.keyin.user;

import org.mindrot.jbcrypt.BCrypt;
import java.util.List;

public class UserService {
    private UserDao userDao = new UserDao();

    // new user registration
    public boolean registerUser(String username, String password, String role, String email, String phoneNumber,
            String address) {
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

    // user login
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

    // get all users
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    // delete user
    public boolean deleteUserByUsername(String username) {
        return userDao.deleteUserByUsername(username);
    }
}
