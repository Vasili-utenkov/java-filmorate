package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Collection;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {


    @Autowired
    InMemoryFilmStorage storage;

    @Autowired
    private FilmService service;


    //    получение всех фильмов.
    @GetMapping
    public Collection<Film> getFilms() {
        return storage.getAll();
    }

    //    добавление фильма;
    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        return storage.create(film);
    }

    //    обновление фильма;
    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film newFilm) {
        return storage.update(newFilm);
    }

    // Поставить лайк фильму
    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@PathVariable Long id, @PathVariable Long userId) {
        service.addLike(id, userId);
    }

    // Удалить лайк фильма
/* 1
    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> unlikeFilm(@PathVariable Long id, @PathVariable Long userId) {
        service.removeLike(id, userId);
        return ResponseEntity.ok().build();
    }
*/

    @DeleteMapping("/{id}/like/{userId}")
    public void unlikeFilm(@PathVariable Long id, @PathVariable Long userId) {
        service.removeLike(id, userId);
    }

    // Вернуть фильмы по популярности
    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false, defaultValue = "10") Integer count) {
        return service.getTopPopularFilms(count);
    }

}
