package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikesStorage;
import java.util.*;

@Service
public class FilmService {

    private FilmStorage filmStorage;
    private LikesStorage likesStorage;

    public FilmService(FilmStorage filmStorage, LikesStorage likesStorage) {
        this.filmStorage = filmStorage;
        this.likesStorage = likesStorage;
    }

    // получение всех фильмов.
    public Collection<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    //  добавление фильма;
    public Film addFilm(Film film) {
        film = filmStorage.create(film);
        // Создали чистую мапу лайков
        likesStorage.createLikesEmptySet(film.getId());
        return film;
    }

    //    обновление фильма;
    public Film updateFilm(Film newFilm) {
        return filmStorage.update(newFilm);
    }

}
