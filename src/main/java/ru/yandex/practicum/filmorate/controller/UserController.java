package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.factory.UserServiceFactory;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceFactory userServiceFactory;
    private final UserService userService; // Делаем final

    @Autowired
    public UserController(UserServiceFactory userServiceFactory) {
        this.userServiceFactory = userServiceFactory;
        this.userService = userServiceFactory.getUserService(); // Инициализация здесь
    }

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
