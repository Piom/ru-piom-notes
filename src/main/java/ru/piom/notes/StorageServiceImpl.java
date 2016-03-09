package ru.piom.notes;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.piom.notes.entities.Note;

/**
 * Created by Alex on 09.03.2016.
 */
@Service
public class StorageServiceImpl implements StorageService {
    @Override
    public boolean saveFile(Note note, MultipartFile file) {
        return false;
    }
}
