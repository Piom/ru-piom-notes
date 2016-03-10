package ru.piom.notes.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.piom.notes.StorageService;
import ru.piom.notes.repository.AccountRepository;
import ru.piom.notes.repository.NoteRepository;

/**
 * Created by Alex on 09.03.2016.
 */
@RestController
@RequestMapping("/tags")
public class StorageController {
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private StorageService storageService;


    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity download(@PathVariable String userId){
        return ResponseEntity.ok().build();
    }


    private void validateUser(String userId) {
        accountRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }
}
