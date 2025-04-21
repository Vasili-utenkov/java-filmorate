package ru.yandex.practicum.filmorate.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final Map<Long, Set<Long>> filmLikes = new HashMap<>(); // ключ - id фильма, значение - множество id пользователей, которые лайкнули фильм
    private InMemoryFilmStorage storage;

    @Autowired
    public void setStorage(@Lazy InMemoryFilmStorage storage) {
        this.storage = storage;
    }

    // Добавление лайка фильму
    public void addLike(Long filmId, Long userId) {
// + проверки - неизвестный пользователь, неизвестный фильм
        checkFilmContains(filmId);
        Set<Long> likes = filmLikes.get(filmId);

        checkFilmContains(filmId);
        if (likes.contains(userId)) {
            throw new IllegalArgumentException("Пользователь уже поставил лайк этому фильму");
        }
        likes.add(userId);
    }

    // Удаление лайка у фильма
    public void removeLike(Long filmId, Long userId) {
        checkFilmContains(filmId);
        Set<Long> likes = filmLikes.get(filmId);
        if (likes == null || !likes.contains(userId)) {
            throw new NotFoundException("Лайк от пользователя " + userId + " не найден у фильма " + filmId);
        }
        likes.remove(userId);
    }

    // Получение самых популярных фильмов по количеству лайков
    public List<Film> getTopPopularFilms(Integer count) {
        return filmLikes.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size())) // сортировка по убыванию количества лайков
                .limit(count)
                .map(entry -> storage.getById(entry.getKey()))
                .collect(Collectors.toList());
    }

    // Добавление мапы лайков
    public void addLikesEmptySet(Long filmId) {
        filmLikes.put(filmId, new HashSet<>());
    }

    // Удаление мапы лайков
    public void deleteLikesSet(Long filmId) {
        filmLikes.remove(filmId);
    }


    private void checkFilmContains(Long filmId) {
        if (!filmLikes.containsKey(filmId)) {
            throw new NotFoundException("Переданный пользователь не найден id = " + filmId);
        }
    }


}
