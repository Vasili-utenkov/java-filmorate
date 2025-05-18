package ru.yandex.practicum.filmorate.service.inmemory;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.LikeService;
import ru.yandex.practicum.filmorate.storage.inmemory.FilmIMStorage;
import ru.yandex.practicum.filmorate.storage.inmemory.LikesIMStorage;
import ru.yandex.practicum.filmorate.storage.inmemory.UserIMStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service("likeIMService")
@ConditionalOnProperty(name = "film.storage.type", havingValue = "memory", matchIfMissing = true)
public class LikeIMService implements LikeService {

    private FilmIMStorage filmStorage;
    private LikesIMStorage likesStorage;
    private UserIMStorage userStorage;

    public LikeIMService(FilmIMStorage filmStorage, LikesIMStorage likesIMStorage, UserIMStorage userStorage) {
        this.filmStorage = filmStorage;
        this.likesStorage = likesIMStorage;
        this.userStorage = userStorage;
    }

    // Добавление лайка фильму
    @Override
    public void addLike(Long filmId, Long userId) {
        checkFilmContains(filmId);
        checkUserContains(userId);
        likesStorage.addLike(filmId, userId);
    }

    // Удаление лайка у фильма
    @Override
    public void removeLike(Long filmId, Long userId) {
        checkFilmContains(filmId);
        checkUserContains(userId);
        likesStorage.removeLike(filmId, userId);
    }

    // Получение самых популярных фильмов по количеству лайков
    @Override
    public List<Film> getTopPopularFilms(Integer count) {

        return  likesStorage.getTopPopularFilmsId(count).stream()
                .map(id -> filmStorage.getById(id))
                .collect(Collectors.toList());

    }

    // Добавление мапы лайков
    @Override
    public void addLikesEmptySet(Long filmId) {
        likesStorage.createLikesEmptySet(filmId);
    }

    // Удаление мапы лайков
    @Override
    public void deleteLikesSet(Long filmId) {
        likesStorage.deleteLikesSet(filmId);
    }

    private void checkFilmContains(Long filmId) {
        if (filmStorage.getById(filmId) == null) {
            throw new NotFoundException("Переданный фильм не найден id = " + filmId);
        }
    }

    private void checkUserContains(Long userId) {
        if (userStorage.getById(userId) == null) {
            throw new NotFoundException("Переданный пользователь не найден id = " + userId);
        }
    }


}
