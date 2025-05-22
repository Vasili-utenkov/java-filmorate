package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikeService {
    // Добавление лайка фильму
    void addLike(Long filmId, Long userId);

    // Удаление лайка у фильма
    void removeLike(Long filmId, Long userId);

    // Получение самых популярных фильмов по количеству лайков
    List<Film> getTopPopularFilms(Integer count);

    // Добавление мапы лайков
    void addLikesEmptySet(Long filmId);

    // Удаление мапы лайков
    void deleteLikesSet(Long filmId);
}
