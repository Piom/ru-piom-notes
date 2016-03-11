package ru.piom.notes.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Collections;
import java.util.List;

/**
 * Created by Alex on 13.03.2016.
 */
public class NoteInput {

    @NotBlank
    private final String title;

    private final String body;

    private final List<String> tags;

    @JsonCreator
    public NoteInput(@JsonProperty("title") String title,
                     @JsonProperty("body") String body, @JsonProperty("tags") List<String> tags) {
        this.title = title;
        this.body = body;
        this.tags = tags == null ? Collections.emptyList() : tags;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    @JsonProperty("tags")
    public List<String> getTags() {
        return this.tags;
    }
}
