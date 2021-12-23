package repositories;

import javafx.stage.Window;
import models.Note;


import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class NoteRepositoryJdbcImpl implements NoteRepository {

    //language=SQL
    private static final String SQL_INSERT = "insert into note(title, date, text) values (?, ? , ?)";

    //language=SQL
    private static final String SQL_UPDATE = "update note set title = ?, text = ?";

    //language=SQL
    private static final String SQL_DELETE = "delete from note where id = ?";

    //language=SQL
    private static final String SQL_SELECT_BY_DATE = "select * from note where date = ?";

    //language=SQL
    private static final String SQL_SELECT_BY_SUBSTRING = "select * from note where text like ? or title like ?";

    private final DataSource dataSource;

    public NoteRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final Function<ResultSet, Note> productMapper = resultSet -> {
        try {
            Long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            String text = resultSet.getString("text");
            Date date = resultSet.getDate("date");
            return new Note(id, title, date.toLocalDate(), text);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    @Override
    public void save(Note note) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, note.getTitle());
            Date date = Date.valueOf(note.getDate());
            statement.setDate(2, date);
            statement.setString(3, note.getText());
            int affectedRow = statement.executeUpdate();

            if (affectedRow != 1) {
                throw new SQLException("Can`t insert note");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                note.setId(generatedKeys.getLong("id"));
            } else {
                throw new SQLException("Can`t get id");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public void update(Note note) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, note.getTitle());
            statement.setString(2, note.getText());
            int affectedRow = statement.executeUpdate();

            if (affectedRow != 1) {
                throw new SQLException("Can`t update note");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public void delete(Note note) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setLong(1, note.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public List<Note> findByDate(LocalDate dateCreate) {
        List<Note> notes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_DATE)) {
            Date date = Date.valueOf(dateCreate);
            statement.setDate(1, date);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    notes.add(productMapper.apply(resultSet));
                }
                return notes;
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public List<Note> findBySubstring(String substring) {
        List<Note> notes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_SUBSTRING)) {
            statement.setString(1, substring);
            statement.setString(2, substring);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    notes.add(productMapper.apply(resultSet));
                }
                return notes;
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
