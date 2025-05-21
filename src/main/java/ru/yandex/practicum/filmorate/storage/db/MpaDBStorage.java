package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mappers.repository.BaseRepository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class MpaDBStorage extends BaseRepository<MPA> implements MpaStorage {

    private static final String GET_ALL_QUERY =
            "SELECT * FROM MPA ";
    private static final String GET_BY_ID_QUERY =
            "SELECT * FROM MPA WHERE id = ?";

    public MpaDBStorage(JdbcTemplate jdbc, RowMapper<MPA> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public List<MPA> getAll() {
        return findMany(GET_ALL_QUERY);
    }

    @Override
    public MPA getByID(Long id) {
        return findOne(GET_BY_ID_QUERY, id);
    }





}
