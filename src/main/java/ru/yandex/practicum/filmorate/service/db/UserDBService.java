package ru.yandex.practicum.filmorate.service.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.db.FriendsDBStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDBStorage;
import java.util.Collection;

@Slf4j
@Service("userDBService")
@ConditionalOnProperty(name = "film.storage.type", havingValue = "db")
public class UserDBService implements UserService {

    private UserDBStorage userStorage;

    public UserDBService(UserDBStorage userStorage, FriendsDBStorage friendsIMStorage) {
        this.userStorage = userStorage;
    }

    /**
     * получение списка всех пользователей.
     *
     * @return
     */
    @Override
    public Collection<User> getAllUsers() {
        return userStorage.getAll();
    }

    /**
     * создание пользователя;
     *
     * @param user
     * @return
     */
    @Override
    public User create(User user) {
        return userStorage.create(user);
    }

    /**
     * обновление пользователя;
     *
     * @param newUser
     * @return
     */
    @Override
    public User update(User newUser) {
        return userStorage.update(newUser);
    }
}
