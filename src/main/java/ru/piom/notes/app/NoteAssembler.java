package ru.piom.notes.app;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import ru.piom.notes.entities.Note;
import ru.piom.notes.entities.NoteRevisionDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 12.03.2016.
 */
public class NoteAssembler {

    public static List<NoteRevisionDto> toDto(Revisions<Integer, Note> revisions) {
        List<NoteRevisionDto> result = new ArrayList<>();
        for(Revision<Integer, Note> revision : revisions) {
            NoteRevisionDto rev = new NoteRevisionDto();
            rev.entity = revision.getEntity();
            rev.revisionDate = revision.getRevisionDate().toDate();
            rev.revisionNumber = revision.getRevisionNumber();
            result.add(rev);
        }
        return result;
    }
}
