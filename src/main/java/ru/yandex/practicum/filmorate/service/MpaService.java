package ru.yandex.practicum.filmorate.service;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

public interface MpaService {

    // список объектов содержащих жанр
    List<MPA> getAll();

    // объект содержащий жанр с идентификатором id
    MPA getByID(Long id);

}
