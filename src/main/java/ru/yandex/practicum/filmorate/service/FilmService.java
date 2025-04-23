package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.LikesStorage;
import java.util.*;

@Service
public class FilmService {

    private InMemoryFilmStorage inMemoryFilmStorage;
    private LikesStorage likesStorage;

    public FilmService(InMemoryFilmStorage inMemoryFilmStorage, LikesStorage likesStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.likesStorage = likesStorage;
    }

    // получение всех фильмов.
    public Collection<Film> getAllFilms() {
        return inMemoryFilmStorage.getAll();
    }

    //  добавление фильма;
    public Film addFilm(Film film) {
        film = inMemoryFilmStorage.create(film);
        // Создали чистую мапу лайков
        likesStorage.createLikesEmptySet(film.getId());
        return film;
    }

    //    обновление фильма;
    public Film updateFilm(Film newFilm) {
        return inMemoryFilmStorage.update(newFilm);
    }

}
