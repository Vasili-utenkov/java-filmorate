package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mappers.repository.BaseRepository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import java.util.Collection;
import java.util.List;

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

    private static final String GET_TOP_POPULAR_FILMS_QUERY = """
            SELECT f.id, f.name, f.description, f.releaseDate, f.duration, f.mpaID, COUNT(l.userid) AS likes_count
            FROM film f LEFT JOIN likes l ON f.id = l.filmid
            GROUP BY
                f.id
            ORDER BY
                likes_count DESC
            LIMIT ?
            """;

@Autowired
    public FilmDBStorage(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    // создание фильма
    @Override
    public Film create(Film film) {
        log.info("Запрос добавления фильма " + film);

        long id = insert(
                CREATE_QUERY,
                true,
                film.getName(),
                film.getDescription(),
                java.sql.Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpaID() != null ? film.getMpaID().getId() : null
        );
        film.setId(id);
        log.info("Добавили фильм " + film);
        return film;
    }

    // Изменение фильма
    @Override
    public Film update(Film film) {
        log.info("Запрос изменения фильма " + film);

        update(
                UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                java.sql.Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpaID() != null ? film.getMpaID().getId() : null,
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

    // Получение списка ID самых популярных фильмов по количеству лайков
    public List<Film> getTopPopularFilms(Integer count) {
        return findMany(GET_TOP_POPULAR_FILMS_QUERY, count);
    }

    public void checkNullId(Long id) {
        if (this.getById(id) == null) {
            throw new NotFoundException(String.format("Фильм с id %d не найден", id));
        }
    }

}
