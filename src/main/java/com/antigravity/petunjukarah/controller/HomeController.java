package com.antigravity.petunjukarah.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class HomeController {

    @Autowired
    private com.antigravity.petunjukarah.repository.NoteRepository noteRepository;
    
    @Autowired
    private com.antigravity.petunjukarah.repository.TripRepository tripRepository;

    @GetMapping("/")
    public String home(org.springframework.ui.Model model) {
        // Fetch top 3 recent notes for the public dashboard
        model.addAttribute("recentNotes", noteRepository.findAllByOrderByCreatedAtDesc().stream().limit(3).collect(java.util.stream.Collectors.toList()));
        // Fetch trips for alarm checking
        model.addAttribute("trips", tripRepository.findAllByOrderByCreatedAtDesc());
        return "dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(org.springframework.ui.Model model) {
        return home(model);
    }

}
