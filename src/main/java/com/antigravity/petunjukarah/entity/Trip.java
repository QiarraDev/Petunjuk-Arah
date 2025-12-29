package com.antigravity.petunjukarah.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDate startDate;

    private Integer duration; // in days

    private String mainDestination;
    
    private LocalTime reminderTime; // Alarm/Reminder time

    private LocalDateTime createdAt;

    public Trip() {
        this.createdAt = LocalDateTime.now();
    }

    public Trip(String name, LocalDate startDate, Integer duration, String mainDestination) {
        this();
        this.name = name;
        this.startDate = startDate;
        this.duration = duration;
        this.mainDestination = mainDestination;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public String getMainDestination() { return mainDestination; }
    public void setMainDestination(String mainDestination) { this.mainDestination = mainDestination; }

    public LocalTime getReminderTime() { return reminderTime; }
    public void setReminderTime(LocalTime reminderTime) { this.reminderTime = reminderTime; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
