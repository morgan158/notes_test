import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Builder;
import models.Note;
import repositories.NoteRepository;
import repositories.NoteRepositoryJdbcImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static final String ORG_POSTGRESQL_DRIVER = "org.postgresql.Driver";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final int MAX_CLIENTS_COUNT = 10;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/notes";

    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();

        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        config.setDriverClassName(ORG_POSTGRESQL_DRIVER);
        config.setJdbcUrl(DB_URL);
        config.setMaximumPoolSize(20);
        HikariDataSource dataSource = new HikariDataSource(config);

        NoteRepository noteRepository = new NoteRepositoryJdbcImpl(dataSource);

//        Note note = Note.builder()
//                .title("Название")
//                .text("Текст заметки")
//                .date(LocalDate.now())
//                .build();

//        noteRepository.save(note);
//
//        note.setText("Обновленный текст заметки");
//
//        noteRepository.update(note);

//        Note noteForDelete = Note.builder()
//                .title("Заметка под удаление")
//                .text("Текст")
//                .date(LocalDate.now())
//                .build();
//
//        noteRepository.save(noteForDelete);
//
//        noteRepository.delete(8L);
//
//        List<Note> noteList = noteRepository.findByDate(LocalDate.of(2021, 12, 24));
//        for (Note note1 : noteList) {
//            System.out.println(note1);
//        }

//        List<Note> noteList1 = noteRepository.findBySubstring("Наз");
//        System.out.println(noteList1.get(0));
//
//        List<Note> noteList2 = noteRepository.findBySubstring("Зам");
//        System.out.println(noteList2.get(0));

    }

}
