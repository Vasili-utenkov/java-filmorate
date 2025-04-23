package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LikesStorage {
    private final Map<Long, Set<Long>> filmLikes = new HashMap<>(); // ключ - id фильма, значение - множество id пользователей, которые лайкнули фильм

    // Добавление мапы лайков (insert)
    public void createLikesEmptySet(Long filmId) {
        filmLikes.put(filmId, new HashSet<>());
    }

    // Удаление мапы лайков
    public void deleteLikesSet(Long filmId) {
        filmLikes.remove(filmId);
    }

    // Добавление лайка фильму
    public void addLike(Long filmId, Long userId) {
        Set<Long> likes = filmLikes.get(filmId);

        if (likes.contains(userId)) {
            throw new IllegalArgumentException("Пользователь уже поставил лайк этому фильму");
        }
        likes.add(userId);
    }

    // Удаление лайка у фильма
    public void removeLike(Long filmId, Long userId) {
        Set<Long> likes = filmLikes.get(filmId);
        if (likes == null || !likes.contains(userId)) {
            throw new NotFoundException("Лайк от пользователя " + userId + " не найден у фильма " + filmId);
        }
        likes.remove(userId);
    }

    // Получение самых популярных фильмов по количеству лайков
    // переделать на коды. фильмы подставить наверху
    public List<Long> getTopPopularFilmsId(Integer count) {
        List<Long> collect = filmLikes.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size())) // сортировка по убыванию количества лайков
                .limit(count)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return collect;
    }



}
