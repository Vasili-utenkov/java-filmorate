package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;


import java.util.Collection;

@Component
public interface FilmStorage extends Storage<Film> {

    @Override
    Film create(Film film);

    @Override
    Film update(Film film);

    @Override
    void delete(long id);

    @Override
    Film getById(long id);

    @Override
    Collection<Film> getAll();

}
