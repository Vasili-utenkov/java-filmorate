package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mappers.repository.BaseRepository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenresStorage;
import java.util.List;

@Component
public class GenresDBStorage extends BaseRepository<Genre>  implements GenresStorage {

    private static final String GET_ALL_QUERY =
            "SELECT * FROM Genres ";
    private static final String GET_BY_ID_QUERY =
            "SELECT * FROM Genres WHERE id = ?";

    public GenresDBStorage(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public List<Genre> getAll() {
        return findMany(GET_ALL_QUERY);
    }

    @Override
    public Genre getByID(Long id) {
        return findOne(GET_BY_ID_QUERY, id);
    }
}
