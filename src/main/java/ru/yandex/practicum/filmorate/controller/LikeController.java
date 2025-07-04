package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.LikeService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class LikeController {

    private LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    // Поставить лайк фильму
    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Поставить лайк пользователя " + userId + " фильму  с id = " + id);
        likeService.addLike(id, userId);
    }

    // Удалить лайк фильма
    @DeleteMapping("/{id}/like/{userId}")
    public void unlikeFilm(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Удалить лайк пользователя " + userId + " у фильма с id = " + id);
        likeService.removeLike(id, userId);
    }

    // Вернуть фильмы по популярности
    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false, defaultValue = "10") Integer count) {
        log.info("Вернуть список из " + count + " фильмов по убыванию популярности");
        return likeService.getTopPopularFilms(count);
    }
}
