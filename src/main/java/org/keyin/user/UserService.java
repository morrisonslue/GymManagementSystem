package org.keyin.user;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class UserService {
    private UserDao userDao = new UserDao();

    public boolean registerUser(String username, String password, String role, String email, String phone,
            String address) {
        try {
            String hash = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = new User(username, hash, role, email, phone, address);
            userDao.saveUser(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User loginUser(String username, String password) {
        try {
            User user = userDao.getUserByUsername(username);
            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                return user;
            }
        } catch (Exception e) {
            System.out.println("Login failed.");
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public boolean deleteUserByUsername(String username) {
        return userDao.deleteUserByUsername(username);
    }
}
