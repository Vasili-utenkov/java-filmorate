package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> users = new HashMap<>();

    //    получение списка всех пользователей.
    @GetMapping
    public Collection<User> getUsers() {
        log.info("Запрос списка пользователей");
        return users.values();
    }

    //    создание пользователя;
    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        // имя для отображения может быть пустым — в таком случае будет использован логин;
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(getNextID());
        log.info("Добавили пользователя" + user);
        users.put(user.getId(), user);
        return user;
    }

    private long getNextID() {
        long l = users.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++l;
    }

    //    обновление пользователя;
    @PutMapping
    public User updateUser(@Valid @RequestBody User newUser) {
        // имя для отображения может быть пустым — в таком случае будет использован логин;
        if (newUser.getName() == null || newUser.getName().isBlank()) {
            newUser.setName(newUser.getLogin());
        }

        Long id = newUser.getId();
        if (users.containsKey(id)) {
            // если публикация найдена и все условия соблюдены, обновляем её содержимое
            User oldUser = users.get(id);
            users.replace(id, newUser);
            log.info("Изменили данные пользователя" + oldUser);
            return newUser;
        }
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");

    }


}
