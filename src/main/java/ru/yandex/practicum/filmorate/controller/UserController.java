package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    UserService userService;

    //  получение списка всех пользователей.
    @GetMapping
    public Collection<User> getAllUsers() {
        log.info("Запрос списка пользователей");
        return userService.getAllUsers();
    }

    //    создание пользователя;
    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("Создание пользователя");
        return userService.create(user);
    }

    //    обновление пользователя;
    @PutMapping
    public User updateUser(@Valid @RequestBody User newUser) {
        log.info("Изменение пользователя");
        return userService.update(newUser);
    }


}
