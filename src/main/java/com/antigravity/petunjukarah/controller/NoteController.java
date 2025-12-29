package com.antigravity.petunjukarah.controller;

import com.antigravity.petunjukarah.entity.Note;
import com.antigravity.petunjukarah.entity.Folder;
import com.antigravity.petunjukarah.repository.NoteRepository;
import com.antigravity.petunjukarah.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private FolderRepository folderRepository;

    @GetMapping
    public String listNotes(@RequestParam(required = false) Long folderId, Model model) {
        List<Note> notes;
        if (folderId != null) {
            notes = noteRepository.findByFolderIdOrderByCreatedAtDesc(folderId);
            model.addAttribute("currentFolderId", folderId);
        } else {
            notes = noteRepository.findAllByOrderByCreatedAtDesc();
        }
        
        model.addAttribute("notes", notes);
        model.addAttribute("folders", folderRepository.findAll());
        model.addAttribute("newNote", new Note());
        return "notes";
    }

    @PostMapping("/add")
    public String saveNote(@ModelAttribute Note note, @RequestParam(required = false) Long folderId) {
        if (folderId != null) {
            folderRepository.findById(folderId).ifPresent(note::setFolder);
        }
        noteRepository.save(note);
        return "redirect:/notes" + (folderId != null ? "?folderId=" + folderId : "");
    }

    @PostMapping("/folder/add")
    public String addFolder(@RequestParam String name) {
        folderRepository.save(new Folder(name));
        return "redirect:/notes";
    }

    @PostMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        noteRepository.deleteById(id);
        return "redirect:/notes";
    }
}
