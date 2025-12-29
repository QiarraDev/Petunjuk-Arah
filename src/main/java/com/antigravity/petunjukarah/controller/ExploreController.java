package com.antigravity.petunjukarah.controller;

import com.antigravity.petunjukarah.entity.Destination;
import com.antigravity.petunjukarah.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ExploreController {

    @Autowired
    private DestinationRepository destinationRepository;

    @GetMapping("/explore")
    public String explore(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String category,
            Model model) {

        List<Destination> destinations;

        if (q != null && !q.isEmpty()) {
            destinations = destinationRepository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrLocationContainingIgnoreCase(q, q, q);
        } else if (category != null && !category.isEmpty() && !category.equalsIgnoreCase("Semua")) {
            destinations = destinationRepository.findByCategoryContainingIgnoreCase(category);
        } else {
            destinations = destinationRepository.findAll();
        }

        model.addAttribute("destinations", destinations);
        model.addAttribute("searchQuery", q);
        model.addAttribute("activeCategory", category != null ? category : "Semua");
        
        return "explore";
    }
}
