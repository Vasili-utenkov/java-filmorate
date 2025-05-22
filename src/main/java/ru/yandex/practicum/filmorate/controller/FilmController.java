package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.factory.FilmServiceFactory;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;


import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmServiceFactory filmServiceFactory;
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmServiceFactory filmServiceFactory) {
        this.filmServiceFactory = filmServiceFactory;
        this.filmService = filmServiceFactory.getFilmService();
    }

    //  получение всех фильмов.
    @GetMapping
    public Collection<Film> getFilms() {
        log.info("Запрос списка фильмов");
        return filmService.getAllFilms();
    }

    //  Запрос фильма по коду id
    @GetMapping("/{id}")
    public Film getFilmByID(@PathVariable("id") Long id) {
        log.info("Запрос фильма с кодом " + id);
        Film film = filmService.getFilmByID(id);
        return film;
    }


    //  добавление фильма;
    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Добавление фильма");
        return filmService.addFilm(film);
    }

    //    обновление фильма;
    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film newFilm) {
        log.info("Обновление фильма");
        return filmService.updateFilm(newFilm);
    }

}
