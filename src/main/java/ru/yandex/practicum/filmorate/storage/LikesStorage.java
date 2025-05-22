package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface LikesStorage {
    // Добавление мапы лайков (insert)
    void createLikesEmptySet(Long filmId);

    // Удаление мапы лайков
    void deleteLikesSet(Long filmId);

    // Добавление лайка фильму
    void addLike(Long filmId, Long userId);

    // Удаление лайка у фильма
    void removeLike(Long filmId, Long userId);

    // Получение самых популярных фильмов по количеству лайков
    List<Long> getTopPopularFilmsId(Integer count);
}
