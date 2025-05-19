package ru.yandex.practicum.filmorate.service.inmemory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.inmemory.FriendsIMStorage;
import ru.yandex.practicum.filmorate.storage.inmemory.UserIMStorage;

import java.util.*;

@Slf4j
@Service("userIMService")
@ConditionalOnProperty(name = "film.storage.type", havingValue = "memory", matchIfMissing = true)
@RequiredArgsConstructor
public class UserIMService implements UserService {

    private UserIMStorage userStorage;
    private FriendsIMStorage friendsStorage;

//    public UserIMService(UserIMStorage userStorage, FriendsIMStorage friendsIMStorage) {
//        this.userStorage = userStorage;
//        this.friendsStorage = friendsIMStorage;
//    }

    //  получение списка всех пользователей.
    @Override
    public Collection<User> getAllUsers() {
        return userStorage.getAll();
    }

    //    создание пользователя;
    @Override
    public User create(User user) {

        // Создали пользователя
        user = userStorage.create(user);
        // Создали чистый список друзей
        friendsStorage.createEmptyFriendsSet(user.getId());
        return user;
    }

    //    обновление пользователя;
    @Override
    public User update(User newUser) {
        return userStorage.update(newUser);
    }

}
