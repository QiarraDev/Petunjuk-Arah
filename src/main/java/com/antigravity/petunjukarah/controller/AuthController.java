package com.antigravity.petunjukarah.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
    
    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password) {
        // Simple mock login logic
        if ("admin".equals(username) && "admin".equals(password)) {
            return "redirect:/dashboard";
        }
        return "redirect:/login?error=true";
    }
    
    @PostMapping("/signup")
    public String handleSignup() {
        // Redirect to login after mock signup
        return "redirect:/login";
    }
}
