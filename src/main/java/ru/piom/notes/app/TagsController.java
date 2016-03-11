package ru.piom.notes.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.piom.notes.StorageService;
import ru.piom.notes.entities.Note;
import ru.piom.notes.entities.Tag;
import ru.piom.notes.repository.AccountRepository;
import ru.piom.notes.repository.NoteRepository;
import ru.piom.notes.repository.TagRepository;

/**
 * Created by Alex on 09.03.2016.
 */
@RestController
@RequestMapping("/tags")
public class TagsController {
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TagRepository tagRepository;


    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity getAllTags() {
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody Tag input) {
        Tag tag = new Tag();
        tag.setName(input.getName());
        tagRepository.save(tag);
    }

    @RequestMapping(value = "/{tagId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable Long tagId, @RequestBody Tag input) {
        Tag tag = tagRepository.findOne(tagId);
        if (tag == null) {
            new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
        tag.setName(input.getName());
        tagRepository.save(tag);
    }


    private void validateUser(String userId) {
        accountRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }
}
