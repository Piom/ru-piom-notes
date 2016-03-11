package ru.piom.notes.repository;

import org.springframework.data.repository.CrudRepository;
import ru.piom.notes.entities.Account;
import ru.piom.notes.entities.Note;
import ru.piom.notes.entities.Tag;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Alex on 10.03.2016.
 */
public interface TagRepository extends CrudRepository<Tag, Long> {
    Optional<Tag> findByName(String tagName);
}
