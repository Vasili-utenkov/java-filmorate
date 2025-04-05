package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Long, Film> films = new HashMap<>();

    //    получение всех фильмов.
    @GetMapping
    public Collection<Film> getFilms() {
        log.info("Запрос списка фильмов");
        return films.values();
    }

    //    добавление фильма;
    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        film.setId(getNextID());
        log.info("Добавили фильм" + film);
        films.put(film.getId(), film);
        return film;
    }

    private long getNextID() {
        long l = films.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++l;
    }

    //    обновление фильма;
    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film newFilm) {
        Long id = newFilm.getId();
        if (films.containsKey(id)) {
            Film oldFilm = films.get(id);
            films.replace(id, newFilm);
            log.info("Изменили данные по фильму" + oldFilm);
            return newFilm;
        }

        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
    }

}
