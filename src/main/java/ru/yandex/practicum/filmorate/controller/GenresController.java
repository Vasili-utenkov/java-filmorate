package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.factory.FilmServiceFactory;
import ru.yandex.practicum.filmorate.controller.factory.GenresServiceFactory;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.GenresService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("genres")
@RequiredArgsConstructor
public class GenresController {

    private final GenresServiceFactory genresServiceFactory;
    private GenresService genresService = genresServiceFactory.getGenresService();

// список объектов содержащих жанр
// GET /genres

    @GetMapping("")
    public List<Genre> getAll() {
        log.info("Список жанров.");
        return genresService.getAll();
    }

// объект содержащий жанр с идентификатором id
// GET /genres/{id}

    @GetMapping("/{id}")
    public Genre getByID(@PathVariable("id") Long Id) {
        log.info("Жанр с id = " + Id);
        return genresService.getByID(Id);
    }

}
