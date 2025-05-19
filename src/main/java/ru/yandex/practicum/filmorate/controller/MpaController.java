package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.factory.MpaServiceFactory;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("mpa")
@RequiredArgsConstructor
public class MpaController {

    private final MpaServiceFactory mpaServiceFactory;
    private MpaService mpaService = mpaServiceFactory.getMPAService();

// список объектов содержащих жанр
// GET /genres

    @GetMapping("")
    public List<MPA> getAll() {
        log.info("Список жанров.");
        return mpaService.getAll();
    }

// объект содержащий жанр с идентификатором id
// GET /genres/{id}

    @GetMapping("/{id}")
    public MPA getByID(@PathVariable("id") Long Id) {
        log.info("Жанр с id = " + Id);
        return mpaService.getByID(Id);
    }

}
