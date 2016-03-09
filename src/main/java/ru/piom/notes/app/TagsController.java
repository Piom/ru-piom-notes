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
@RequestMapping("/{userId}/tags")
public class TagsController {
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private StorageService storageService;


    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity download(@PathVariable String userId){

    }


    private void validateUser(String userId) {
        accountRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }
}
