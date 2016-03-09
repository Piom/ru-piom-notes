package ru.piom.notes;

import org.springframework.web.multipart.MultipartFile;
import ru.piom.notes.entities.Note;

/**
 * Created by Alex on 09.03.2016.
 */
public interface StorageService {

    boolean saveFile(Note note, MultipartFile file);
}
