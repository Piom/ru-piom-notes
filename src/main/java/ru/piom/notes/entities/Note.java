package ru.piom.notes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alexandr Korkin on 3/5/2016.
 */
@Entity
@Audited
public class Note extends AbstractEntity {

    @JsonIgnore
    @ManyToOne
    @NotAudited
    private Account account;

    @NotBlank
    public String title;

    public String body;

    @ManyToMany
    private List<Tag> tags;

    public Note() {
    }

    public Note(Account account, String title, String content, List<Tag> tags) {
        this.title = title;
        this.account = account;
        this.body = content;
        this.tags = tags;
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
