package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.factory.GenresServiceFactory;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenresService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("genres")
@RequiredArgsConstructor
public class GenresController {

    private final GenresServiceFactory genresServiceFactory;
    private final GenresService genresService;

    @Autowired
    public GenresController(GenresServiceFactory genresServiceFactory) {
        this.genresServiceFactory = genresServiceFactory;
        this.genresService = genresServiceFactory.getGenresService();
    }

    // список объектов содержащих жанр
    @GetMapping("")
    public List<Genre> getAll() {
        log.info("Список жанров.");
        return genresService.getAll();
    }

// объект содержащий жанр с идентификатором id
    @GetMapping("/{id}")
    public Genre getByID(@PathVariable("id") Long id) {
        log.info("Жанр с id = " + id);
        return genresService.getByID(id);
    }

}
