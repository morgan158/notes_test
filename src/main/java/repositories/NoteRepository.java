package repositories;

import models.Note;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NoteRepository {

    void save(Note note);
    void update(Note note);
    List<Note> findByDate(LocalDate dateCreate);
    List<Note> findBySubstring(String substring);
    void delete(Long id);

    Optional<Note> findById(Long id);
}
