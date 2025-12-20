package controller;

import model.User;
import util.JsonHelper;

import java.util.List;

public class AuthController {

    public static User login(String username, String password) {
        List<User> users = JsonHelper.readUsers();

        for (User u : users) {
            if (u.getUsername().equals(username) &&
                    u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }
}

