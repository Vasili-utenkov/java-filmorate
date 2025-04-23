package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Service
public class UserService {

    private UserStorage userStorage;
    private FriendsStorage friendsStorage;

    public UserService(UserStorage userStorage, FriendsStorage friendsStorage) {
        this.userStorage = userStorage;
        this.friendsStorage = friendsStorage;
    }

    //  получение списка всех пользователей.
    public Collection<User> getAllUsers() {
        return userStorage.getAll();
    }

    //    создание пользователя;
    public User create(User user) {
        // Создали пользователя
        user = userStorage.create(user);
    // Создали чистый список друзей
        friendsStorage.createEmptyFriendsSet(user.getId());
        return user;
    }

    //    обновление пользователя;
    public User update(User newUser) {
        return userStorage.update(newUser);
    }


}
