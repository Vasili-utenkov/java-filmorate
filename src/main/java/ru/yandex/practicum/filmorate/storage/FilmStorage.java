package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmStorage extends AbstractStorage<Film> {
    private final Map<Long, Film> films;

    @Override
    public Film create(Film film) {
        film.setId(getNextID());
        log.info("Добавили фильм" + film);
        film = super.create(film);
        return film;
    }

    @Override
    public Film update(Film newFilm) {
        Long id = newFilm.getId();
        if (films.containsKey(id)) {
            Film oldFilm = films.get(id);
            films.replace(id, newFilm);
            log.info("Изменили данные по фильму" + oldFilm);
            return super.update(newFilm);
        }

        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    @Override
    public void delete(long id) {
        log.info("Удалили фильм с id = " + id);
        super.delete(id);
    }

    @Override
    public Film getById(long id) {
        log.info("Получение фильма с id = " + id);
        return super.getById(id);
    }

    @Override
    public Collection<Film> getAll() {
        log.info("Запрос списка фильмов");
        return super.getAll();
    }

    private long getNextID() {
        long l = films.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++l;
    }

}
