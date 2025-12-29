package com.antigravity.petunjukarah.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String profile(Model model) {
        // Dummy profile data
        model.addAttribute("name", "Angga Adi Wibowo");
        model.addAttribute("role", "Premium User");
        model.addAttribute("email", "angga@example.com");
        return "profile";
    }
}
