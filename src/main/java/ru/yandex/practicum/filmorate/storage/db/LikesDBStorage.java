package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mappers.repository.BaseRepository;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.LikesStorage;
import java.util.List;

@Slf4j
@Component
public class LikesDBStorage extends BaseRepository<Like> implements LikesStorage {

    private static final String DELETE_LIKE_SET_QUERY =
            "DELETE FROM Likes WHERE filmID = ?";
    private static final String ADD_LIKE_QUERY =
            "INSERT INTO Likes (userID, filmID) VALUES (?, ?)";
    private static final String REMOVE_LIKE_QUERY =
            "DELETE FROM Likes WHERE filmID = ? AND userID = ?";
    private static final String GET_TOP_POPULAR_FILMS_ID_QUERY = """
            SELECT f.id, f.name, f.description, f.releaseDate, f.duration, f.mpaID as mpa_id, MPA.name AS mpa_name, COUNT(l.userid) AS likes_count
            FROM film f LEFT JOIN likes l ON f.id = l.filmID
            LEFT JOIN MPA l ON MPA.id = f.mpaID
            GROUP BY
                f.id
            ORDER BY
                likes_count DESC
            LIMIT ?
            """;

    public LikesDBStorage(JdbcTemplate jdbc, RowMapper<Like> mapper) {
        super(jdbc, mapper);
    }

    // Добавление мапы лайков (insert)
    @Override
    public void createLikesEmptySet(Long filmId) {

    }

    // Удаление мапы лайков
    @Override
    public void deleteLikesSet(Long filmId) {
        delete(DELETE_LIKE_SET_QUERY, filmId);
    }

    // Добавление лайка фильму
    @Override
    public void addLike(Long userId, Long filmId) {
        insert(ADD_LIKE_QUERY, false, userId, filmId);
    }

    // Удаление лайка у фильма
    @Override
    public void removeLike(Long filmId, Long userId) {
        delete(REMOVE_LIKE_QUERY, filmId, userId);
    }

    // Получение списка ID самых популярных фильмов по количеству лайков
    @Override
    public List<Long> getTopPopularFilmsId(Integer count) {
        return getIDList(GET_TOP_POPULAR_FILMS_ID_QUERY, count);
    }


}
