package ru.piom.notes.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.Revisions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.tags.form.OptionsTag;
import ru.piom.notes.entities.Note;
import ru.piom.notes.entities.NoteInput;
import ru.piom.notes.entities.NoteRevisionDto;
import ru.piom.notes.entities.Tag;
import ru.piom.notes.repository.AccountRepository;
import ru.piom.notes.repository.NoteRepository;
import ru.piom.notes.repository.TagRepository;

import java.util.*;

/**
 * Created by Alex on 3/6/2016.
 */
@RestController
@RequestMapping("/{userId}/notes")
class NoteController {

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TagRepository tagRepository;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> create(@PathVariable String userId, @RequestBody NoteInput input) {
        validateUser(userId);
        return accountRepository
                .findByUsername(userId)
                .map(account -> {
                    Note note = new Note(account, input.getBody(), input.getTitle(), getTags(input.getTags()));
                    note = noteRepository.save(note);
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setLocation(ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(note.getId()).toUri());
                    return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
                }).get();

    }

    private List<Tag> getTags(List<String> inputTagList) {
        List<Tag> tags = new ArrayList<>(inputTagList.size());
        for (String tagName : inputTagList) {
            Optional<Tag> tag = tagRepository.findByName(tagName);
            if (!tag.isPresent()) {
                throw new IllegalArgumentException("The tag '" + tag
                        + "' does not exist");
            }
            tags.add(tag.get());
        }
        return tags;
    }

    @RequestMapping(value = "/{noteId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateNote(@PathVariable String userId, @PathVariable Long noteId, @RequestBody NoteInput input) {
        validateUser(userId);
        Note note = noteRepository.findOne(noteId);
        if (note == null) {
            new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
        note.title = input.getTitle();
        note.body = input.getBody();
        note.setTags(getTags(input.getTags()));
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

    @RequestMapping(value = "/{noteId}/revisions", method = RequestMethod.GET)
    List<NoteRevisionDto> readNoteRevisions(@PathVariable String userId, @PathVariable Long noteId) {
        validateUser(userId);
        Revisions<Integer, Note> revisions = noteRepository.findRevisions(noteId);
        return NoteAssembler.toDto(revisions);
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

