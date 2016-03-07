package ru.piom.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;
import ru.piom.notes.entities.Note;

import java.util.Collection;

/**
 * Created by Alexandr Korkin on 3/5/2016.
 */
public interface NoteRepository extends JpaRepository<Note, Long> {
    Collection<Note> findByAccountUsername(String username);
}
