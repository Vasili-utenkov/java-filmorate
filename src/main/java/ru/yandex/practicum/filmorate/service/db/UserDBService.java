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
@RequiredArgsConstructor
public class UserDBService implements UserService {

    private UserDBStorage userStorage;
    private FriendsDBStorage friendsStorage;

    public UserDBService(UserDBStorage userStorage, FriendsDBStorage friendsIMStorage) {
        this.userStorage = userStorage;
        this.friendsStorage = friendsIMStorage;
    }

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
