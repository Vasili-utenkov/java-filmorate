package ru.yandex.practicum.filmorate.service.db;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.db.FilmDBStorage;
import ru.yandex.practicum.filmorate.storage.db.LikesDBStorage;

import java.util.Collection;

@Service("filmDBService")
@ConditionalOnProperty(name = "film.storage.type", havingValue = "db")
public class FilmDBService implements FilmService {

    private FilmDBStorage filmStorage;
    private LikesDBStorage likesStorage;

    public FilmDBService(FilmDBStorage filmStorage, LikesDBStorage likesStorage) {
        this.filmStorage = filmStorage;
        this.likesStorage = likesStorage;
    }

    // получение всех фильмов.
    @Override
    public Collection<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    //  добавление фильма;
    @Override
    public Film addFilm(Film film) {
        film = filmStorage.create(film);
        // Создали чистую мапу лайков
        likesStorage.createLikesEmptySet(film.getId());
        return film;
    }

    //    обновление фильма;
    @Override
    public Film updateFilm(Film newFilm) {
        return filmStorage.update(newFilm);
    }
}
