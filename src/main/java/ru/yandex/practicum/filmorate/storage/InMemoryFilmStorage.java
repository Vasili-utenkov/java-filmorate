package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage extends AbstractStorage<Film> implements FilmStorage {

    private final Map<Long, Film> films;// = new HashMap<>();
    private final FilmService service; // ссылка на сервис для удаления лайков

    public InMemoryFilmStorage(FilmService service) {
        this.service = service;
        this.films = storage;
    }


    @Override
    public Film create(Film film) {
        film.setId(getNextID());
        log.info("Добавили фильм" + film);
        film = super.create(film);
        log.info("Добавили мапу лайков для фильма " + film);
        service.addLikesEmptySet(film.getId());
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
        service.deleteLikesSet(id);
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
