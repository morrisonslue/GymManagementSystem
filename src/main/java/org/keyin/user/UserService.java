package org.keyin.user;

import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    private UserDao userDao = new UserDao();

    public boolean registerUser(String username, String password, String role) {
        try {
            String hashedPw = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = new User(0, username, hashedPw, role);
            userDao.saveUser(user);
            return true;
        } catch (Exception e) {
            System.out.println("Error registering user.");
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
            System.out.println("Login error.");
        }
        return null;
    }
}

