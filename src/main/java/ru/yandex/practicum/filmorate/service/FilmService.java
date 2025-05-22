package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {

    // получение всех фильмов.
    Collection<Film> getAllFilms();

    //  добавление фильма;
    Film addFilm(Film film);

    //    обновление фильма;
    Film updateFilm(Film newFilm);

    Film getFilmByID(Long id);

}
