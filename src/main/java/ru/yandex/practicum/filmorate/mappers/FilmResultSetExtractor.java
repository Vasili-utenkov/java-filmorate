package ru.yandex.practicum.filmorate.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
public class FilmResultSetExtractor implements ResultSetExtractor<Film> {
    @Override
    public Film extractData(ResultSet rs) throws SQLException {
        Film film = null;
        Set<Genre> genres = new TreeSet<>(Comparator.comparingInt(g -> g.getId()));

        while (rs.next()) {
            if (film == null) {
                film = new FilmRowMapper().mapRow(rs, rs.getRow());
            }

            if (rs.getObject("genre_id") != null) {
                Genre genre = new Genre();
                genre.setId(rs.getInt("genre_id"));
                genre.setName(rs.getString("genre_name"));
                genres.add(genre);
            }
        }

        if (film != null) {
            film.setGenres(genres);
        }

        return film;
    }

}
