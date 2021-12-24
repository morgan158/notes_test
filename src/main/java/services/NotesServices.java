package services;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import models.Note;
import repositories.NoteRepository;
import repositories.NoteRepositoryJdbcImpl;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class NotesServices {
    public static final String ORG_POSTGRESQL_DRIVER = "org.postgresql.Driver";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/notes";

    private final NoteRepository noteRepository;

    public NotesServices () {
        HikariConfig config = new HikariConfig();
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        config.setDriverClassName(ORG_POSTGRESQL_DRIVER);
        config.setJdbcUrl(DB_URL);
        config.setMaximumPoolSize(20);
        HikariDataSource dataSource = new HikariDataSource(config);
        noteRepository = new NoteRepositoryJdbcImpl(dataSource);
    }

    public void toCreateNote() {
        System.out.println("Введите название заметки и текст на следующей строке");

        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();

        System.out.println("Введите текст");
        String text = scanner.nextLine();

        Note note = Note.builder()
                .title(title)
                .text(text)
                .date(LocalDate.now())
                .build();

        noteRepository.save(note);
    }

    public void toUpdateNote() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер заметки");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Введите текст");
        String text = scanner.nextLine();
        scanner.close();

        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            note.setText(text);
            noteRepository.update(note);
        }
    }

    public void toDeleteNote() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер заметки, которую хотите удалить");
        Long id = scanner.nextLong();
        scanner.close();

        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            noteRepository.delete(id);
        }
    }

    public void toFindNoteBySubstring() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите строку");
        String substring = scanner.nextLine().trim();
        scanner.close();
        List<Note> noteList = noteRepository.findBySubstring(substring);
        for (Note note : noteList) {
            System.out.println(note);
        }
    }

    public void toFindNoteByDate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите дату в формате 'год месяц число' (например, '2021 12 24'");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
        LocalDate localDate = LocalDate.parse(scanner.nextLine().trim(), formatter);
        scanner.close();
        List<Note> noteList = noteRepository.findByDate(localDate);
        for (Note note : noteList) {
            System.out.println(note);
        }
    }


}
