package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mappers.repository.BaseRepository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import java.util.Collection;

@Slf4j
@Component
public class FilmDBStorage extends BaseRepository<Film> implements FilmStorage {

    private static final String CREATE_QUERY =
            "INSERT INTO Film(name, description, releaseDate, duration, mpaID) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY =
            "UPDATE Film SET name = ?, description = ?, releaseDate = ?,duration = ?, mpaID = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE Film WHERE id = ?";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM Film WHERE id = ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM Film";

    public FilmDBStorage(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    // создание фильма
    @Override
    public Film create(Film film) {
        long id = insert(
                CREATE_QUERY,
                true,
                film.getName(),
                film.getDescription(),
                java.sql.Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpaID()
        );
        film.setId(id);
        log.info("Добавили фильм " + film);
        return film;
    }

    // Изменение фильма
    @Override
    public Film update(Film film) {
        update(
                UPDATE_QUERY,
                true,
                film.getName(),
                film.getDescription(),
                java.sql.Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpaID(),
                film.getId()
        );
        log.info("Изменили данные по фильму " + film);
        return film;
    }

    // Удаление фильма
    @Override
    public void delete(long id) {
        log.info("Удалили фильм с id = " + id);
        delete(DELETE_QUERY, id);
    }

    // Получение фильма по коду id
    @Override
    public Film getById(long id) {
        log.info("Получение фильма с id = " + id);
        return findOne(GET_BY_ID_QUERY, id);
    }

    // Получение списка фильмов
    @Override
    public Collection<Film> getAll() {
        log.info("Запрос списка фильмов");
        return findMany(GET_ALL_QUERY);
    }
}
