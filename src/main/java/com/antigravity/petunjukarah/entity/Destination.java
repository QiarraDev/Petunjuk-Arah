package com.antigravity.petunjukarah.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "destinations")
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String category; // Wisata, Hotel, Kuliner, Fasilitas

    @Column(columnDefinition = "TEXT")
    private String description;

    private String location; // City/Regency

    private Double rating;
    
    private String price; // e.g. "Rp 50.000" or "Start from Rp 500k"

    private Double lat;
    private Double lon;

    private String imageUrl;

    private LocalDateTime createdAt;

    public Destination() {
        this.createdAt = LocalDateTime.now();
    }

    public Destination(String name, String category, String location, Double rating, String imageUrl) {
        this();
        this.name = name;
        this.category = category;
        this.location = location;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public Double getLat() { return lat; }
    public void setLat(Double lat) { this.lat = lat; }

    public Double getLon() { return lon; }
    public void setLon(Double lon) { this.lon = lon; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
