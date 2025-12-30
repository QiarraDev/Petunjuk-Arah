package com.antigravity.petunjukarah.controller;

import com.antigravity.petunjukarah.entity.Destination;
import com.antigravity.petunjukarah.entity.Note;
import com.antigravity.petunjukarah.entity.Trip;
import com.antigravity.petunjukarah.repository.DestinationRepository;
import com.antigravity.petunjukarah.repository.NoteRepository;
import com.antigravity.petunjukarah.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private TripRepository tripRepository;

    @GetMapping("/destinations")
    public List<Destination> getDestinations() {
        return destinationRepository.findAll();
    }

    @GetMapping("/destinations/search")
    public List<Destination> searchDestinations(@RequestParam String query) {
        return destinationRepository
                .findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrLocationContainingIgnoreCase(query,
                        query, query);
    }

    @GetMapping("/notes")
    public List<Note> getNotes() {
        return noteRepository.findAllByOrderByCreatedAtDesc();
    }

    @GetMapping("/trips")
    public List<Trip> getTrips() {
        return tripRepository.findAllByOrderByCreatedAtDesc();
    }

    @PutMapping("/notes/{id}")
    public Note updateNote(@PathVariable Long id, @RequestBody Note noteDetails) {
        return noteRepository.findById(id).map(note -> {
            note.setTitle(noteDetails.getTitle());
            note.setContent(noteDetails.getContent());
            return noteRepository.save(note);
        }).orElseThrow(() -> new RuntimeException("Note not found with id " + id));
    }
}
