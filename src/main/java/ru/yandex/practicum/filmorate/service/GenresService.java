package ru.yandex.practicum.filmorate.service;
import ru.yandex.practicum.filmorate.model.Genre;
import java.util.List;

public interface GenresService {

    // список объектов содержащих жанр
    List<Genre> getAll();

    // объект содержащий жанр с идентификатором id
    Genre getByID(int id);

}
