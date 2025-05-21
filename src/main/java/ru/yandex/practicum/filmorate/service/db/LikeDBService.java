package ru.yandex.practicum.filmorate.service.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.LikeService;
import ru.yandex.practicum.filmorate.storage.db.FilmDBStorage;
import ru.yandex.practicum.filmorate.storage.db.LikesDBStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDBStorage;
import java.util.List;

@Slf4j
@Service("likeDBService")
@ConditionalOnProperty(name = "film.storage.type", havingValue = "db")
public class LikeDBService implements LikeService {

    private FilmDBStorage filmStorage;
    private LikesDBStorage likesStorage;

    public LikeDBService(FilmDBStorage filmStorage, LikesDBStorage likesIMStorage, UserDBStorage userStorage) {
        this.filmStorage = filmStorage;
        this.likesStorage = likesIMStorage;
    }

    // Добавление лайка фильму
    @Override
    public void addLike(Long filmId, Long userId) {
        likesStorage.addLike(userId, filmId);
    }

    // Удаление лайка у фильма
    @Override
    public void removeLike(Long filmId, Long userId) {
        likesStorage.removeLike(filmId, userId);
    }

    // Получение списка самых популярных фильмов по количеству лайков
    @Override
    public List<Film> getTopPopularFilms(Integer count) {
        return filmStorage.getTopPopularFilms(count);
    }

    // Добавление мапы лайков
    @Override
    public void addLikesEmptySet(Long filmId) {
    }

    // Удаление мапы лайков
    @Override
    public void deleteLikesSet(Long filmId) {
        likesStorage.deleteLikesSet(filmId);
    }

}
