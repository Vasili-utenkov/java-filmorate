package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> users = new HashMap<>();

    public static void validate(User user) throws ValidationException {

//        электронная почта не может быть пустой и должна содержать символ @;
        if (user.getEmail().isEmpty() || user.getEmail() == null || user.getEmail().length() == 0) {
            throw new ValidationException("User: электронная почта не может быть пустой");
        }

        if (user.getEmail().indexOf('@') == -1) {
            throw new ValidationException("User: электронная почта должна содержать символ @");
        }

//        логин не может быть пустым и содержать пробелы;

        if (user.getLogin().isEmpty() || user.getLogin() == null || user.getLogin().length() == 0 ) {
            throw new ValidationException("User: логин не может быть пустым");
        }

        if (user.getLogin().indexOf(' ') != -1) {
            throw new ValidationException("User: логин не может содержать пробелы");
        }

//        дата рождения не может быть в будущем.
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("User: дата рождения не может быть в будущем");
        }

    }

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
        if (user.getName().isBlank() || user.getName() == null) {
            user.setName(user.getLogin());
        }

        user.setId(getNextID());

        try {
            validate(user);
        } catch (ValidationException e) {
            e.printStackTrace();
        }

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
        if (newUser.getName().isBlank() || newUser.getName() == null) {
            newUser.setName(newUser.getLogin());
        }

        try {
            validate(newUser);
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());
            // если публикация найдена и все условия соблюдены, обновляем её содержимое
            log.info("Изменили данные пользователя" + oldUser);
            return oldUser;
        }
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");

    }


}
