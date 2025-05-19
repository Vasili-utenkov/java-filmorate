package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserService {
    /**  получение списка всех пользователей.
     *
     * @return
     */
    Collection<User> getAllUsers();

    /**   создание пользователя;
     *
     * @param user
     * @return
     */
    User create(User user);

    /**    обновление пользователя;
     *
     * @param newUser
     * @return
     */
    User update(User newUser);
}
