package ru.piom.notes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexandr Korkin on 3/5/2016.
 */
@Entity
public class Account {

    @OneToMany(mappedBy = "account")
    private Set<Note> notes = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    private String password;
    private String username;

    public Account() { //jpa only
    }

    public Account(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
