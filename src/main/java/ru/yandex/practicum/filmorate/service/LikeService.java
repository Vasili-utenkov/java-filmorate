package ru.yandex.practicum.filmorate.service;


import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.LikesStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private InMemoryFilmStorage inMemoryFilmStorage;
    private LikesStorage likesStorage;
    private InMemoryUserStorage inMemoryUserStorage;

    public LikeService(InMemoryFilmStorage inMemoryFilmStorage, LikesStorage likesStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.likesStorage = likesStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    // Добавление лайка фильму
    public void addLike(Long filmId, Long userId) {
        checkFilmContains(filmId);
        checkUserContains(userId);
        likesStorage.addLike(filmId, userId);
    }

    // Удаление лайка у фильма
    public void removeLike(Long filmId, Long userId) {
        checkFilmContains(filmId);
        checkUserContains(userId);
        likesStorage.removeLike(filmId, userId);
    }

    // Получение самых популярных фильмов по количеству лайков
    public List<Film> getTopPopularFilms(Integer count) {

        return  likesStorage.getTopPopularFilmsId(count).stream()
                .map(id -> inMemoryFilmStorage.getById(id))
                .collect(Collectors.toList());

    }

    // Добавление мапы лайков
    public void addLikesEmptySet(Long filmId) {
        likesStorage.createLikesEmptySet(filmId);
    }

    // Удаление мапы лайков
    public void deleteLikesSet(Long filmId) {
        likesStorage.deleteLikesSet(filmId);
    }

    private void checkFilmContains(Long filmId) {
        if (inMemoryFilmStorage.getById(filmId) == null) {
            throw new NotFoundException("Переданный фильм не найден id = " + filmId);
        }
    }

    private void checkUserContains(Long userId) {
        if (inMemoryUserStorage.getById(userId) == null) {
            throw new NotFoundException("Переданный пользователь не найден id = " + userId);
        }
    }


}
