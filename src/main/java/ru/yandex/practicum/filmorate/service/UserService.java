package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;

@Slf4j
@Service

public class UserService {

    private InMemoryUserStorage inMemoryUserStorage;
    private FriendsStorage friendsStorage;

    public UserService(InMemoryUserStorage inMemoryUserStorage, FriendsStorage friendsStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.friendsStorage = friendsStorage;
    }

    //  получение списка всех пользователей.
    public Collection<User> getAllUsers() {
        return inMemoryUserStorage.getAll();
    }

    //    создание пользователя;
    public User create(User user) {

        // Создали пользователя
        user = inMemoryUserStorage.create(user);
        // Создали чистый список друзей
        friendsStorage.createEmptyFriendsSet(user.getId());
        return user;
    }

    //    обновление пользователя;
    public User update(User newUser) {
        return inMemoryUserStorage.update(newUser);
    }


}
