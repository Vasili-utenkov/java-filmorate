package ru.yandex.practicum.filmorate.storage.inmemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Slf4j
@Component
public class FilmIMStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Film create(Film film) {
        film.setId(getNextID());
        log.info("Добавили фильм " + film);
        checkNullId(film);
        if (films.containsKey(film.getId())) {
            throw new AlreadyExistException("Существует запись с кодом " + film.getId());
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film newFilm) {
        Long id = newFilm.getId();
        if (films.containsKey(id)) {
            Film oldFilm = films.get(id);
            films.replace(id, newFilm);
            log.info("Изменили данные по фильму " + oldFilm);
            checkNullId(newFilm);
            films.replace(newFilm.getId(), newFilm);
            return newFilm;
        }
        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    @Override
    public void delete(long id) {
        log.info("Удалили фильм с id = " + id);
        films.remove(id);
    }

    @Override
    public Film getById(long id) {
        log.info("Получение фильма с id = " + id);
        final Film film = films.get(id);
        if (film == null) {
            throw new NotFoundException("Не найдена запись по id = " + id);
        }
        return film;
    }

    @Override
    public Collection<Film> getAll() {
        log.info("Запрос списка фильмов");
        return films.values();
    }

    private long getNextID() {
        long l = films.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++l;
    }

    private void checkNullId(Film film) {
        if (film.getId() == null) {
            throw new NotFoundException("Не найден код записи id");
        }
    }

}
