package ru.piom.notes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

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

    public String title;

    public String body;

    @ManyToMany
    private List<Tag> tags;

    public Note() {
    }

    public Note(Account account, String content, String title) {
        this.title=title;
        this.account = account;
        this.body = content;
    }

    public Account getAccount() {
        return account;
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
