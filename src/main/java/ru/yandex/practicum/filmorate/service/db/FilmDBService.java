package ru.yandex.practicum.filmorate.service.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.db.*;

import java.util.*;

@Slf4j
@Service("filmDBService")
@ConditionalOnProperty(name = "film.storage.type", havingValue = "db")
public class FilmDBService implements FilmService {

    private final FilmDBStorage filmStorage;
    private final LikesDBStorage likesStorage;
    private final MpaDBStorage mpaStorage;
    private final FilmGenreDBStorage filmGenreStorage;
    private final GenresDBStorage genresStorage;


    public FilmDBService(FilmDBStorage filmStorage,
                         LikesDBStorage likesStorage,
                         MpaDBStorage mpaStorage,
                         FilmGenreDBStorage filmGenreStorage,
                         GenresDBStorage genresStorage) {
        this.filmStorage = filmStorage;
        this.likesStorage = likesStorage;
        this.mpaStorage = mpaStorage;
        this.filmGenreStorage = filmGenreStorage;
        this.genresStorage = genresStorage;
    }

    // получение всех фильмов.
    @Override
    public Collection<Film> getAllFilms() {
        Collection<Film> films = filmStorage.getAll();
        return films;
    }

    //  добавление фильма;
    @Override
    public Film addFilm(Film film) {
        MPA mpa;
        if (film.getMpa() != null) {
            mpa = mpaStorage.getByID(film.getMpa().getId());
            if (mpa == null) {
                throw new NotFoundException("mpa не наден");
            }
        } else {
            mpa = new MPA();
        }

        Film finalFilm = filmStorage.create(film);

        // Обработка жанров
        if (film.hasGenres()) {
            film.getGenres().stream()
                    .forEach(genre -> {
                        Genre existingGenre = genresStorage.getByID(genre.getId());
                        if (existingGenre != null) {
                            filmGenreStorage.createFilmGenres(finalFilm.getId(), existingGenre.getId());
                        } else {
                            throw new NotFoundException("Genres не наден");
                        }
                    });
        }

// Создаем Set (пустой, если жанров нет)
        Set<Genre> genres = new LinkedHashSet<>(filmGenreStorage.findAllByFilmId(finalFilm.getId()));
        finalFilm.setGenres(genres);
        finalFilm.setMpa(mpa);
        filmStorage.update(finalFilm);
        return finalFilm;
    }

    //    обновление фильма;
    @Override
    public Film updateFilm(Film newFilm) {

        // Проверяем существование фильма
        filmStorage.checkNullId(newFilm.getId());

        if (newFilm.getMpa() != null) {
            MPA mpa = mpaStorage.getByID(newFilm.getMpa().getId());
        }

        Film finalFilm = filmStorage.update(newFilm);
        if (newFilm.hasGenres()) {
            // удаляем FilmGenres
            filmGenreStorage.deleteFilmGenres(newFilm.getId());
            // добавляенм новый FilmGenres
            newFilm.getGenres().stream()
                    .map(genre -> genresStorage.getByID(genre.getId()))
                    .forEach(genre -> filmGenreStorage.createFilmGenres(finalFilm.getId(), genre.getId()));
        }
        finalFilm.setGenres(new HashSet<>(filmGenreStorage.findAllByFilmId(finalFilm.getId())));
        return finalFilm;

    }

    public Film getFilmByID(Long id) {
        return filmStorage.getById(id);
    }

}
