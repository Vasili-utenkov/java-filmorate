package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mappers.repository.BaseRepository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;

import java.util.Collection;

@Component
public class FilmGenreDBStorage extends BaseRepository<Genre> implements FilmGenreStorage {

    private static final String CREATE_FILM_GENRES_QUERY = "INSERT INTO FilmGenre (filmID, genreID) VALUES (?, ?)";
    private static final String DELETE_FILM_GENRES_QUERY = "DELETE FROM Genres WHERE filmID = ?";
    private static final String GET_GENRES_BY_FILM_ID_QUERY = """
            SELECT g.id, g.name
            FROM genres g
            INNER JOIN FilmGenre fg ON g.id = fg.genreID WHERE fg.filmID = ?
            ORDER BY g.id
            """;

    @Autowired
    public FilmGenreDBStorage(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public void createFilmGenres(long filmId, long genreId) {
        insert(CREATE_FILM_GENRES_QUERY, false, filmId, genreId);
    }

    @Override
    public void deleteFilmGenres(long filmId) {
        delete(DELETE_FILM_GENRES_QUERY, filmId);
    }

    @Override
    public Collection<Genre> findAllByFilmId(Long filmId) {
        return findMany(GET_GENRES_BY_FILM_ID_QUERY, filmId);
    }

}
