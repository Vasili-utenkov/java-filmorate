package ru.yandex.practicum.filmorate.service.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenresService;
import ru.yandex.practicum.filmorate.storage.db.GenresDBStorage;

import java.util.List;


@Slf4j
@Service("genresDBService")
@ConditionalOnProperty(name = "film.storage.type", havingValue = "db")
public class GenresDBService implements GenresService {

    private GenresDBStorage genresStorage;

    public GenresDBService(GenresDBStorage genresStorage) {
        this.genresStorage = genresStorage;
    }

    @Override
    public List<Genre> getAll() {
        return genresStorage.getAll();
    }

    @Override
    public Genre getByID(Long id) {
        return genresStorage.getByID(id);
    }
}
