package com.antigravity.petunjukarah.repository;

import com.antigravity.petunjukarah.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    // Custom query methods usually go here, but for now we just need CRUD.
    // e.g., List<Note> findAllByOrderByCreatedAtDesc();
    List<Note> findAllByOrderByCreatedAtDesc();
    List<Note> findByFolderIdOrderByCreatedAtDesc(Long folderId);
}
