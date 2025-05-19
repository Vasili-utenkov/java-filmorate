package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mappers.repository.BaseRepository;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Slf4j
@Repository

public class UserDBStorage extends BaseRepository<User> implements UserStorage {

    private static final String CREATE_QUERY =
            "INSERT INTO users(login, email, name, birthday) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY =
            "UPDATE users SET login = ?, email = ?, name = ?,birthday = ? WHERE id = ?";
    private static final String DELETE_QUERY =
            "DELETE FROM users WHERE id = ?";
    private static final String GET_BY_ID_QUERY =
            "SELECT * FROM users WHERE id = ?";
    private static final String GET_ALL_QUERY =
            "SELECT * FROM users";

    public UserDBStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public User create(User user) {
        long id = insert(
                CREATE_QUERY,
                true,
                user.getLogin(),
                user.getEmail(),
                user.getName(),
                java.sql.Date.valueOf(user.getBirthday())
        );
        user.setId(id);
        log.info("Добавили пользователя " + user);
        return user;
    }

    @Override
    public User update(User user) {
        update(
                UPDATE_QUERY,
                user.getLogin(),
                user.getEmail(),
                user.getName(),
                java.sql.Date.valueOf(user.getBirthday()),
                user.getId()
        );
        log.info("Изменили данные пользователя" + user);
        return user;
    }

    @Override
    public void delete(long id) {
        log.info("Удалили пользователя с id = " + id);
        delete(DELETE_QUERY, id);
    }

    @Override
    public User getById(long id) {
        log.info("Получение пользователя с id = " + id);
        return findOne(GET_BY_ID_QUERY, id);
    }

    @Override
    public Collection<User> getAll() {
        log.info("Запрос списка пользователей");
        return findMany(GET_ALL_QUERY);
    }


}