package repositories;

import models.Note;

import java.time.LocalDate;
import java.util.List;

public interface NoteRepository {

    void save(Note note);
    void update(Note note);
    List<Note> findByDate(LocalDate dateCreate);
    List<Note> findBySubstring(String substring);
    void delete(Note note);

}
