package com.sunbaseassignment.controller;

import com.sunbaseassignment.exception.UsernameNotAvailableException;
import com.sunbaseassignment.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showLoginForm() {
        // Return the login form view
        return "login";
    }

    @GetMapping("/login")
    public String showLoginFormAgain() {
        // Return the login form view
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        if (userService.isValidUser(username, password)) {
            session.setAttribute("username", username); // Store username in the session
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            model.addAttribute("username", username);
            return "dashboard";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate the session to log out the user
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String showSignupForm() {
        // Return the signup form view
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String email,
                         @RequestParam String firstName,
                         @RequestParam String lastName, HttpSession session) {
        try {
            userService.registerUser(username, password, email, firstName, lastName);
            return "redirect:/login?signup=success";
        } catch (UsernameNotAvailableException e) {
            // Handle the case where the username is not available
            return "redirect:/signup?error=username-not-available";
        }
    }
}

