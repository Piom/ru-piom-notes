package ru.piom.notes.entities;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by Alex on 10.03.2016.
 */
@Entity
@Audited
public class Tag extends AbstractEntity{

    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Note> notes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
