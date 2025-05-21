package ru.yandex.practicum.filmorate.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

@Slf4j
@Component
@Primary
public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {

        Film film = new Film();

        // Основные поля фильма
        film.setId(rs.getLong("id"));
        film.setName(rs.getString("name"));
        film.setDescription(rs.getString("description"));

        film.setReleaseDate(rs.getDate("releaseDate").toLocalDate());
        film.setDuration(rs.getInt("duration"));
        // Заполнение MPA

        MPA mpa = new MPA();
        if (rs.getObject("mpaID") != null) {
            mpa.setId(rs.getInt("mpaID"));
        }
        if (rs.getObject("mpa_id") != null) {
            mpa.setId(rs.getInt("mpa_id"));
        }
        if (rs.getObject("mpa_name") != null) {
            mpa.setName(rs.getString("mpa_name"));
        }
        film.setMpa(mpa);

        // Жанры будут обрабатываться отдельно в ResultSetExtractor
        film.setGenres(new HashSet<>());

        return film;
    }

}

