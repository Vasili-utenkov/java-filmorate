package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;


import java.util.Collection;

@Component
public interface FilmStorage{

// создание фильма
    Film create(Film film);

// Изменение фильма
    Film update(Film film);

// Удаление фильма
    void delete(long id);

// Получение фильма по коду id
    Film getById(long id);

// Получение списка фильмов
    Collection<Film> getAll();

}
