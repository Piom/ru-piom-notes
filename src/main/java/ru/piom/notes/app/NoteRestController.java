package ru.piom.notes.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.piom.notes.entities.Note;
import ru.piom.notes.repository.AccountRepository;
import ru.piom.notes.repository.NoteRepository;

import java.util.Collection;

/**
 * Created by Alex on 3/6/2016.
 */
@RestController
@RequestMapping("/{userId}/notes")
class NoteRestController {

    private final NoteRepository noteRepository;

    private final AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Note input) {
        this.validateUser(userId);
        return this.accountRepository
                .findByUsername(userId)
                .map(account -> {
                    Note result = noteRepository.save(new Note(account,
                            input.content));

                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setLocation(ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri());
                    return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
                }).get();

    }

    @RequestMapping(value = "/{noteId}", method = RequestMethod.GET)
    Note readNote(@PathVariable String userId, @PathVariable Long noteId) {
        this.validateUser(userId);
        return this.noteRepository.findOne(noteId);
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Note> readNotes(@PathVariable String userId) {
        this.validateUser(userId);
        return this.noteRepository.findByAccountUsername(userId);
    }

    @Autowired
    NoteRestController(NoteRepository bookmarkRepository,
                       AccountRepository accountRepository) {
        this.noteRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    private void validateUser(String userId) {
        this.accountRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }
}

