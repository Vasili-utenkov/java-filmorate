package ru.yandex.practicum.filmorate.service.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
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
        return filmStorage.getAll();
    }

    //  добавление фильма;
    @Override
    public Film addFilm(Film film) {
        // Если mpaID указан - связываем с MPA
        if (film.getMpaID() != null) {
            MPA mpa = mpaStorage.getByID(film.getMpaID().getId());
            // ++ Дополнительная обработка при необходимости
        }

        Film finalFilm = filmStorage.create(film);
        // Обработка жанров
        if (film.hasGenres()) {
            film.getGenres().stream()
                    .filter(Objects::nonNull) // Фильтрация null жанров
                    .forEach(genre -> {
                        Genre existingGenre = genresStorage.getByID(genre.getId());
                        if (existingGenre != null) {
                            filmGenreStorage.createFilmGenres(finalFilm.getId(), existingGenre.getId());
                        }
                    });
        }

        if (filmGenreStorage.findAllByFilmId(finalFilm.getId()) != null) {
            Set<Genre> genres = new LinkedHashSet<>(filmGenreStorage.findAllByFilmId(finalFilm.getId()));  // Сохраняем порядок
            finalFilm.setGenres(genres);
        }

        return finalFilm;
    }

    //    обновление фильма;
    @Override
    public Film updateFilm(Film newFilm) {

        // Проверяем существование фильма
        filmStorage.checkNullId(newFilm.getId());

        if (newFilm.getMpaID() != null) {
            MPA mpa = mpaStorage.getByID(newFilm.getMpaID().getId());
            // ++ Дополнительная обработка при необходимости
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
}
