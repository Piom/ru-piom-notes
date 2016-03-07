package ru.piom.notes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Alexandr Korkin on 3/5/2016.
 */
@Entity
public class Note{

    @JsonIgnore
    @ManyToOne
    private Account account;

    @Id
    @GeneratedValue
    private Long id;

    public String content;

    public Note() {
    }

    public Note(Account account, String content) {
        this.account = account;
        this.content = content;
    }

    public Account getAccount() {
        return account;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
