package com.hostel.hostel_management.factory;

import com.hostel.hostel_management.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

    public User createUser(String role, String username, String encodedPassword,
                           String fullName, String email, String phone) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        switch (role.toUpperCase()) {
            case "STUDENT" -> user.setRole(User.Role.STUDENT);
            case "WARDEN"  -> user.setRole(User.Role.WARDEN);
            case "ADMIN"   -> user.setRole(User.Role.ADMIN);
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        }
        return user;
    }
}