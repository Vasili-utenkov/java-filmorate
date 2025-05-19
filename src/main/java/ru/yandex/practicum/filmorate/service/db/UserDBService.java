package ru.yandex.practicum.filmorate.service.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
@Slf4j
@Service("userDBService")
@ConditionalOnProperty(name = "film.storage.type", havingValue = "db")
public class UserDBService implements UserService {
    /**
     * получение списка всех пользователей.
     *
     * @return
     */
    @Override
    public Collection<User> getAllUsers() {
        return null;
    }

    /**
     * создание пользователя;
     *
     * @param user
     * @return
     */
    @Override
    public User create(User user) {
        return null;
    }

    /**
     * обновление пользователя;
     *
     * @param newUser
     * @return
     */
    @Override
    public User update(User newUser) {
        return null;
    }
}
