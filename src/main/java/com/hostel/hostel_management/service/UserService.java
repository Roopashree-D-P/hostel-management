package com.hostel.hostel_management.service;

import com.hostel.hostel_management.factory.UserFactory;
import com.hostel.hostel_management.model.User;
import com.hostel.hostel_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserFactory userFactory;

    public User register(String role, String username, String rawPassword,
                         String fullName, String email, String phone) {
        if (userRepository.existsByUsername(username))
            throw new RuntimeException("Username already exists!");
        User user = userFactory.createUser(role, username,
                passwordEncoder.encode(rawPassword), fullName, email, phone);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() { return userRepository.findAll(); }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) { userRepository.deleteById(id); }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Not found: " + username));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}