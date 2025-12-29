package com.antigravity.petunjukarah.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LocationController {

    @GetMapping("/map")
    public String map() {
        return "map";
    }
}
