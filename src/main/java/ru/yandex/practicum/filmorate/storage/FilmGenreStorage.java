package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface FilmGenreStorage {
    void createFilmGenres(long filmId, long genreId);

    void deleteFilmGenres(long filmId);

    Collection<Genre> findAllByFilmId(Long filmId);
}
