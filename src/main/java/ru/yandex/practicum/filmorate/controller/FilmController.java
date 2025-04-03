package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Long, Film> films = new HashMap<>();

    public static void validate(Film film) throws ValidationException {

//        название не может быть пустым;
        if (film.getName().isEmpty() || film.getName() == null || film.getName().length() == 0) {
            throw new ValidationException("Film: название не может быть пустым");
        }

//        максимальная длина описания — 200 символов;
        if (film.getDescription().isEmpty() || film.getDescription() == null) {
            throw new ValidationException("Film: описание не может быть пустым");
        }

        if (film.getDescription().length() < 1 || film.getDescription().length() > 200) {
            throw new ValidationException("Film: максимальная длина описания — 200 символов");
        }

//        дата релиза — не раньше 28 декабря 1895 года;
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Film: дата релиза — не раньше 28 декабря 1895 года;");
        }

//        продолжительность фильма должна быть положительным числом.
        if (film.getDuration() < 1) {
            throw new ValidationException("Film: продолжительность фильма должна быть положительным числом");
        }

    }

    //    получение всех фильмов.
    @GetMapping
    public Collection<Film> getFilms() {
        return films.values();
    }

    //    добавление фильма;
    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        film.setId(getNextID());
        films.put(film.getId(), film);
        try {
            validate(film);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
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
        try {
            validate(newFilm);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());
            return oldFilm;
        }

        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
    }

}
