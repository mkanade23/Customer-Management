package com.sunbaseassignment.service;

import com.sunbaseassignment.dao.UserRepository;
import com.sunbaseassignment.exception.UsernameNotAvailableException;
import com.sunbaseassignment.model.User;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class    UserService /*implements UserDetailsService*/ {

    @Autowired
    private UserRepository userRepository;

    public boolean isValidUser(String username, String password) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));

        return userOptional.isPresent() && passwordMatches(userOptional.get(), password);
    }

    private boolean passwordMatches(User user, String password) {
        // Implement your password verification logic (e.g., use BCryptPasswordEncoder)
        // For simplicity, a basic check is shown here
        return user.getPassword().equals(password);
    }


    public boolean isUsernameAvailable(String username) {
        return userRepository.findByUsername(username) == null;
    }

    public void registerUser(String username, String password, String email, String firstName, String lastName) {
        // Check if the username is available
        if (!isUsernameAvailable(username)) {
            throw new UsernameNotAvailableException("Username is not available");
        }

        // Create a new User entity
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // In a real-world scenario, I would hash the password
        newUser.setEmail(email);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);

        // Save the user to the database
        try {
            // Attempt to save the user
            userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            // Handle the case where a constraint violation occurred
            // For example, inform the user that the username is not available
            throw new UsernameNotAvailableException("Username is not available");
        }
    }
}
