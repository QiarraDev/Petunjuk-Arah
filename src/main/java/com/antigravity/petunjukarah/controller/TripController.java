package com.antigravity.petunjukarah.controller;

import com.antigravity.petunjukarah.entity.Trip;
import com.antigravity.petunjukarah.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TripController {

    @Autowired
    private TripRepository tripRepository;

    @GetMapping("/trip-planner")
    public String tripPlanner(Model model) {
        List<Trip> trips = tripRepository.findAllByOrderByCreatedAtDesc();
        model.addAttribute("trips", trips);
        model.addAttribute("newTrip", new Trip());
        return "trip-planner";
    }

    @PostMapping("/trip/save")
    public String saveTrip(@ModelAttribute Trip trip) {
        tripRepository.save(trip);
        return "redirect:/trip-planner";
    }
}
