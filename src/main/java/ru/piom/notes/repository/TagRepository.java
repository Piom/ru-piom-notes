package ru.piom.notes.repository;

import org.springframework.data.repository.CrudRepository;
import ru.piom.notes.entities.Tag;

/**
 * Created by Alex on 10.03.2016.
 */
public interface TagRepository extends CrudRepository<Tag, Long> {
}
