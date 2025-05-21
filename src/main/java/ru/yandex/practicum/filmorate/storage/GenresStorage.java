package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;
import java.util.List;

public interface GenresStorage {

    // список объектов содержащих жанр
    List<Genre> getAll();

    // объект содержащий жанр с идентификатором id
    Genre getByID(int id);

}
