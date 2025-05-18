package ru.yandex.practicum.filmorate.service.inmemory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.inmemory.FilmIMStorage;
import ru.yandex.practicum.filmorate.storage.inmemory.LikesIMStorage;
import java.util.*;

@Service("filmIMService")
@ConditionalOnProperty(name = "film.storage.type", havingValue = "memory", matchIfMissing = true)
public class FilmIMService implements FilmService {

    private FilmIMStorage filmStorage;
    private LikesIMStorage likesStorage;

    public FilmIMService(FilmIMStorage filmIMStorage, LikesIMStorage likesStorage) {
        this.filmStorage = filmIMStorage;
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
