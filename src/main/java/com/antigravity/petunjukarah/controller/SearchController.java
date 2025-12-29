package com.antigravity.petunjukarah.controller;

import com.antigravity.petunjukarah.service.OverpassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SearchController {

    @Autowired
    private OverpassService overpassService;

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public String search(@RequestParam double lat, @RequestParam double lon, @RequestParam String type) {
        return overpassService.searchNearby(lat, lon, type);
    }
}
