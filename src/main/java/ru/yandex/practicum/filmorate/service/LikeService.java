package ru.yandex.practicum.filmorate.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikesStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class LikeService {

    private FilmStorage filmStorage;
    private LikesStorage likesStorage;
    private UserStorage userStorage;


    // Добавление лайка фильму
    public void addLike(Long filmId, Long userId) {
        checkFilmContains(filmId);
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
                .map(id -> filmStorage.getById(id))
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
