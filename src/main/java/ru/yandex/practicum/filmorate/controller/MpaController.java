package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final MpaService mpaService;

    @Autowired
    public MpaController(MpaServiceFactory mpaServiceFactory) {
        this.mpaServiceFactory = mpaServiceFactory;
        this.mpaService = mpaServiceFactory.getMPAService();
    }

    // список объектов содержащих жанр
    @GetMapping("")
    public List<MPA> getAll() {
        log.info("Список жанров.");
        return mpaService.getAll();
    }

// объект содержащий жанр с идентификатором id
    @GetMapping("/{id}")
    public MPA getByID(@PathVariable("id") Long id) {
        log.info("Жанр с id = " + id);
        return mpaService.getByID(id);
    }

}
