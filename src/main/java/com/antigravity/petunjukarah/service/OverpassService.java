package com.antigravity.petunjukarah.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Map;

@Service
public class OverpassService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String OVERPASS_API = "https://overpass-api.de/api/interpreter";

    public String searchNearby(double lat, double lon, String type) {
        String query = buildQuery(lat, lon, type);
        String url = UriComponentsBuilder.fromHttpUrl(OVERPASS_API)
                .queryParam("data", query)
                .toUriString();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"elements\": []}"; // Return empty JSON on error
        }
    }

    private String buildQuery(double lat, double lon, String type) {
        int radius = 2000;
        String nodeFilter = "";

        switch (type) {
            case "shop":
                nodeFilter = "node[\"shop\"~\"supermarket|convenience\"]";
                break;
            case "fuel":
                nodeFilter = "node[\"amenity\"=\"fuel\"]";
                break;
            case "market":
                nodeFilter = "node[\"amenity\"=\"marketplace\"]";
                break;
            case "police":
                nodeFilter = "node[\"amenity\"=\"police\"]";
                break;
            default:
                nodeFilter = "node[\"amenity\"]";
        }

        return String.format("[out:json];%s(around:%d,%f,%f);out;", nodeFilter, radius, lat, lon);
    }
}
