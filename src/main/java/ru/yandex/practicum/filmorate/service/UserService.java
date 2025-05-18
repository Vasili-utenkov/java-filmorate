package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserService {
    //  получение списка всех пользователей.
    Collection<User> getAllUsers();

    //    создание пользователя;
    User create(User user);

    //    обновление пользователя;
    User update(User newUser);
}
