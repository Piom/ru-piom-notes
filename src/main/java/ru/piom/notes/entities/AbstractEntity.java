package ru.piom.notes.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by Alex on 12.03.2016.
 */
@MappedSuperclass
abstract class AbstractEntity {
    @Id
    @GeneratedValue
    public Long id;
}
