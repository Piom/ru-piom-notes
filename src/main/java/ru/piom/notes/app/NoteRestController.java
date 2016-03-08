package ru.piom.notes.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Note input) {
        validateUser(userId);
        return accountRepository
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

    @RequestMapping(value = "/{noteId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateNote(@PathVariable String userId, @PathVariable Long noteId, @RequestBody Note input) {
        validateUser(userId);
        Note note = noteRepository.findOne(noteId);
        if (note == null) {
            new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
        note.content = input.getContent();
        noteRepository.save(note);
    }

    @RequestMapping(value = "/{noteId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteNote(@PathVariable String userId, @PathVariable Long noteId) {
        validateUser(userId);
        Note note = noteRepository.findOne(noteId);
        if (note == null) {
            new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
        noteRepository.delete(noteId);

    }

    @RequestMapping(value = "/{noteId}", method = RequestMethod.GET)
    Note readNote(@PathVariable String userId, @PathVariable Long noteId) {
        validateUser(userId);
        return noteRepository.findOne(noteId);
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Note> readNotes(@PathVariable String userId) {
        validateUser(userId);
        return noteRepository.findByAccountUsername(userId);
    }

    private void validateUser(String userId) {
        accountRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }
}

