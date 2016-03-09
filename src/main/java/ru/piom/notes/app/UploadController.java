package ru.piom.notes.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.piom.notes.StorageService;
import ru.piom.notes.entities.Note;
import ru.piom.notes.repository.AccountRepository;
import ru.piom.notes.repository.NoteRepository;

/**
 * Created by Alex on 09.03.2016.
 */
@RestController
@RequestMapping("/{userId}/notes/{noteId}")
public class UploadController {
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private StorageService storageService;


    @RequestMapping(value = "/upload/{fileId}", method = RequestMethod.GET)
    ResponseEntity download(@PathVariable String userId, @PathVariable Long noteId,@PathVariable Long fileId){
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = {"multipart/*"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void upload(@PathVariable String userId, @PathVariable Long noteId, @RequestParam("file") MultipartFile file) {
        validateUser(userId);
        Note note = noteRepository.findOne(noteId);
        if (note == null) {
            new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
        storageService.saveFile(note, file);
    }

    private void validateUser(String userId) {
        accountRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }
}
